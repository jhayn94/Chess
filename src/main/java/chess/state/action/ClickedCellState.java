package chess.state.action;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessAI;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.model.Color;
import chess.model.Move;
import chess.model.MoveWithSource;
import chess.model.MovedPieces;
import chess.model.piece.ChessPiece;
import chess.model.piece.King;
import chess.state.GameState;
import chess.view.core.ChessBoardCell;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

import static chess.model.ChessBoardModel.LEFT_ROOK_CASTLE_DEST_COL;
import static chess.model.ChessBoardModel.RIGHT_ROOK_CASTLE_DEST_COL;
import static chess.model.piece.Rook.LEFT_ROOK_START_COL;
import static chess.model.piece.Rook.PLAYER_ONE_ROOK_START_ROW;
import static chess.model.piece.Rook.PLAYER_TWO_ROOK_START_ROW;
import static chess.model.piece.Rook.RIGHT_ROOK_START_COL;

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
        final ChessBoardModel chessBoardModel = this.context.getBoard();
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
     *
     * @param board                 - chess board object.
     * @param newSelectedPieceColor - piece color to determine moves for.
     */
    private void updateCellHighlightingForSelectedCell(final ChessBoardModel board,
                                                       final Color newSelectedPieceColor) {
        final int selectedCellRow = this.context.getSelectedCellRow();
        final int selectedCellCol = this.context.getSelectedCellCol();
        final int selectedPiece = board.getPieceForCell(selectedCellRow, selectedCellCol);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(selectedPiece);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType, newSelectedPieceColor);
        final List<Move> moves = chessPiece.getMoves(selectedCellRow, selectedCellCol, true);
        moves.forEach(move -> {
            final ChessBoardModel boardAfterMove = this.utils.applyMoveToCopiedBoard(move,
                    selectedCellRow, selectedCellCol, board, newSelectedPieceColor, pieceType);
            if (this.utils.isMoveLegal(board, boardAfterMove, newSelectedPieceColor, move)) {
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
     * moves)
     */
    private boolean triedToSelectWrongColorPiece(final ChessBoardModel board, final Color newSelectedPieceColor,
                                                 final int selectedCellRow, final int selectedCellCol) {
        return (selectedCellRow == -1 && selectedCellCol == -1) &&
                ((board.isPlayer1sTurn() && board.isPlayerOneWhite()
                        && Color.BLACK == newSelectedPieceColor) ||
                        (board.isPlayer1sTurn() && !board.isPlayerOneWhite()
                                && Color.WHITE == newSelectedPieceColor) ||
                        (!board.isPlayer1sTurn() && board.isPlayerOneWhite()
                                && Color.WHITE == newSelectedPieceColor) ||
                        (!board.isPlayer1sTurn() && !board.isPlayerOneWhite()
                                && Color.BLACK == newSelectedPieceColor));
    }

    /**
     * Validates the legality of a move, and applies it if it passes such checks.
     *
     * @param selectedRow        - source row of proposed move.
     * @param selectedCol        - source column of proposed move.
     * @param board              - chess board object.
     * @param selectedPieceColor - piece color of proposed move.
     */
    private void doMove(final int selectedRow, final int selectedCol, final ChessBoardModel board,
                        final Color selectedPieceColor) {
        final int selectedPiece = board.getPieceForCell(selectedRow, selectedCol);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(selectedPiece);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                selectedPieceColor);
        final List<Move> moves = chessPiece.getMoves(selectedRow, selectedCol, true);
        final Optional<Move> moveToMake = moves.stream()
                .filter(move -> move.getDestRow() == this.row && move.getDestCol() == this.col).findFirst();
        final boolean isValidMoveForPiece = moveToMake.isPresent();

        if (isValidMoveForPiece) {
            final ChessBoardModel tempBoard = this.utils.applyMoveToCopiedBoard(moveToMake.get(),
                    selectedRow, selectedCol, board, selectedPieceColor, pieceType);
            if (this.utils.isMoveLegal(board, tempBoard, selectedPieceColor,
                    moveToMake.get())) {
                this.addBoardToUndoStack();
                this.applyMove(selectedRow, selectedCol, moveToMake.get(), selectedPieceColor, pieceType);
                this.doAfterMoveChecks(board, selectedPieceColor);
                final Color opposingColor = Color.getOpposingColor(selectedPieceColor);
                if (board.isP2Ai() && !this.utils.isColorInCheckMate(board, opposingColor)
                        && !this.utils.isColorInStalemate(board, opposingColor)) {
                    this.makeAIMove(board, opposingColor);
                }
            }
        }

    }

    /**
     * Makes a move as the AI.
     *
     * @param board   - chess board object.
     * @param aiColor - ai's color
     */
    private void makeAIMove(final ChessBoardModel board, final Color aiColor) {
        final ChessAI chessAI = this.modelFactory.chessAi(board, aiColor, this.utils);
        final MoveWithSource move = chessAI.findMove();
        final int pieceCode = board.getPieceForCell(move.getSrcRow(), move.getSrcCol());
        final ChessPiece.PieceType aiMovePieceType = ChessPiece.PieceType.fromCode(pieceCode);
        this.applyMove(move.getSrcRow(), move.getSrcCol(), move,
                aiColor, aiMovePieceType);
        this.doAfterMoveChecks(board, aiColor);
    }

    /**
     * Updates the view and model to apply a move to the board.
     *
     * @param selectedRow        - currently selected row.
     * @param selectedCol        - currently selected column.
     * @param moveToMake         - move to apply.
     * @param selectedPieceColor - color of piece to move.
     * @param pieceType          - type of piece to move.
     */
    private void applyMove(final int selectedRow, final int selectedCol, final Move moveToMake,
                           final Color selectedPieceColor, final ChessPiece.PieceType pieceType) {
        this.clearHighlightedCells(true);
        this.clearCell(selectedRow, selectedCol, true);
        this.updateBoardWithPiece(moveToMake.getDestRow(), moveToMake.getDestCol(), pieceType, selectedPieceColor);
        this.checkForSpecialMove(moveToMake, selectedPieceColor);
        this.context.setSelectedRow(-1);
        this.context.setSelectedCol(-1);
        this.context.getBoard().setIsPlayer1sTurn(!this.context.getBoard().isPlayer1sTurn());
        this.updateSpecialMoveStates(selectedRow, selectedCol, moveToMake, selectedPieceColor, pieceType);
    }

    /**
     * Updates various data structures defining if special moves like castling and en passant are allowed.
     *
     * @param selectedRow        - selected row of the moved piece.
     * @param selectedCol        - selected column of the moved piece.
     * @param moveToMake         - move being made this turn.
     * @param selectedPieceColor - color making the move.
     * @param pieceType          - piece type being moved.
     */
    private void updateSpecialMoveStates(final int selectedRow, final int selectedCol, final Move moveToMake, final Color selectedPieceColor, final ChessPiece.PieceType pieceType) {
        final ChessBoardModel board = this.context.getBoard();
        if (ChessPiece.PieceType.PAWN == pieceType && Math.abs(selectedRow - moveToMake.getDestRow()) == 2) {
            board.setEnpassant(new Pair<>(selectedPieceColor, moveToMake.getDestCol()));
        } else {
            board.setEnpassant(new Pair<>(Color.NONE, -1));
        }
        if (ChessPiece.PieceType.KING == pieceType && selectedRow == King.KING_START_ROW_BOTTOM) {
            board.getMovedPieces().add(MovedPieces.BOTTOM_KING);
        } else if (ChessPiece.PieceType.KING == pieceType && selectedRow == King.KING_START_ROW_TOP) {
            board.getMovedPieces().add(MovedPieces.TOP_KING);
        } else if (ChessPiece.PieceType.ROOK == pieceType) {
            if (selectedRow == PLAYER_ONE_ROOK_START_ROW && selectedCol == LEFT_ROOK_START_COL) {
                board.getMovedPieces().add(MovedPieces.BOTTOM_LEFT_ROOK);
            } else if (selectedRow == PLAYER_ONE_ROOK_START_ROW && selectedCol == RIGHT_ROOK_START_COL) {
                board.getMovedPieces().add(MovedPieces.BOTTOM_RIGHT_ROOK);
            } else if (selectedRow == PLAYER_TWO_ROOK_START_ROW && selectedCol == LEFT_ROOK_START_COL) {
                board.getMovedPieces().add(MovedPieces.TOP_LEFT_ROOK);
            } else if (selectedRow == PLAYER_TWO_ROOK_START_ROW && selectedCol == RIGHT_ROOK_START_COL) {
                board.getMovedPieces().add(MovedPieces.TOP_RIGHT_ROOK);
            }
        }
    }

    /**
     * Checks for and applies any special move logic.
     *
     * @param moveToMake         - move to check if is special.
     * @param selectedPieceColor - color who is moving.
     */
    private void checkForSpecialMove(final Move moveToMake, final Color selectedPieceColor) {
        final ChessBoardModel board = this.context.getBoard();
        if (Move.MoveType.CASTLE_RIGHT == moveToMake.getMoveType()) {
            if (board.isPlayer1sTurn()) {
                this.clearCell(PLAYER_ONE_ROOK_START_ROW, RIGHT_ROOK_START_COL, true);
                this.updateBoardWithPiece(PLAYER_ONE_ROOK_START_ROW, RIGHT_ROOK_CASTLE_DEST_COL, ChessPiece.PieceType.ROOK, selectedPieceColor);
            } else {
                this.clearCell(PLAYER_TWO_ROOK_START_ROW, RIGHT_ROOK_START_COL, true);
                this.updateBoardWithPiece(PLAYER_TWO_ROOK_START_ROW, RIGHT_ROOK_CASTLE_DEST_COL, ChessPiece.PieceType.ROOK, selectedPieceColor);
            }
        } else if (Move.MoveType.CASTLE_LEFT == moveToMake.getMoveType()) {
            if (board.isPlayer1sTurn()) {
                this.clearCell(PLAYER_ONE_ROOK_START_ROW, LEFT_ROOK_START_COL, true);
                this.updateBoardWithPiece(PLAYER_ONE_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL, ChessPiece.PieceType.ROOK, selectedPieceColor);
            } else {
                this.clearCell(PLAYER_TWO_ROOK_START_ROW, LEFT_ROOK_START_COL, true);
                this.updateBoardWithPiece(PLAYER_TWO_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL, ChessPiece.PieceType.ROOK, selectedPieceColor);
            }
        } else if (Move.MoveType.EN_PASSANT == moveToMake.getMoveType()) {
            if (board.isPlayer1sTurn()) {
                this.clearCell(moveToMake.getDestRow() + 1, moveToMake.getDestCol(), true);
            } else {
                this.clearCell(moveToMake.getDestRow() - 1, moveToMake.getDestCol(), true);
            }
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
