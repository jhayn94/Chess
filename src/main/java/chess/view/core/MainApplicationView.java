package chess.view.core;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

/**
 * This class represents the main content view of the application.
 */
public class MainApplicationView extends SplitPane {

	private static final String CSS_CLASS = "chess-main-app-view";

	private final ApplicationSideBar applicationSideBar;

	private final ChessBoardView chessBoardView;

	public MainApplicationView(final ApplicationSideBar applicationSideBar, final ChessBoardView chessBoardView) {
		super();
		this.applicationSideBar = applicationSideBar;
		this.chessBoardView = chessBoardView;
	}

	public void configure() {
		// TODO - can we delete this and use the app root pane's .setLeft method for sidebar?
		this.getStyleClass().add(CSS_CLASS);
		this.setOrientation(Orientation.HORIZONTAL);
		this.getItems().addAll(this.applicationSideBar, this.chessBoardView);
		// CHESS BOARD VIEW HERE
	}


}
