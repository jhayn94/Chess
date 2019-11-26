package chess.view.dialog;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class represents a special stage which must be closed before the main
 * application stage can be used again. It is used for various settings screens.
 */
public class ModalStage extends Stage {

	private static final int MIN_HEIGHT = 600;

	public ModalStage() {
		super();
		this.configure();
	}

	private void configure() {
		// TODO - design ... just use the main app class?
//		final Stage mainStage = ViewController.getInstance().getStage();
//		this.initOwner(mainStage);
		this.initModality(Modality.APPLICATION_MODAL);
		this.initStyle(StageStyle.TRANSPARENT);
		this.centerOnScreen();
		this.setMinHeight(MIN_HEIGHT);
	}

}
