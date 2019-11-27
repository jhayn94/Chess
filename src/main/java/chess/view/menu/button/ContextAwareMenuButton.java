package chess.view.menu.button;

import chess.config.StateFactory;
import chess.controller.ApplicationViewStateContext;

/**
 * A menu button with a window context for passing requests to handle state changes.
 */
public abstract class ContextAwareMenuButton extends AbstractMenuButton {

    protected final ApplicationViewStateContext viewStateContext;

    protected final StateFactory stateFactory;

    ContextAwareMenuButton(final String resourcePath,
                           final ApplicationViewStateContext viewStateContext, final StateFactory stateFactory) {
        super(resourcePath);
        this.viewStateContext = viewStateContext;
        this.stateFactory = stateFactory;
    }

}
