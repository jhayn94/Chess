package chess.state.window;

import chess.controller.ApplicationStateContext;
import chess.state.GameState;
import javafx.application.Platform;

/**
 * This class contains code to run when the user closes the application.
 */
public class ClosedState extends GameState {

    public ClosedState(final ApplicationStateContext context) {
        super(context);
    }

    @Override
    public void onEnter() {
        Platform.runLater(() -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
