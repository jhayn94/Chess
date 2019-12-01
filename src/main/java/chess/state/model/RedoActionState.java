package chess.state.model;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessModelUtils;
import chess.state.GameState;

/**
 * This class updates the state of the application when the user invokes a
 * "redo", either through the keyboard or a button press in the UI.
 */
public class RedoActionState extends GameState {

	public RedoActionState(final ApplicationStateContext context,
						   final ModelFactory modelFactory, final ChessModelUtils utils) {
		super(context, utils, modelFactory);
	}

	@Override
	public void onEnter() {
		System.out.println("DID REDO");
	}

}
