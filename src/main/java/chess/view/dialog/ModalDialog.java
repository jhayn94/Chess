package chess.view.dialog;

import chess.view.menu.ApplicationMenuSpacer;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ModalDialog extends BorderPane {

	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	private final Stage stage;

	private ApplicationMenuSpacer applicationMenuSpacer;

	public ModalDialog(final Stage stage) {
		super();
		this.stage = stage;
		this.createCloseButton();
	}

	public void setTitle(final String title) {
		this.applicationMenuSpacer.setTitle(title);
	}

	protected void configure() {
		// Nothing to do; only sub-classes will use this.
	}

	protected void createChildElements() {
		// Nothing to do; only sub-classes will use this.
	}

	private void createCloseButton() {
//		final AbstractMenuButton closeButton = MenuFactory.getInstance()
//				.createApplicationMenuButton(ApplicationMenuButtonType.CLOSE);
//		closeButton.setOnAction(event -> this.stage.close());
//		this.applicationMenuSpacer = MenuFactory.getInstance().createApplicationMenuSpacer();
//		final HBox systemMenuBar = new HBox();
//		systemMenuBar.getStyleClass().add(MENU_BAR_CSS_CLASS);
//		HBox.setHgrow(this.applicationMenuSpacer, Priority.SOMETIMES);
//		this.setTop(systemMenuBar);
//		systemMenuBar.getChildren().addAll(this.applicationMenuSpacer, closeButton);
	}

	public Stage getStage() {
		return stage;
	}

}
