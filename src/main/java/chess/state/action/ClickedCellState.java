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

    private final ModelFactory modelFactory;

    private final ChessModelUtils utils;

    private int row;

    private int col;

    public ClickedCellState(final ApplicationStateContext context,
                            final ModelFactory modelFactory, final ChessModelUtils utils) {
        super(context);
        this.modelFactory = modelFactory;
        this.utils = utils;
        this.row = -1;
        this.col = -1;
    }

    @Override
    public void onEnter() {
        if (this.row == -1 || this.col == -1) {
            throw new IllegalStateException("Must set row and column index first.");
        }
        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        final Color newSelectedPieceColor = chessBoardModel.getPieceColorForCell(this.row,
                this.col);
        final int selectedRow = this.context.getSelectedCellRow();
        final int selectedCol = this.context.getSelectedCellCol();

        if (this.triedToSelectWrongColorPiece(chessBoardModel, newSelectedPieceColor,
                selectedRow, selectedCol)) {
            return;
        }

        if (selectedRow > -1 && selectedCol > -1) {
            final Color oldSelectedPieceColor =
                    chessBoardModel.getPieceColorForCell(selectedRow, selectedCol);
            if (oldSelectedPieceColor != newSelectedPieceColor) {
                this.doMove(selectedRow, selectedCol, chessBoardModel, oldSelectedPieceColor);
            } else {
                this.updateSelectedCell();
                this.clearHighlightedCells();
                this.updateCellHighlightedForSelectedCell(chessBoardModel, newSelectedPieceColor);
            }

        } else if (Color.NONE != newSelectedPieceColor) {
            this.updateSelectedCell();
            this.updateCellHighlightedForSelectedCell(chessBoardModel, newSelectedPieceColor);
        }
    }

    private void updateCellHighlightedForSelectedCell(final ChessBoardModel chessBoardModel, final Color newSelectedPieceColor) {
        final int selectedCellRow = this.context.getSelectedCellRow();
        final int selectedCellCol = this.context.getSelectedCellCol();
        final int selectedPiece = chessBoardModel.getPieceForCell(selectedCellRow, selectedCellCol);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(selectedPiece);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(chessBoardModel, pieceType, newSelectedPieceColor);
        final List<Move> moves = chessPiece.getMoves(selectedCellRow, selectedCellCol);
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
     * @param chessBoardModel       - chess board to check.
     * @param newSelectedPieceColor - selected piece color.
     * @param selectedCellRow       - selected row.
     * @param selectedCellCol       - selected column.
     * @return true iff a player tries to select the wrong color piece (i.e. to show available
     * * moves)
     */
    private boolean triedToSelectWrongColorPiece(final ChessBoardModel chessBoardModel, final Color newSelectedPieceColor, final int selectedCellRow, final int selectedCellCol) {
        return (selectedCellRow == -1 && selectedCellCol == -1) &&
                ((this.context.isPlayer1sTurn() && chessBoardModel.isPlayerOneWhite()
                        && Color.BLACK == newSelectedPieceColor) ||
                        (this.context.isPlayer1sTurn() && !chessBoardModel.isPlayerOneWhite()
                                && Color.WHITE == newSelectedPieceColor) ||
                        (!this.context.isPlayer1sTurn() && chessBoardModel.isPlayerOneWhite()
                                && Color.WHITE == newSelectedPieceColor) ||
                        (!this.context.isPlayer1sTurn() && !chessBoardModel.isPlayerOneWhite()
                                && Color.BLACK == newSelectedPieceColor));
    }

    private void doMove(final int selectedRow, final int selectedCol,
                        final ChessBoardModel chessBoardModel,
                        final Color selectedPieceColor) {
        final int selectedPiece = chessBoardModel.getPieceForCell(selectedRow, selectedCol);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(selectedPiece);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(chessBoardModel, pieceType,
                selectedPieceColor);
        final List<Move> moves = chessPiece.getMoves(selectedRow, selectedCol);
        final boolean isLegalMove = moves.stream()
                .filter(move -> move.getDestRow() == this.row && move.getDestCol() == this.col).count() == 1;

        final ChessBoardModel tempChessBoard = this.utils.applyMoveToCopiedBoard(this.row, this.col,
                selectedRow, selectedCol, chessBoardModel, selectedPieceColor, pieceType);

        if (isLegalMove && !this.utils.isColorInCheck(tempChessBoard, selectedPieceColor)) {
            this.updateAfterMove(selectedRow, selectedCol, selectedPieceColor, pieceType);
        }

        final Color opposingColor = Color.getOpposingColor(selectedPieceColor);
        if (this.utils.isColorInCheckMate(tempChessBoard, opposingColor)) {
            this.setCheckmateStyle();
        } else if (this.utils.isColorInCheck(tempChessBoard, opposingColor)) {
            this.setKingInCheckStyle(chessBoardModel, opposingColor);
        }


    }

    private void updateAfterMove(final int selectedRow, final int selectedCol,
                                 final Color selectedPieceColor, final ChessPiece.PieceType pieceType) {
        this.clearHighlightedCells();
        this.clearCell(selectedRow, selectedCol);
        this.updateBoardWithPiece(this.row, this.col, pieceType, selectedPieceColor);
        this.context.setSelectedRow(-1);
        this.context.setSelectedCol(-1);
        this.context.setIsPlayer1sTurn(!this.context.isPlayer1sTurn());
    }

    private void setKingInCheckStyle(final ChessBoardModel chessBoardModel,
                                     final Color opposingColor) {
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final int pieceForCell = chessBoardModel.getPieceForCell(row, col);
                final Color pieceColorForCell = chessBoardModel.getPieceColorForCell(row, col);
                if (Math.abs(pieceForCell) == ChessPiece.PieceType.KING.getPieceCode()
                        && pieceColorForCell == opposingColor) {
                    kingRow = row;
                    kingCol = col;
                }
            }
        }
        this.updateCellStyle(kingRow, kingCol, ChessBoardCell.IN_CHECK_CELL_CSS_CLASS, true);
    }

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
