package chess.view.menu.button;

import chess.controller.ApplicationStateContext;
import chess.view.menu.ContextMenu;
import chess.view.util.ResourceConstants;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.input.MouseEvent;

public class ContextMenuButton extends AbstractMenuButton {

    private final ContextMenu contextMenu;

    private final ApplicationStateContext stateContext;

    public ContextMenuButton(final ContextMenu contextMenu, final ApplicationStateContext stateContext) {
        super(ResourceConstants.CONTEXT_MENU_ICON);
        this.contextMenu = contextMenu;
        this.stateContext = stateContext;
    }

    public void toggleContextMenuVisible() {
        if (this.contextMenu.isShowing()) {
            this.contextMenu.hide();
        } else {
            this.contextMenu.show(this, Side.BOTTOM, 0, 0);
        }
    }

    @Override
    public void configure() {
        super.configure();
        this.setOnMousePressed(this.onMousePressed());
        this.setContextMenu(this.contextMenu);
        this.stateContext.setContextMenuButton(this);
    }

    private EventHandler<? super MouseEvent> onMousePressed() {
        return event -> this.toggleContextMenuVisible();
    }
}
