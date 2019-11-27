package chess.config;

import chess.view.core.ApplicationRootPane;
import chess.view.core.ApplicationSideBar;
import chess.view.core.MainApplicationView;
import chess.view.menu.ApplicationTitleBar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class LayoutFactory {

    private final MenuFactory menuFactory;

    public LayoutFactory(final MenuFactory menuFactory) {
        this.menuFactory = menuFactory;
    }

    @Bean
    public ApplicationRootPane applicationRootPane() {
        final ApplicationRootPane applicationRootPane = new ApplicationRootPane(this.applicationTitleBar(), this.mainApplicationView());
        applicationRootPane.configure();
        return applicationRootPane;
    }

    @Bean
    public ApplicationTitleBar applicationTitleBar() {
        final ApplicationTitleBar applicationTitleBar = new ApplicationTitleBar(this.menuFactory.applicationMenu(), this.menuFactory.applicationMenuSpacer(), this.menuFactory.systemMenu());
        applicationTitleBar.configure();
        return applicationTitleBar;
    }

    @Bean
    public MainApplicationView mainApplicationView() {
        final MainApplicationView mainApplicationView = new MainApplicationView(this.applicationSideBar());
        mainApplicationView.configure();
        return mainApplicationView;
    }

    @Bean
    public ApplicationSideBar applicationSideBar() {
        final ApplicationSideBar applicationSideBar = new ApplicationSideBar();
        applicationSideBar.configure();
        return applicationSideBar;
    }
}
