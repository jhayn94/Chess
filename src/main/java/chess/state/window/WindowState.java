package chess.state.window;

import chess.controller.ApplicationStateContext;

public abstract class WindowState {

    protected final ApplicationStateContext context;

    protected WindowState(final ApplicationStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();
}
