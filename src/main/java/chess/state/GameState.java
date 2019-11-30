package chess.state;

import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.piece.ChessPiece;
import chess.view.core.ChessBoardView;

public abstract class GameState {

    protected final ApplicationStateContext context;

    protected GameState(final ApplicationStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();

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
}
