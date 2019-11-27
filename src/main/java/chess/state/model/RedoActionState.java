package chess.state.model;

import chess.controller.ApplicationModelStateContext;

/**
 * This class updates the state of the application when the user invokes a
 * "redo", either through the keyboard or a button press in the UI.
 */
public class RedoActionState extends ApplicationModelState {

	public RedoActionState(final ApplicationModelStateContext context) {
		super(context);
	}

	@Override
	public void onEnter() {
		System.out.println("DID REDO");
	}

}