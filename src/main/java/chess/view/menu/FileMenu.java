package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;

public class FileMenu extends Menu {

	public FileMenu() {
		super();
		this.configure();
	}

	private void configure() {
		this.setText(LabelConstants.FILE);
		this.createChildElements();
	}

	private void createChildElements() {

	}

}
