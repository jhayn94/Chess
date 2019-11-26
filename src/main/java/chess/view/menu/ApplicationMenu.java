package chess.view.menu;

import chess.view.menu.button.ContextMenuButton;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * A menu bar with a button / dropdown to show various actions, like saving and
 * loading.
 */
public class ApplicationMenu extends HBox {

	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	public ApplicationMenu() {
		this.configure();
	}

	private void configure() {
		this.getStyleClass().add(MENU_BAR_CSS_CLASS);
		this.setMaxHeight(50);
		this.createChildElements();
	}

	private void createChildElements() {
		final Button contextButton = new ContextMenuButton();
//				MenuFactory.getInstance()
//				.createApplicationMenuButton(ApplicationMenuButtonType.CONTEXT);
		this.getChildren().addAll(contextButton);
	}
}
