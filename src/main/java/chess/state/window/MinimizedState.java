package chess.state.window;

import chess.controller.ApplicationStateContext;
import chess.state.GameState;
import javafx.application.Platform;

public class MinimizedState extends GameState {


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
