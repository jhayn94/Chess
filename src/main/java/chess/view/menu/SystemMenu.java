package chess.view.menu;

import chess.view.menu.button.CloseMenuButton;
import chess.view.menu.button.MaximizeMenuButton;
import chess.view.menu.button.MinimizeMenuButton;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/** A menu bar with buttons to minimize, maximum, and close the window. */
public class SystemMenu extends HBox {

	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	public SystemMenu() {
		this.configure();
	}

	private void configure() {
		this.getStyleClass().add(MENU_BAR_CSS_CLASS);
		this.setMaxHeight(50);
		this.createChildElements();
	}

	private void createChildElements() {
		final Button minimizeMenuButton = new MinimizeMenuButton();

//				MenuFactory.getInstance()
//				.createApplicationMenuButton(ApplicationMenuButtonType.MINIMIZE);
		final Button maximizeButton =  new MaximizeMenuButton();
//		MenuFactory.getInstance()
//				.createApplicationMenuButton(ApplicationMenuButtonType.MAXIMIZE);
		final Button closeMenuButton =  new CloseMenuButton();
//				MenuFactory.getInstance()
//				.createApplicationMenuButton(ApplicationMenuButtonType.CLOSE);
		this.getChildren().addAll(minimizeMenuButton, maximizeButton, closeMenuButton);
	}

}
