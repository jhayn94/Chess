package chess.state.model;

import chess.controller.ApplicationModelStateContext;

public abstract class ApplicationModelState {

    protected final ApplicationModelStateContext context;

    protected ApplicationModelState(final ApplicationModelStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();
}
