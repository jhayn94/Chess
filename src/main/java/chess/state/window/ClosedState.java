package chess.state.window;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessModelUtils;
import chess.state.GameState;
import javafx.application.Platform;

/**
 * This class contains code to run when the user closes the application.
 */
public class ClosedState extends GameState {

    public ClosedState(final ApplicationStateContext context,
                       final ModelFactory modelFactory, final ChessModelUtils utils) {
        super(context, utils, modelFactory);
    }

    @Override
    public void onEnter() {
        Platform.runLater(() -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
