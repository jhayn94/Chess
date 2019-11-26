package chess.view.core;

import chess.view.dialog.ModalDialog;
import chess.view.util.LabelConstants;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HelpView extends ModalDialog {

	public HelpView(final Stage stage) {
		super(stage);
		this.configure();
	}

	@Override
	protected void configure() {
		this.setTitle(LabelConstants.ABOUT);
		this.createChildElements();
	}

	@Override
	protected void createChildElements() {
		super.createChildElements();
		final TextArea helpMessageTextArea = new TextArea();
		helpMessageTextArea.setEditable(false);
		helpMessageTextArea.setWrapText(true);
		helpMessageTextArea.setText(LabelConstants.ABOUT + " TEST");
		this.setCenter(helpMessageTextArea);
	}

}
