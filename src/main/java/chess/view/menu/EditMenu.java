package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;

public class EditMenu extends Menu {

	public EditMenu() {
		super();
		this.configure();
	}

	private void configure() {
		this.setText(LabelConstants.EDIT);
		this.createChildElements();
	}

	private void createChildElements() {
//		final MenuItem undoMenuItem = new MenuItem(LabelConstants.UNDO_LONG);
//		undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
//		undoMenuItem.setOnAction(event -> ModelController.getInstance().transitionToUndoActionState());
//		ViewController.getInstance().setUndoMenuItem(undoMenuItem);
//
//		final MenuItem redoMenuItem = new MenuItem(LabelConstants.REDO_LONG);
//		redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
//		redoMenuItem.setOnAction(event -> ModelController.getInstance().transitionToRedoActionState());
//		ViewController.getInstance().setRedoMenuItem(redoMenuItem);
//
//		this.getItems().addAll(undoMenuItem, redoMenuItem, new SeparatorMenuItem(), copyCellsMenuItem, copyGivensMenuItem,
//				pasteMenuItem, new SeparatorMenuItem(), setGivensMenuItem, unlockGivensMenuItem, new SeparatorMenuItem(),
//				restartMenuItem);
	}
}
