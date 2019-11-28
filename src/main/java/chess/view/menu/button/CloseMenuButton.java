package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationStateContext;
import chess.view.util.ResourceConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CloseMenuButton extends ContextAwareMenuButton {

	public CloseMenuButton(final ApplicationStateContext stateContext, final StateFactory stateFactory) {
		super(ResourceConstants.CLOSE_ICON, stateContext, stateFactory);
		this.configure();
	}

	@Override
	public void configure() {
		super.configure();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickClose());
	}

	private EventHandler<MouseEvent> onClickClose() {
		return event -> this.stateContext.changeState(this.stateFactory.closedState());
	}
}
