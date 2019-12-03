package chess.view.menu;

import javafx.scene.control.Menu;

/**
 * Contains code to create a menu of different actions the user can do (save,
 * close, etc.).
 */
public class ContextMenu extends javafx.scene.control.ContextMenu {

    private final Menu fileMenu;

    private final Menu editMenu;

    public ContextMenu(final Menu fileMenu, final Menu editMenu) {
        this.fileMenu = fileMenu;
        this.editMenu = editMenu;
    }

    public void configure() {
        this.getItems().addAll(this.fileMenu, this.editMenu);
    }

}
