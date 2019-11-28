package chess.view.menu;

import javafx.scene.control.Menu;

/**
 * Contains code to create a menu of different actions the user can do (save,
 * close, etc.).
 */
public class ContextMenu extends javafx.scene.control.ContextMenu {

	private final Menu fileMenu;

	private final Menu editMenu;

	private final Menu settingsMenu;

	private final Menu helpMenu;

	public ContextMenu(final Menu fileMenu, final Menu editMenu, final Menu settingsMenu,
					   final Menu helpMenu) {
		this.fileMenu = fileMenu;
		this.editMenu = editMenu;
		this.settingsMenu = settingsMenu;
		this.helpMenu = helpMenu;
	}

	public void configure() {
		this.getItems().addAll(this.fileMenu, this.editMenu, this.settingsMenu, this.helpMenu);
	}

}
