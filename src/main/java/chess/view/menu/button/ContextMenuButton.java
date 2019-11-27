package chess.view.menu.button;

import chess.view.menu.ContextMenu;
import chess.view.util.ResourceConstants;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.input.MouseEvent;

public class ContextMenuButton extends AbstractMenuButton {

    private final ContextMenu contextMenu;

    public ContextMenuButton(final ContextMenu contextMenu) {
        super(ResourceConstants.CONTEXT_MENU_ICON);
        this.contextMenu = contextMenu;
    }

    public void toggleContextMenu() {
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
//		ViewController.getInstance().setContextMenuButton(this);
        this.setContextMenu(this.contextMenu);
    }

    private EventHandler<? super MouseEvent> onMousePressed() {
        return event -> this.toggleContextMenu();
    }
}
