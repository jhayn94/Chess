package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationStateContext;

/**
 * A menu button with a window context for passing requests to handle state changes.
 */
public abstract class ContextAwareMenuButton extends AbstractMenuButton {

    protected final ApplicationStateContext stateContext;

    protected final StateFactory stateFactory;

    ContextAwareMenuButton(final String resourcePath,
                           final ApplicationStateContext stateContext,
                           final StateFactory stateFactory) {
        super(resourcePath);
        this.stateContext = stateContext;
        this.stateFactory = stateFactory;
    }

}
