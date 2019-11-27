package chess.state.window;

import chess.controller.ApplicationWindowStateContext;

public abstract class ApplicationWindowState {

    protected final ApplicationWindowStateContext context;

    protected ApplicationWindowState(final ApplicationWindowStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();
}
