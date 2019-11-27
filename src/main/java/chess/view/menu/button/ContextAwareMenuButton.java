package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationWindowStateContext;

/**
 * A menu button with a window context for passing requests to handle state changes.
 */
public abstract class ContextAwareMenuButton extends AbstractMenuButton {

    protected final ApplicationWindowStateContext windowStateContext;

    protected final StateFactory stateFactory;

    ContextAwareMenuButton(final String resourcePath,
                           final ApplicationWindowStateContext windowStateContext, final StateFactory stateFactory) {
        super(resourcePath);
        this.windowStateContext = windowStateContext;
        this.stateFactory = stateFactory;
    }

}
