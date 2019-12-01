package chess.state.action;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.model.Color;
import chess.model.Move;
import chess.model.piece.ChessPiece;
import chess.state.GameState;
import chess.view.core.ChessBoardCell;

import java.util.List;

/**
 * This class contains methods to show or hide the application context menu.
 */
public class ClickedCellState extends GameState {

    private int row;

    private int col;

    public ClickedCellState(final ApplicationStateContext context,
                            final ModelFactory modelFactory, final ChessModelUtils utils) {
        super(context, utils, modelFactory);
        this.row = -1;
        this.col = -1;
    }

    @Override
    public void onEnter() {
        // Check some basic preconditions: require fields to be set.
        if (this.row == -1 || this.col == -1) {
            throw new IllegalStateException("Must set row and column index first.");
        }
        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        final Color newSelectedPieceColor = chessBoardModel.getPieceColorForCell(this.row,
                this.col);
        final int selectedRow = this.context.getSelectedCellRow();
        final int selectedCol = this.context.getSelectedCellCol();
        // If the player tries to move the other color.
        if (this.triedToSelectWrongColorPiece(chessBoardModel, newSelectedPieceColor, selectedRow, selectedCol)) {
            return;
        }

        // If a piece is already selected.
        if (selectedRow > -1 && selectedCol > -1) {
            final Color oldSelectedPieceColor =
                    chessBoardModel.getPieceColorForCell(selectedRow, selectedCol);
            // If new selected cell color is the same as the current selected piece color.
            if (oldSelectedPieceColor == newSelectedPieceColor) {
                this.updateSelectedCell();
                this.clearHighlightedCells(false);
                this.updateCellHighlightingForSelectedCell(chessBoardModel, newSelectedPieceColor);
            } else {
                this.doMove(selectedRow, selectedCol, chessBoardModel, oldSelectedPieceColor);
            }
        } else if (Color.NONE != newSelectedPieceColor) {
            this.updateSelectedCell();
            this.updateCellHighlightingForSelectedCell(chessBoardModel, newSelectedPieceColor);
        }
    }

    /**
     * Adds cell highlighting to every move where the selected piece could move. Note that this filters out
     * moves for check and other special cases (whereas simply calling ChessPiece::getMoves does not).
     * @param chessBoardModel - chess board object.
     * @param newSelectedPieceColor - piece color to determine moves for.
     */
    private void updateCellHighlightingForSelectedCell(final ChessBoardModel chessBoardModel,
                                                       final Color newSelectedPieceColor) {
        final int selectedCellRow = this.context.getSelectedCellRow();
        final int selectedCellCol = this.context.getSelectedCellCol();
        final int selectedPiece = chessBoardModel.getPieceForCell(selectedCellRow, selectedCellCol);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(selectedPiece);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(chessBoardModel, pieceType, newSelectedPieceColor);
        final List<Move> moves = chessPiece.getMoves(selectedCellRow, selectedCellCol, true);
        moves.forEach(move -> {
            final ChessBoardModel tempChessBoard = this.utils.applyMoveToCopiedBoard(move.getDestRow(),
                    move.getDestCol(), selectedCellRow, selectedCellCol,
                    chessBoardModel, newSelectedPieceColor, pieceType);
            if (!this.utils.isColorInCheck(tempChessBoard, newSelectedPieceColor)) {
                this.updateCellStyle(move.getDestRow(), move.getDestCol(),
                        ChessBoardCell.HIGHLIGHTED_CELL_CSS_CLASS, true);
            }
        });
    }

