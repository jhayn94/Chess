package chess.state.window;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessModelUtils;
import chess.state.GameState;
import javafx.application.Platform;

public class MinimizedState extends GameState {

    public MinimizedState(final ApplicationStateContext context,
                          final ModelFactory modelFactory, final ChessModelUtils utils) {
        super(context, utils, modelFactory);
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
