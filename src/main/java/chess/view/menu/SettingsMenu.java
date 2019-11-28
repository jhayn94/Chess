package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class SettingsMenu extends Menu {

    public SettingsMenu() {
        super();
    }

    public void configure() {
        this.setText(LabelConstants.SETTINGS);
        this.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN));

        final MenuItem aiSettingsMenuItem = new MenuItem(LabelConstants.AI);
        aiSettingsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A,
                KeyCombination.CONTROL_DOWN));
        aiSettingsMenuItem.setOnAction(event -> System.out.println("AI SETTINGS MENU ITEM"));

        this.getItems().addAll(aiSettingsMenuItem);
    }

}
