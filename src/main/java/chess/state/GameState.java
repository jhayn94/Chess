package chess.state;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.BoardHistory;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.model.Color;
import chess.model.Move;
import chess.model.piece.ChessPiece;
import chess.view.core.ChessBoardCell;
import chess.view.core.ChessBoardView;
import chess.view.menu.EditMenu;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.List;

/**
 * A generic class for when the game changes state (i.e. anything occurs through the AI or player interaction).
 */
public abstract class GameState {

    protected final ApplicationStateContext context;

    protected final ChessModelUtils utils;

    protected final ModelFactory modelFactory;

    protected GameState(final ApplicationStateContext context, final ChessModelUtils utils,
                        final ModelFactory modelFactory) {
        this.context = context;
        this.utils = utils;
        this.modelFactory = modelFactory;
    }

    public abstract void onEnter();

    /**
     * Removes all pieces from the board.
     */
    protected void clearBoard(final boolean clearModel) {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                this.clearCell(row, col, clearModel);
            }
        }
    }

    /**
     * Removes all non-static styling to cells.
     *
     * @param clearInCheckStyle - if true, removes the (currently red) style for indicating check or checkmate.
     */
    protected void clearHighlightedCells(final boolean clearInCheckStyle) {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                if (clearInCheckStyle) {
                    this.updateCellStyle(row, col, ChessBoardCell.IN_CHECK_CELL_CSS_CLASS, false);
                    this.updateCellStyle(row, col, ChessBoardCell.STALEMATE_CELL_CSS_CLASS, false);
                }
                this.updateCellStyle(row, col, ChessBoardCell.HIGHLIGHTED_CELL_CSS_CLASS, false);
            }
        }
    }

    /**
     * Clears a single cell of its piece.
     *
     * @param row - row to clear.
     * @param col - column to clear.
     */
    protected void clearCell(final int row, final int col, final boolean clearModel) {
        if (clearModel) {
            this.clearCellModel(row, col);
        }
        this.clearCellView(row, col);
    }

    /**
     * Clears a single cell view component of its piece.
     *
     * @param row - row to clear.
     * @param col - column to clear.
     */
    protected void clearCellView(final int row, final int col) {
        final ChessPiece.PieceType emptyPiece = ChessPiece.PieceType.NONE;
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        chessBoardView.getCell(row, col).setImage(emptyPiece.getResourcePath(), Color.NONE);
    }

    /**
     * Clears a single cell model component of its piece.
     *
     * @param row - row to clear.
     * @param col - column to clear.
     */
    protected void clearCellModel(final int row, final int col) {
        final ChessPiece.PieceType emptyPiece = ChessPiece.PieceType.NONE;
        final ChessBoardModel chessBoardModel = this.context.getBoard();
        chessBoardModel.setPieceForCell(row, col, emptyPiece.getPieceCode(), Color.NONE);
    }

    /**
     * Places a piece at the given location.
     *
     * @param row       - row to use when placing piece.
     * @param col       - column to use when placing piece.
     * @param pieceType - type of piece to place (see ChessPiece.PieceType).
     * @param color     - color of piece to place.
     */
    protected void updateBoardWithPiece(final int row, final int col,
                                        final ChessPiece.PieceType pieceType, final Color color) {
        final ChessBoardModel chessBoardModel = this.context.getBoard();
        chessBoardModel.setPieceForCell(row, col, pieceType.getPieceCode(), color);
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        chessBoardView.getCell(row, col).setImage(pieceType.getResourcePath(), color);
    }

    /**
     * Does various after move checks, such as looking for check, checkmate and stalemate.
     *
     * @param board              - chess board object.
     * @param movedPieceColor - color whose turn it just was.
     */
    protected void doAfterMoveChecks(final ChessBoardModel board, final Color movedPieceColor) {
        final Color opposingColor = Color.getOpposingColor(movedPieceColor);
        if (this.utils.isColorInCheckMate(board, opposingColor)) {
            System.out.println("CHECKMATE");
            this.setKingInCheckmateStyle(board, opposingColor);
        } else if (this.utils.isColorInCheck(board, opposingColor)) {
            this.setKingCellStyle(board, opposingColor, ChessBoardCell.IN_CHECK_CELL_CSS_CLASS);
        } else if (this.utils.isColorInStalemate(board, opposingColor)) {
            this.setKingInStalemateStyle(board, opposingColor);
        }
    }

    /**
     * Updates the style of the given cell on the board.
     *
     * @param row      - row to modify style of.
     * @param col      - column to modify style of.
     * @param cssClass - CSS selector to add or remove.
     * @param add      - if true, adds the given selector. Otherwise, removes the selector.
     */
    protected void updateCellStyle(final int row, final int col, final String cssClass, final boolean add) {
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        final ChessBoardCell cell = chessBoardView.getCell(row, col);
        final ObservableList<String> styleClass = cell.getStyleClass();
        if (add && !styleClass.contains(cssClass)) {
            styleClass.add(cssClass);
        } else {
            styleClass.remove(cssClass);
        }
    }

    /**
     * Sets the style on the king cell to indicate he is in check.
     *
     * @param board        - chess board object.
     * @param colorInCheck - color of king to use.
     * @param cssClass     - CSS class to apply to king cell.
     */
    protected void setKingCellStyle(final ChessBoardModel board, final Color colorInCheck, final String cssClass) {
        final Pair<Integer, Integer> kingPosition = this.utils.getKingPosition(board, colorInCheck);
        this.updateCellStyle(kingPosition.getKey(), kingPosition.getValue(),
                cssClass, true);
    }

    /**
     * Sets the style on the king cell to indicate he is in check. Adds style to the king and any piece
     * threatening a cell adjacent to him.
     *
     * @param board            - chess board object.
     * @param colorInCheckmate - color of king to use.
     */
    protected void setKingInCheckmateStyle(final ChessBoardModel board, final Color colorInCheckmate) {
        this.setKingCellStyle(board, colorInCheckmate, ChessBoardCell.IN_CHECK_CELL_CSS_CLASS);
        this.applyEndGameStyle(board, colorInCheckmate, ChessBoardCell.IN_CHECK_CELL_CSS_CLASS);
    }

    /**
     * Sets the style on the king cell to indicate he is in check. Adds style to the king and any piece
     * threatening a cell adjacent to him.
     *
     * @param board            - chess board object.
     * @param colorInCheckmate - color of king to use.
     */
    protected void setKingInStalemateStyle(final ChessBoardModel board, final Color colorInCheckmate) {
        this.setKingCellStyle(board, colorInCheckmate, ChessBoardCell.STALEMATE_CELL_CSS_CLASS);
        this.applyEndGameStyle(board, colorInCheckmate, ChessBoardCell.STALEMATE_CELL_CSS_CLASS);
    }

    /**
     * Applies the given CSS class to cells which participate in creating an end game condition.
     *
     * @param board    - chess board object.
     * @param color    - color causing end game conditions (i.e. who is in checkmate or stalemate).
     * @param cssClass - CSS class to apply to relevant cells
     */
    private void applyEndGameStyle(final ChessBoardModel board, final Color color,
                                   final String cssClass) {
        final Pair<Integer, Integer> kingPosition = this.utils.getKingPosition(board, color);
        final ChessPiece king = this.modelFactory.chessPiece(board, ChessPiece.PieceType.KING, color);
        final List<Move> moves = king.getMoves(kingPosition.getKey(), kingPosition.getValue(), true);
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                for (final Move move : moves) {
                    final boolean styleApplied = this.applyStyleIfBlockingMove(board, color,
                            kingPosition, move, row, col, cssClass);
                    if (styleApplied) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Applies CSS styling to a cell if it threatens the opposing king or blocks at least one of his potential
     * moves.
     *
     * @param board        - chess board object.
     * @param color        - color of king to examine move viability.
     * @param kingPosition - position of king on board.
     * @param move         - move to determine if a given piece can block.
     * @param row          - source row of piece to check.
     * @param col          - source column of piece to check.
     * @param cssClass     - CSS class to apply if piece meets described conditions.
     * @return - true iff styling is applied.
     */
    private boolean applyStyleIfBlockingMove(final ChessBoardModel board, final Color color,
                                             final Pair<Integer, Integer> kingPosition, final Move move,
                                             final int row, final int col, final String cssClass) {
        final Color pieceColorForCell = board.getPieceColorForCell(row, col);
        if (pieceColorForCell == Color.getOpposingColor(color)) {
            if (this.utils.isPieceThreateningCell(board, move.getDestRow(),
                    move.getDestCol(), row, col) || this.utils.isPieceThreateningCell(board,
                    kingPosition.getKey(), kingPosition.getValue(), row, col)) {
                this.updateCellStyle(row, col, cssClass, true);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the undo and redo buttons / menu items based on their current sizes.
     */
    protected void updateUndoRedoButtons() {
        final EditMenu editMenu = this.context.getEditMenu();
        final BoardHistory history = this.context.getHistory();
        editMenu.setUndoMenuItemEnabled(!history.isUndoStackEmpty());
        editMenu.setRedoMenuItemEnabled(!history.isRedoStackEmpty());
    }

    /**
     * Adds the current board to the undo stack.
     */
    protected void addBoardToUndoStack() {
        final BoardHistory history = this.context.getHistory();
        history.addToUndoStack(this.context.getBoard());
        history.clearRedoStack();
        this.updateUndoRedoButtons();
    }

    /**
     * Updates the view to match a restored model (e.g. from an undo).
     */
    protected void resetAppToMatchBoard() {
        this.clearBoard(false);
        final ChessBoardModel board = this.context.getBoard();
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final int pieceCode = board.getPieceForCell(row, col);
                final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(pieceCode);
                final Color color = board.getPieceColorForCell(row, col);
                this.updateBoardWithPiece(row, col, pieceType, color);
            }
        }
    }

}
