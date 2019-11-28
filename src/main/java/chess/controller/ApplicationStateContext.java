package chess.controller;

import chess.state.action.ActionState;
import chess.state.model.ModelState;
import chess.state.window.WindowState;
import chess.view.menu.EditMenu;
import chess.view.menu.button.ContextMenuButton;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStateContext {

    private Stage primaryStage;

    private ModelState currentModelState;

    private ModelState previousModelState;

    private WindowState currentViewState;

    private ActionState currentActionState;

    private EditMenu editMenu;

    private ContextMenuButton contextMenuButton;

    public ApplicationStateContext() {
        this.currentModelState = null;
        this.previousModelState = null;
        this.currentViewState = null;
        this.currentActionState = null;
    }

    public void changeState(final ModelState newState) {
        this.previousModelState = this.currentModelState;
        this.currentModelState = newState;
        this.currentModelState.onEnter();
    }

    public void changeState(final WindowState newState) {
        this.currentViewState = newState;
        this.currentViewState.onEnter();
    }

    public void changeState(final ActionState newState) {
        this.currentActionState = newState;
        this.currentActionState.onEnter();
    }

    public void setPrimaryStage(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void setEditMenu(final EditMenu editMenu) {
        this.editMenu = editMenu;
    }

    public void setContextMenuButton(final ContextMenuButton contextMenuButton) {
        this.contextMenuButton = contextMenuButton;
    }

    public ContextMenuButton getContextMenuButton() {
        return this.contextMenuButton;
    }

    public EditMenu getEditMenu() {
        return this.editMenu;
    }
}
