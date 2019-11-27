package chess.view.menu;

import chess.config.StateFactory;
import chess.controller.ApplicationModelStateContext;
import chess.controller.ApplicationViewStateContext;
import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class FileMenu extends Menu {

    private final ApplicationViewStateContext viewStateContext;

    private final ApplicationModelStateContext modelStateContext;

    private final StateFactory stateFactory;

    public FileMenu(final ApplicationViewStateContext viewStateContext,
                    final ApplicationModelStateContext modelStateContext,
                    final StateFactory stateFactory) {
        super();
        this.viewStateContext = viewStateContext;
        this.modelStateContext = modelStateContext;
        this.stateFactory = stateFactory;
    }

    public void configure() {
        this.setText(LabelConstants.FILE);
        this.createChildElements();
    }

    private void createChildElements() {
        final MenuItem newGameMenuItem = new MenuItem(LabelConstants.NEW_GAME);
        newGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newGameMenuItem.setOnAction(event -> this.modelStateContext.changeState(this.stateFactory.newGameState()));

        final MenuItem closeMenuItem = new MenuItem(LabelConstants.CLOSE);
        closeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
        closeMenuItem.setOnAction(event -> this.viewStateContext.changeState(this.stateFactory.closedState()));

        this.getItems().addAll(newGameMenuItem, new SeparatorMenuItem(), closeMenuItem);
    }

}
