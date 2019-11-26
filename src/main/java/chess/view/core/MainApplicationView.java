package chess.view.core;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

/**
 * This class represents the main content view of the application.
 */
public class MainApplicationView extends SplitPane {

	private static final String CSS_CLASS = "chess-main-app-view";

	public MainApplicationView() {
		super();
		this.configure();
	}

	private void configure() {
		this.getStyleClass().add(CSS_CLASS);
		this.setOrientation(Orientation.HORIZONTAL);
		this.createChildElements();
	}

	private void createChildElements() {
		// TODO - factory!
		// CHESS BOARD VIEW HERE
		final ApplicationSideBar sideBarView = new ApplicationSideBar();
		this.getItems().addAll(sideBarView);
	}

}
