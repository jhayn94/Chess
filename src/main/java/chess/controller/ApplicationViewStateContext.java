package chess.controller;

import chess.state.window.ApplicationWindowState;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

@Service
public class ApplicationViewStateContext {

    private Stage primaryStage;

    private ApplicationWindowState currentViewState;


    public ApplicationViewStateContext() {
        this.currentViewState = null;
    }

    public void changeState(final ApplicationWindowState newState) {
        this.currentViewState = newState;
        this.currentViewState.onEnter();
    }


    public void setPrimaryStage(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}
