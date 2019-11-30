package chess.config;

import chess.controller.ApplicationStateContext;
import chess.view.core.ApplicationRootPane;
import chess.view.core.ApplicationSideBar;
import chess.view.core.ApplicationWindow;
import chess.view.core.ChessBoardCell;
import chess.view.core.ChessBoardView;
import chess.view.core.MainApplicationView;
import chess.view.menu.ApplicationTitleBar;
import chess.view.util.ShadowRectangle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class LayoutFactory {

    private final ApplicationStateContext stateContext;

    private final StateFactory stateFactory;

    private final MenuFactory menuFactory;

    public LayoutFactory(final MenuFactory menuFactory,
                         final ApplicationStateContext stateContext, final StateFactory stateFactory) {
        this.menuFactory = menuFactory;
        this.stateContext = stateContext;
        this.stateFactory = stateFactory;
    }

    @Bean
    public ChessBoardView chessBoardView() {
        final ChessBoardView chessBoardView = new ChessBoardView(this.stateContext, this.stateFactory, this);
        chessBoardView.configure();
        return chessBoardView;
    }

    @Bean
    @Scope("prototype")
    public ChessBoardCell chessBoardCell() {
        final ChessBoardCell chessBoardCell = new ChessBoardCell(this.stateContext, this.stateFactory);
        chessBoardCell.configure();
        return chessBoardCell;
    }

    @Bean
    public ApplicationRootPane applicationRootPane() {
        final ApplicationRootPane applicationRootPane = new ApplicationRootPane(this.applicationTitleBar(), this.mainApplicationView());
        applicationRootPane.configure();
        return applicationRootPane;
    }

    @Bean
    public ApplicationWindow applicationWindow(final ApplicationRootPane applicationRootPane) {
        final ApplicationWindow rootStackPane = new ApplicationWindow(this.shadowRectangle(),
                applicationRootPane);
        rootStackPane.configure();
        return rootStackPane;
    }

    @Bean
    public ApplicationTitleBar applicationTitleBar() {
        final ApplicationTitleBar applicationTitleBar = new ApplicationTitleBar(this.menuFactory.applicationMenu(), this.menuFactory.applicationMenuSpacer(), this.menuFactory.systemMenu());
        applicationTitleBar.configure();
        return applicationTitleBar;
    }

    @Bean
    public MainApplicationView mainApplicationView() {
        final MainApplicationView mainApplicationView =
                new MainApplicationView(this.applicationSideBar(), this.chessBoardView());
        mainApplicationView.configure();
        return mainApplicationView;
    }

    @Bean
    public ApplicationSideBar applicationSideBar() {
        final ApplicationSideBar applicationSideBar = new ApplicationSideBar();
        applicationSideBar.configure();
        return applicationSideBar;
    }

    @Bean
    public ShadowRectangle shadowRectangle() {
        final ShadowRectangle shadowRectangle = new ShadowRectangle();
        shadowRectangle.configure();
        return shadowRectangle;
    }
}
