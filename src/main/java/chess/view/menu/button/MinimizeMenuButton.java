package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationStateContext;
import chess.view.util.ResourceConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MinimizeMenuButton extends ContextAwareMenuButton {

    public MinimizeMenuButton(final ApplicationStateContext stateContext, final StateFactory stateFactory) {
        super(ResourceConstants.MINIMIZE_ICON, stateContext, stateFactory);
    }

    @Override
    public void configure() {
        super.configure();
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickMinimize());
    }

    private EventHandler<MouseEvent> onClickMinimize() {
        return event -> this.stateContext.changeState(this.stateFactory.minimizedState());
    }

}
