package chess.view.menu;

import chess.config.StateFactory;
import chess.controller.ApplicationStateContext;
import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class EditMenu extends Menu {

    private final ApplicationStateContext stateContext;

    private final StateFactory stateFactory;

    private MenuItem undoMenuItem;

    private MenuItem redoMenuItem;

    public EditMenu(final ApplicationStateContext stateContext, final StateFactory stateFactory) {
        super();
        this.stateContext = stateContext;
        this.stateFactory = stateFactory;
    }

    public void configure() {
        this.setText(LabelConstants.EDIT);
        this.stateContext.setEditMenu(this);
        this.createChildElements();
    }

    private void createChildElements() {
        this.undoMenuItem = new MenuItem(LabelConstants.UNDO_LONG);
        this.undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        this.undoMenuItem.setOnAction(event -> this.stateContext.changeState(this.stateFactory.undoState()));

        this.redoMenuItem = new MenuItem(LabelConstants.REDO_LONG);
        this.redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        this.redoMenuItem.setOnAction(event -> this.stateContext.changeState(this.stateFactory.redoState()));

        this.getItems().addAll(this.undoMenuItem, this.redoMenuItem);

    }

    public void setUndoMenuItemEnabled(final boolean enabled) {
        this.undoMenuItem.setDisable(!enabled);
    }

    public void setRedoMenuItemEnabled(final boolean enabled) {
        this.redoMenuItem.setDisable(!enabled);
    }
}
