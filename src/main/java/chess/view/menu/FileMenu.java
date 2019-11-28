package chess.view.menu;

import chess.config.StateFactory;
import chess.controller.ApplicationStateContext;
import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class FileMenu extends Menu {

    private final ApplicationStateContext stateContext;

    private final StateFactory stateFactory;

    public FileMenu(final ApplicationStateContext stateContext,
                    final StateFactory stateFactory) {
        super();
        this.stateContext = stateContext;
        this.stateFactory = stateFactory;
    }

    public void configure() {
        this.setText(LabelConstants.FILE);
        this.createChildElements();
    }

    private void createChildElements() {
        final MenuItem newGameMenuItem = new MenuItem(LabelConstants.NEW_GAME);
        newGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newGameMenuItem.setOnAction(event -> this.stateContext.changeState(this.stateFactory.newGameState()));

        final MenuItem closeMenuItem = new MenuItem(LabelConstants.CLOSE);
        closeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
        closeMenuItem.setOnAction(event -> this.stateContext.changeState(this.stateFactory.closedState()));

        this.getItems().addAll(newGameMenuItem, new SeparatorMenuItem(), closeMenuItem);
    }

}
