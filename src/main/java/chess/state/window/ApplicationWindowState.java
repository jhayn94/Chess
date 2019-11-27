package chess.state.window;

import chess.controller.ApplicationViewStateContext;

public abstract class ApplicationWindowState {

    protected final ApplicationViewStateContext context;

    protected ApplicationWindowState(final ApplicationViewStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();
}
