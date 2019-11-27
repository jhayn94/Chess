package chess.view.core;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
/**
 * This class corresponds to the view on the left side of the screen. It
 * contains all other view elements on this side of the application.
 */
public class ApplicationSideBar extends VBox {

	private static final int DEFAULT_WIDTH = 220;

	private static final String CSS_STYLE_CLASS = "chess-side-bar";

//	public ApplicationSideBar() {
//		this.configure();
//	}

	public void configure() {
		this.setAlignment(Pos.TOP_CENTER);
		this.getStyleClass().add(CSS_STYLE_CLASS);
		this.setMinWidth(DEFAULT_WIDTH);
		this.setMaxWidth(DEFAULT_WIDTH);
	}

}
