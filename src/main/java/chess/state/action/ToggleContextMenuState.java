package chess.state.action;

import chess.controller.ApplicationStateContext;

/**
 * This class contains methods to show or hide the application context menu.
 */
public class ToggleContextMenuState extends ActionState {

    public ToggleContextMenuState(final ApplicationStateContext context) {
        super(context);
    }

    @Override
    public void onEnter() {
        this.context.getContextMenuButton().toggleContextMenuVisible();
    }

}
