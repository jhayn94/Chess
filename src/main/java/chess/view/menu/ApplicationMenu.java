package chess.view.menu;

import chess.view.menu.button.ContextMenuButton;
import javafx.scene.layout.HBox;

/**
 * A menu bar with a button / dropdown to show various actions, like saving and
 * loading.
 */
public class ApplicationMenu extends HBox {

	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	private static final int MAX_HEIGHT = 50;

	private final ContextMenuButton contextMenuButton;

	public ApplicationMenu(final ContextMenuButton contextMenuButton) {
		this.contextMenuButton = contextMenuButton;
	}

	public void configure() {
		this.getStyleClass().add(MENU_BAR_CSS_CLASS);
		this.setMaxHeight(MAX_HEIGHT);
		this.getChildren().addAll(this.contextMenuButton);
	}
}
