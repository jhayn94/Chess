package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;

public class HelpMenu extends Menu {

	public HelpMenu() {
		super();
		this.configure();
	}

	private void configure() {
		this.setText(LabelConstants.HELP);
		this.createChildElements();
	}

	private void createChildElements() {
//		final MenuItem aboutMenuItem = new MenuItem(LabelConstants.ABOUT);
//		aboutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
//		aboutMenuItem.setOnAction(event -> LayoutFactory.getInstance().showHelpView());
//
//		this.getItems().addAll(aboutMenuItem, hotkeysMenuItem);
	}
}
