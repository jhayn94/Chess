package chess.controller;

import chess.state.window.ApplicationWindowState;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

@Service
public class ApplicationWindowStateContext {

    private Stage primaryStage;

    private ApplicationWindowState currentViewState;

    private ApplicationWindowState previousViewState;

    public ApplicationWindowStateContext() {
        this.currentViewState = null;
        this.previousViewState = null;
    }

    public void changeState(final ApplicationWindowState newState) {
        this.previousViewState = this.currentViewState;
        this.currentViewState = newState;
        this.currentViewState.onEnter();
    }

    public void minimizeStage() {
        this.primaryStage.setIconified(true);
    }

    public void setPrimaryStage(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
