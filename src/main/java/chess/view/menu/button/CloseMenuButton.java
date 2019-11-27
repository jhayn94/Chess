package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationWindowStateContext;
import chess.view.util.ResourceConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CloseMenuButton extends ContextAwareMenuButton {

	public CloseMenuButton(final ApplicationWindowStateContext windowStateContext, final StateFactory stateFactory) {
		super(ResourceConstants.CLOSE_ICON, windowStateContext, stateFactory);
		this.configure();
	}

	@Override
	public void configure() {
		super.configure();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickClose());
	}

	private EventHandler<MouseEvent> onClickClose() {
		return event -> this.windowStateContext.changeState(this.stateFactory.closedState());
	}
}
