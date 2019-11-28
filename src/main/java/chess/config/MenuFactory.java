package chess.config;

import chess.controller.ApplicationStateContext;
import chess.view.menu.ApplicationMenu;
import chess.view.menu.ApplicationMenuSpacer;
import chess.view.menu.ContextMenu;
import chess.view.menu.EditMenu;
import chess.view.menu.FileMenu;
import chess.view.menu.HelpMenu;
import chess.view.menu.SettingsMenu;
import chess.view.menu.SystemMenu;
import chess.view.menu.button.CloseMenuButton;
import chess.view.menu.button.ContextMenuButton;
import chess.view.menu.button.MinimizeMenuButton;
import javafx.scene.control.Menu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class MenuFactory {

    private final StateFactory stateFactory;

    private final ApplicationStateContext stateContext;

    public MenuFactory(final StateFactory stateFactory,
                       final ApplicationStateContext stateContext) {
        this.stateFactory = stateFactory;
        this.stateContext = stateContext;
    }

    @Bean
    public SystemMenu systemMenu() {
        final SystemMenu systemMenu = new SystemMenu(this.minimizeMenuButton(), this.closeMenuButton());
        systemMenu.configure();
        return systemMenu;
    }

    @Bean
    public ApplicationMenu applicationMenu() {
        final ApplicationMenu applicationMenu = new ApplicationMenu(this.contextMenuButton());
        applicationMenu.configure();
        return applicationMenu;
    }

    @Bean
    public ContextMenu contextMenu() {
        final ContextMenu contextMenu = new ContextMenu(this.fileMenu(), this.editMenu(),
                this.settingsMenu(), this.helpMenu());
        contextMenu.configure();
        return contextMenu;
    }

    @Bean
    public FileMenu fileMenu() {
        final FileMenu fileMenu = new FileMenu(this.stateContext,
                this.stateFactory);
        fileMenu.configure();
        return fileMenu;
    }

    @Bean
    public EditMenu editMenu() {
        final EditMenu editMenu = new EditMenu(this.stateContext, this.stateFactory);
        editMenu.configure();
        return editMenu;
    }

    @Bean
    public Menu settingsMenu() {
        final SettingsMenu settingsMenu = new SettingsMenu();
        settingsMenu.configure();
        return settingsMenu;
    }

    @Bean
    public Menu helpMenu() {
        final HelpMenu helpMenu = new HelpMenu();
        helpMenu.configure();
        return helpMenu;
    }

    @Bean
    public ApplicationMenuSpacer applicationMenuSpacer() {
        final ApplicationMenuSpacer applicationMenuSpacer = new ApplicationMenuSpacer();
        applicationMenuSpacer.configure();
        return applicationMenuSpacer;
    }

    @Bean
    public MinimizeMenuButton minimizeMenuButton() {
        final MinimizeMenuButton minimizeMenuButton =
                new MinimizeMenuButton(this.stateContext, this.stateFactory);
        minimizeMenuButton.configure();
        return minimizeMenuButton;
    }

    @Bean
    public CloseMenuButton closeMenuButton() {
        final CloseMenuButton closeMenuButton = new CloseMenuButton(this.stateContext, this.stateFactory);
        closeMenuButton.configure();
        return closeMenuButton;
    }

    @Bean
    public ContextMenuButton contextMenuButton() {
        final ContextMenuButton contextMenuButton = new ContextMenuButton(this.contextMenu(),
                this.stateContext);
        contextMenuButton.configure();
        return contextMenuButton;
    }

}
