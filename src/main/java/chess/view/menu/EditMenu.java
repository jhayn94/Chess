package chess.view.menu;

import chess.config.StateFactory;
import chess.controller.ApplicationModelStateContext;
import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class EditMenu extends Menu {

    private final ApplicationModelStateContext modelStateContext;

    private final StateFactory stateFactory;

    public EditMenu(final ApplicationModelStateContext modelStateContext, final StateFactory stateFactory) {
        super();
        this.modelStateContext = modelStateContext;
        this.stateFactory = stateFactory;
    }

    public void configure() {
        this.setText(LabelConstants.EDIT);
        this.createChildElements();
    }

    private void createChildElements() {
        final MenuItem undoMenuItem = new MenuItem(LabelConstants.UNDO_LONG);
        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undoMenuItem.setOnAction(event -> this.modelStateContext.changeState(this.stateFactory.undoState()));
        final MenuItem redoMenuItem = new MenuItem(LabelConstants.REDO_LONG);
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
		redoMenuItem.setOnAction(event -> this.modelStateContext.changeState(this.stateFactory.redoState()));

		// TODO - eventually these will need to be set in the view state context.
		//		ViewController.getInstance().setUndoMenuItem(undoMenuItem);
//		ViewController.getInstance().setRedoMenuItem(redoMenuItem);
        this.getItems().addAll(undoMenuItem, redoMenuItem);
    }
}
