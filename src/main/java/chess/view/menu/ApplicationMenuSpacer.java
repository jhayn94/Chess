package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * This class fills the gap between two elements in an HBox. It is used by
 * ApplicationTitleBar to have a left and a right menu.
 */
public class ApplicationMenuSpacer extends Region {

	private static final int MAX_HEIGHT = 50;

	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	private Label title;

	public void configure() {
		this.setMaxHeight(MAX_HEIGHT);
		this.getStyleClass().add(MENU_BAR_CSS_CLASS);
		this.title = new Label(LabelConstants.APPLICATION_TITLE);
		this.title.setPadding(new Insets(10, 0, 0, 0));
		this.getChildren().add(this.title);
	}

	public void setTitle(final String newTitle) {
		this.title.setText(newTitle);
	}
}
