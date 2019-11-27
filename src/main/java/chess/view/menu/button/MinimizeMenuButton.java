package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationViewStateContext;
import chess.view.util.ResourceConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MinimizeMenuButton extends ContextAwareMenuButton {

    public MinimizeMenuButton(final ApplicationViewStateContext viewStateContext, final StateFactory stateFactory) {
        super(ResourceConstants.MINIMIZE_ICON, viewStateContext, stateFactory);
    }

    @Override
    public void configure() {
        super.configure();
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickMinimize());
    }

    private EventHandler<MouseEvent> onClickMinimize() {
        return event -> this.viewStateContext.changeState(this.stateFactory.minimizedState());
    }

}
