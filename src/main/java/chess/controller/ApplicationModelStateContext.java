package chess.controller;

import chess.state.model.ApplicationModelState;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

@Service
public class ApplicationModelStateContext {

    private Stage primaryStage;

    private ApplicationModelState currentModelState;

    private ApplicationModelState previousModelState;

    public ApplicationModelStateContext() {
        this.currentModelState = null;
        this.previousModelState = null;
    }

    public void changeState(final ApplicationModelState newState) {
        this.previousModelState = this.currentModelState;
        this.currentModelState = newState;
        this.currentModelState.onEnter();
    }

}
