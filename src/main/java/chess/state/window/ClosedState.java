package chess.state.window;

import chess.controller.ApplicationWindowStateContext;
import javafx.application.Platform;

/**
 * This class contains code to run when the user closes the application.
 */
public class ClosedState extends ApplicationWindowState {

    public ClosedState(final ApplicationWindowStateContext context) {
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