    /**
     * Returns true iff a player tries to select the wrong color piece (i.e. to show available
     * moves)
     *
     * @param board                 - chess board to check.
     * @param newSelectedPieceColor - selected piece color.
     * @param selectedCellRow       - selected row.
     * @param selectedCellCol       - selected column.
     * @return true iff a player tries to select the wrong color piece (i.e. to show available
     *   moves)
     */
    private boolean triedToSelectWrongColorPiece(final ChessBoardModel board, final Color newSelectedPieceColor,
                                                 final int selectedCellRow, final int selectedCellCol) {
        return (selectedCellRow == -1 && selectedCellCol == -1) &&
                ((this.context.isPlayer1sTurn() && board.isPlayerOneWhite()
                        && Color.BLACK == newSelectedPieceColor) ||
                        (this.context.isPlayer1sTurn() && !board.isPlayerOneWhite()
                                && Color.WHITE == newSelectedPieceColor) ||
                        (!this.context.isPlayer1sTurn() && board.isPlayerOneWhite()
                                && Color.WHITE == newSelectedPieceColor) ||
                        (!this.context.isPlayer1sTurn() && !board.isPlayerOneWhite()
                                && Color.BLACK == newSelectedPieceColor));
    }

    /**
     * Validates the legality of a move, and applies it if it passes such checks.
     *
     * @param selectedRow - source row of proposed move.
     * @param selectedCol - source column of proposed move.
     * @param board - chess board object.
     * @param selectedPieceColor - piece color of proposed move.
     */
    private void doMove(final int selectedRow, final int selectedCol, final ChessBoardModel board,
                        final Color selectedPieceColor) {
        final int selectedPiece = board.getPieceForCell(selectedRow, selectedCol);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(selectedPiece);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                selectedPieceColor);
        final List<Move> moves = chessPiece.getMoves(selectedRow, selectedCol, true);
        final boolean isLegalMove = moves.stream()
                .filter(move -> move.getDestRow() == this.row && move.getDestCol() == this.col).count() == 1;

        final ChessBoardModel tempBoard = this.utils.applyMoveToCopiedBoard(this.row, this.col,
                selectedRow, selectedCol, board, selectedPieceColor, pieceType);

        if (isLegalMove && !this.utils.isColorInCheck(tempBoard, selectedPieceColor)) {
            this.applyMove(selectedRow, selectedCol, selectedPieceColor, pieceType);
            this.doAfterMoveChecks(board, selectedPieceColor, tempBoard);
        }

    }

    /**
     * Updates the view and model to apply a move to the board.
     *
     * @param selectedRow        - source row of move.
     * @param selectedCol        - source column of move.
     * @param selectedPieceColor - color of piece to move.
     * @param pieceType          - type of piece to move.
     */
    private void applyMove(final int selectedRow, final int selectedCol,
                           final Color selectedPieceColor, final ChessPiece.PieceType pieceType) {
        this.clearHighlightedCells(true);
        this.clearCell(selectedRow, selectedCol);
        this.updateBoardWithPiece(this.row, this.col, pieceType, selectedPieceColor);
        this.context.setSelectedRow(-1);
        this.context.setSelectedCol(-1);
        this.context.setIsPlayer1sTurn(!this.context.isPlayer1sTurn());
    }

    /**
     * Does various after move checks, such as looking for check, checkmate and stalemate.
     * @param board - chess board object.
     * @param selectedPieceColor - color whose turn it will be.
     * @param tempBoard - new state of the board after a move.
     */
    private void doAfterMoveChecks(final ChessBoardModel board, final Color selectedPieceColor, final ChessBoardModel tempBoard) {
        final Color opposingColor = Color.getOpposingColor(selectedPieceColor);
        if (this.utils.isColorInCheckMate(tempBoard, opposingColor)) {
            this.setKingInCheckmateStyle(board, opposingColor);
        } else if (this.utils.isColorInCheck(tempBoard, opposingColor)) {
            this.setKingInCheckStyle(board, opposingColor);
        }
    }


    /**
     * Updates the selected cell with the newly clicked cell in this event / state.
     */
    private void updateSelectedCell() {
        this.context.setSelectedRow(this.row);
        this.context.setSelectedCol(this.col);
    }

    public void setCol(final int col) {
        this.col = col;
    }

    public void setRow(final int row) {
        this.row = row;
    }
}
