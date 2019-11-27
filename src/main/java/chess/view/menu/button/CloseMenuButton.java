package chess.view.menu.button;

import chess.view.util.ResourceConstants;

public class CloseMenuButton extends AbstractMenuButton {

	public CloseMenuButton() {
		super(ResourceConstants.CLOSE_ICON);
		this.configure();
	}

	@Override
	public void configure() {
		super.configure();
//		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickClose());
	}

//	private EventHandler<MouseEvent> onClickClose() {
//		return event -> ModelController.getInstance().transitionToClosedState();
//	}
}
