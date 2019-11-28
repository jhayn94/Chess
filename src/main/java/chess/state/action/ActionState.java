package chess.state.action;

import chess.controller.ApplicationStateContext;

public abstract class ActionState {

    protected final ApplicationStateContext context;

    protected ActionState(final ApplicationStateContext context) {
        this.context = context;
    }

    public abstract void onEnter();
}
