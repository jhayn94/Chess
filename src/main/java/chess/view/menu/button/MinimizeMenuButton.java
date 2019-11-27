package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationWindowStateContext;
import chess.view.util.ResourceConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MinimizeMenuButton extends ContextAwareMenuButton {

    public MinimizeMenuButton(final ApplicationWindowStateContext windowStateContext, final StateFactory stateFactory) {
        super(ResourceConstants.MINIMIZE_ICON, windowStateContext, stateFactory);
    }

    @Override
    public void configure() {
        super.configure();
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickMinimize());
    }

    private EventHandler<MouseEvent> onClickMinimize() {
        return event -> this.windowStateContext.changeState(this.stateFactory.minimizedState());
    }

}
