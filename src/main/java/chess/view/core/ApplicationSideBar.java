package chess.view.core;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
/**
 * This class corresponds to the view on the left side of the screen. It
 * contains all other view elements on this side of the application.
 */
public class ApplicationSideBar extends VBox {

	private static final int DEFAULT_WIDTH = 320;

	private static final String CSS_STYLE_CLASS = "sudoku-side-bar";

	public ApplicationSideBar() {
		this.configure();
	}

	private void configure() {
		this.setAlignment(Pos.TOP_CENTER);
		this.getStyleClass().add(CSS_STYLE_CLASS);
		this.setMinWidth(DEFAULT_WIDTH);
		this.setMaxWidth(DEFAULT_WIDTH);
		this.createChildElements();
	}

	private void createChildElements() {
		final Label l1 = new Label("L1");
		final Label l2 =new Label("L1");
		final Label l3 = new Label("L1");
		final ObservableList<Node> children = this.getChildren();
		children.addAll(l1, l2, l3);
	}
}
