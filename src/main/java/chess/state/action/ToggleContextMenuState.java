package chess.state.action;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessModelUtils;
import chess.state.GameState;

/**
 * This class contains methods to show or hide the application context menu.
 */
public class ToggleContextMenuState extends GameState {

    public ToggleContextMenuState(final ApplicationStateContext context,
                                  final ModelFactory modelFactory, final ChessModelUtils utils) {
        super(context, utils, modelFactory);
    }

    @Override
    public void onEnter() {
        this.context.getContextMenuButton().toggleContextMenuVisible();
    }

}
