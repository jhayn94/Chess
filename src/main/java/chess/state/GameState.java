package chess.state;

import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.piece.ChessPiece;
import chess.view.core.ChessBoardCell;
import chess.view.core.ChessBoardView;
import javafx.collections.ObservableList;

public abstract class GameState {

    protected final ApplicationStateContext context;

    protected GameState(final ApplicationStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();

    protected void clearBoard() {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                this.clearCell(row, col);
            }
        }
    }

    protected void clearHighlightedCells() {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
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
        if (add) {
            styleClass.add(cssClass);
        } else {
            styleClass.remove(cssClass);
        }
    }

    protected ChessBoardModel applyMoveToCopiedBoard(final int rowToSet, final int colToSet,
                                                     final int rowToClear, final int colToClear,
                                                     final ChessBoardModel chessBoardModel,
                                                     final Color selectedPieceColor,
                                                     final ChessPiece.PieceType pieceType) {
        final ChessBoardModel tempChessBoard = chessBoardModel.createCopy();
        tempChessBoard.setPieceForCell(rowToSet, colToSet, pieceType.getPieceCode(), selectedPieceColor);
        tempChessBoard.setPieceForCell(rowToClear, colToClear, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
        return tempChessBoard;
    }
}
