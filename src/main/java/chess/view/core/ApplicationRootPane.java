package chess.view.core;

import chess.config.LayoutFactory;
import chess.view.menu.ApplicationTitleBar;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class represents the root element of the application. It extends {@link BorderPane}, for designating top, bottom, middle (etc.) elements.
 */
public class ApplicationRootPane extends BorderPane {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRootPane.class);

	// TODO - how to use?
	@Autowired
	private LayoutFactory layoutFactory;

	private final ApplicationTitleBar applicationTitleBar;

	private final MainApplicationView mainApplicationView;

	public ApplicationRootPane() {
		this.applicationTitleBar = new ApplicationTitleBar();
		this.mainApplicationView = new MainApplicationView();
		this.configure();
	}

	private void configure() {
		this.setTop(this.applicationTitleBar);
		this.setCenter(this.mainApplicationView);
	}

}
