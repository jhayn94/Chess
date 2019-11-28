package chess.state.model;

import chess.controller.ApplicationStateContext;

public abstract class ModelState {

    protected final ApplicationStateContext context;

    protected ModelState(final ApplicationStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();
}
