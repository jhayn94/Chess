package chess.state;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.model.Color;
import chess.model.Move;
import chess.model.piece.ChessPiece;
import chess.view.core.ChessBoardCell;
import chess.view.core.ChessBoardView;
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
    protected void clearBoard() {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                this.clearCell(row, col);
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
                }
                this.updateCellStyle(row, col, ChessBoardCell.HIGHLIGHTED_CELL_CSS_CLASS, false);
            }
        }
    }

    /**
     * Clears a single cell of its piece.
     * @param row - row to clear.
     * @param col - column to clear.
     */
    protected void clearCell(final int row, final int col) {
        final ChessPiece.PieceType emptyPiece = ChessPiece.PieceType.NONE;
        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        chessBoardModel.setPieceForCell(row, col, emptyPiece.getPieceCode(), Color.NONE);
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        chessBoardView.getCell(row, col).setImage(emptyPiece.getResourcePath(), Color.NONE);
    }

    /**
     *  Places a piece at the given location.
     * @param row - row to use when placing piece.
     * @param col - column to use when placing piece.
     * @param pieceType - type of piece to place (see ChessPiece.PieceType).
     * @param color - color of piece to place.
     */
    protected void updateBoardWithPiece(final int row, final int col,
                                        final ChessPiece.PieceType pieceType, final Color color) {
        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        chessBoardModel.setPieceForCell(row, col, pieceType.getPieceCode(), color);
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        chessBoardView.getCell(row, col).setImage(pieceType.getResourcePath(), color);
    }

    /**
     * Updates the style of the given cell on the board.
     * @param row - row to modify style of.
     * @param col - column to modify style of.
     * @param cssClass - CSS selector to add or remove.
     * @param add - if true, adds the given selector. Otherwise, removes the selector.
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
     * @param board - chess board object.
     * @param colorInCheck - color of king to use.
     */
    protected void setKingInCheckStyle(final ChessBoardModel board, final Color colorInCheck) {
        final Pair<Integer, Integer> kingPosition = this.utils.getKingPosition(board, colorInCheck);
        this.updateCellStyle(kingPosition.getKey(), kingPosition.getValue(),
                ChessBoardCell.IN_CHECK_CELL_CSS_CLASS, true);
    }

    /**
     * Sets the style on the king cell to indicate he is in check. Adds style to the king and any piece
     * threatening a cell adjacent to him.
     * @param board - chess board object.
     * @param colorInCheckmate - color of king to use.
     */
    protected void setKingInCheckmateStyle(final ChessBoardModel board, final Color colorInCheckmate) {
        this.setKingInCheckStyle(board, colorInCheckmate);
        final Pair<Integer, Integer> kingPosition = this.utils.getKingPosition(board, colorInCheckmate);
        final ChessPiece king = this.modelFactory.chessPiece(board, ChessPiece.PieceType.KING, colorInCheckmate);
        final List<Move> moves = king.getMoves(kingPosition.getKey(), kingPosition.getValue(), true);
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                for (final Move move : moves) {
                    final boolean styleApplied = this.applyCheckmateStyleIfBlockingMove(board, colorInCheckmate,
                            kingPosition, move, row, col);
                    if (styleApplied) {
                        break;
                    }
                }
            }
        }
    }

    private boolean applyCheckmateStyleIfBlockingMove(final ChessBoardModel board, final Color colorInCheckmate,
                                                      final Pair<Integer, Integer> kingPosition, final Move move,
                                                      final int row, final int col) {
        final Color pieceColorForCell = board.getPieceColorForCell(row, col);
        if (pieceColorForCell == Color.getOpposingColor(colorInCheckmate)) {
            if (this.utils.isPieceThreateningCell(board, move.getDestRow(),
                    move.getDestCol(), row, col) || this.utils.isPieceThreateningCell(board,
                    kingPosition.getKey(), kingPosition.getValue(), row, col)) {
                this.updateCellStyle(row, col, ChessBoardCell.IN_CHECK_CELL_CSS_CLASS, true);
                return true;
            }
        }
        return false;
    }
}
