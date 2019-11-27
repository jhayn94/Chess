package chess.view.menu.button;

import chess.view.util.ResourceConstants;

public class MinimizeMenuButton extends AbstractMenuButton {

	public MinimizeMenuButton() {
		super(ResourceConstants.MINIMIZE_ICON);
		this.configure();
	}

	@Override
	public void configure() {
		super.configure();
//		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickMinimize());
	}

//	private EventHandler<MouseEvent> onClickMinimize() {
//		return event -> ModelController.getInstance().transitionToMinimizedState();
//	}

}
