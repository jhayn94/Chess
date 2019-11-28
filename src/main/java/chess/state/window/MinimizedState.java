package chess.state.window;

import chess.controller.ApplicationStateContext;
import javafx.application.Platform;

public class MinimizedState extends WindowState {


    public MinimizedState(final ApplicationStateContext context) {
        super(context);
    }

    @Override
    public void onEnter() {
        if (Platform.isFxApplicationThread()) {
            this.doMinimize();
        } else {
            Platform.runLater(this::doMinimize);
        }
    }

    private void doMinimize() {
        this.context.getPrimaryStage().setIconified(true);
    }

}
