package chess.state.window;

import chess.controller.ApplicationWindowStateContext;
import javafx.application.Platform;

public class MinimizedState extends ApplicationWindowState {


    public MinimizedState(final ApplicationWindowStateContext context) {
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
        this.context.minimizeStage();
    }

}
