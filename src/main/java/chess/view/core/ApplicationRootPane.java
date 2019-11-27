package chess.view.core;

import chess.view.menu.ApplicationTitleBar;
import javafx.scene.layout.BorderPane;

/**
 * This class represents the root element of the application. It extends {@link BorderPane}, for
 * designating top, bottom, middle (etc.) elements.
 */
public class ApplicationRootPane extends BorderPane {

    private final ApplicationTitleBar applicationTitleBar;

    private final MainApplicationView mainApplicationView;

    public ApplicationRootPane(final ApplicationTitleBar applicationTitleBar,
                               final MainApplicationView mainAppView) {
        this.applicationTitleBar = applicationTitleBar;
        this.mainApplicationView = mainAppView;
    }

    public void configure() {
        this.setTop(this.applicationTitleBar);
        this.setCenter(this.mainApplicationView);
    }

}
