package chess.config;

import chess.core.ChessApplication;
import chess.view.menu.*;
import chess.view.menu.button.AbstractMenuButton;
import chess.view.menu.button.ApplicationMenuButtonType;
import javafx.scene.control.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class MenuFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChessApplication.class);

    public SystemMenu createSystemMenu() {
        return new SystemMenu();
    }


    public ApplicationMenu createApplicationMenu() {
        return new ApplicationMenu();
    }

    public ActionMenu createActionMenu() {
        return new ActionMenu();
    }


    public FileMenu createFileMenu() {
        return new FileMenu();
    }


    public EditMenu createEditMenu() {
        return new EditMenu();
    }

    public Menu createSettingsMenu() {
        return new SettingsMenu();
    }

    public Menu createHelpMenu() {
        return new HelpMenu();
    }

    public ApplicationMenuSpacer createApplicationMenuSpacer() {
        return new ApplicationMenuSpacer();
    }

    public AbstractMenuButton createApplicationMenuButton(final ApplicationMenuButtonType type) {
        try {
            return (AbstractMenuButton) type.getClassType().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
