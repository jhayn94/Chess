package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;

public class SettingsMenu extends Menu {

	public SettingsMenu() {
		super();
		this.configure();
	}

	private void configure() {
		this.setText(LabelConstants.SETTINGS);
		this.createChildElements();
	}

	private void createChildElements() {

	}
}
