package chess.view.menu;

import chess.view.util.LabelConstants;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class HelpMenu extends Menu {

    public HelpMenu() {
        super();
    }

    public void configure() {
        this.setText(LabelConstants.HELP);

        final MenuItem aboutMenuItem = new MenuItem(LabelConstants.ABOUT);
        aboutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
        aboutMenuItem.setOnAction(event -> System.out.println("ABOUT MENU ITEM"));

        final MenuItem hotkeysMenuItem = new MenuItem(LabelConstants.HOTKEYS);
        hotkeysMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.BACK_QUOTE,
                KeyCombination.CONTROL_DOWN));
        hotkeysMenuItem.setOnAction(event -> System.out.println("HOTKEYS MENU ITEM"));

        this.getItems().addAll(aboutMenuItem, hotkeysMenuItem);
    }

}
