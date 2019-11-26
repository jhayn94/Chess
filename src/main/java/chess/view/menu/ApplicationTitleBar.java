package chess.view.menu;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * This class emulates a title bar with a left and a right grouping. It is
 * actually an HBox.
 */
@Component
public class ApplicationTitleBar extends HBox {

	public ApplicationTitleBar() {
		this.configure();
	}

	private Stage stage;

	public Stage getStage() {
		return this.stage;
	}

	private void configure() {
		this.createChildElements();
	}

	private void createChildElements() {
		final ApplicationMenu leftBar = new ApplicationMenu();
//				MenuFactory.getInstance().createApplicationMenu();
		final Region spacer = new ApplicationMenuSpacer();
//				MenuFactory.getInstance().createApplicationMenuSpacer();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		final SystemMenu rightBar = new SystemMenu();
				//MenuFactory.getInstance().createSystemMenu();
		this.getChildren().addAll(leftBar, spacer, rightBar);
	}

}
