package chess.view.menu;

import chess.view.menu.button.CloseMenuButton;
import chess.view.menu.button.MinimizeMenuButton;
import javafx.scene.layout.HBox;

/**
 * A menu bar with buttons to minimize, maximum, and close the window.
 */
public class SystemMenu extends HBox {

	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	private static final int MAX_HEIGHT = 50;

	private final MinimizeMenuButton minimizeMenuButton;

    private final CloseMenuButton closeMenuButton;

    public SystemMenu(final MinimizeMenuButton minimizeMenuButton, final CloseMenuButton closeMenuButton) {
        this.minimizeMenuButton = minimizeMenuButton;
        this.closeMenuButton = closeMenuButton;
    }

    public void configure() {
        this.getStyleClass().add(MENU_BAR_CSS_CLASS);
        this.setMaxHeight(MAX_HEIGHT);
        this.getChildren().addAll(this.minimizeMenuButton, this.closeMenuButton);
    }

}
