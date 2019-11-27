package chess.view.menu;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * This class emulates a title bar with a left and a right grouping. It is
 * actually an HBox.
 */
@Component
public class ApplicationTitleBar extends HBox {

    private final ApplicationMenu applicationMenunu;

    private final ApplicationMenuSpacer spacer;

    private final SystemMenu systemMenu;

    public ApplicationTitleBar(final ApplicationMenu applicationMenu, final ApplicationMenuSpacer spacer,
                               final SystemMenu systemMenu) {
        this.applicationMenunu = applicationMenu;
        this.spacer = spacer;
        this.systemMenu = systemMenu;
    }

    private Stage stage;

    public Stage getStage() {
        return this.stage;
    }

    public void configure() {
        HBox.setHgrow(this.spacer, Priority.SOMETIMES);
        this.getChildren().addAll(this.applicationMenunu, this.spacer, this.systemMenu);
    }

}
