package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;

public class FileMenu extends Menu {

	public FileMenu() {
		super();
		this.configure();
	}

	public void configure() {
		this.setText(LabelConstants.FILE);
	}


}
