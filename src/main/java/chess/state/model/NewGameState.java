package chess.state.model;

import chess.controller.ApplicationStateContext;

/**
 * This class updates the state of the application when the user starts a new game, either with
 * CTRL + N, or via the context menu.
 */
public class NewGameState extends ModelState {

	public NewGameState(final ApplicationStateContext context) {
		super(context);
	}

	@Override
	public void onEnter() {
		System.out.println("DID NEW GAME");
	}

}
