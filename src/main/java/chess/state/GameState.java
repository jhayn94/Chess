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

    protected void clearBoard() {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                this.clearCell(row, col);
            }
        }
    }

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

    protected void clearCell(final int row, final int col) {
        final ChessPiece.PieceType emptyPiece = ChessPiece.PieceType.NONE;
        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        chessBoardModel.setPieceForCell(row, col, emptyPiece.getPieceCode(), Color.NONE);
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        chessBoardView.getCell(row, col).setImage(emptyPiece.getResourcePath(), Color.NONE);
    }

    protected void updateBoardWithPiece(final int row, final int col,
                                        final ChessPiece.PieceType pieceType, final Color color) {
        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        chessBoardModel.setPieceForCell(row, col, pieceType.getPieceCode(), color);
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        chessBoardView.getCell(row, col).setImage(pieceType.getResourcePath(), color);
    }

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

    protected void setKingInCheckStyle(final ChessBoardModel board, final Color colorInCheck) {
        final Pair<Integer, Integer> kingPosition = this.utils.getKingPosition(board, colorInCheck);
        this.updateCellStyle(kingPosition.getKey(), kingPosition.getValue(),
                ChessBoardCell.IN_CHECK_CELL_CSS_CLASS, true);
    }

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
