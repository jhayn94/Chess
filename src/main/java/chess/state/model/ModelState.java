package chess.state.model;

import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.piece.ChessPiece;
import chess.view.core.ChessBoardView;

public abstract class ModelState {

    protected final ApplicationStateContext context;

    protected ModelState(final ApplicationStateContext context) {
        this.context = context;
        // Some states are invoked by clicks. So, refocus grid is called to make
        // keyboard actions always work (see ChessBoardView for more notes on why
        // this is done).
        final ChessBoardView chessBoardView = context.getChessBoardView();
        if (chessBoardView != null) {
            chessBoardView.requestFocus();
        }
    }

    public abstract void onEnter();

    protected void updateBoardWithPiece(final int row, final int col,
                                        final ChessPiece.PieceType pieceType, final Color color) {
        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        chessBoardModel.setPieceForCell(row, col, pieceType.getPieceCode(), color);
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        chessBoardView.getCell(row, col).setImage(pieceType.getResourcePath(), color);
    }

}
