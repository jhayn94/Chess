package chess.state.model;

import chess.controller.ApplicationStateContext;
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
}
