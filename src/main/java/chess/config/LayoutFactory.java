package chess.config;

import chess.view.core.ApplicationRootPane;
import chess.view.core.MainApplicationView;
import chess.view.menu.ApplicationTitleBar;
import org.springframework.stereotype.Service;

@Service
public class LayoutFactory {

    public ApplicationRootPane createApplicationRootPane() {
        return new ApplicationRootPane();
    }

    public ApplicationTitleBar createApplicationTitleBar() {
        return new ApplicationTitleBar();
    }

    public MainApplicationView createMainApplicationView() {
    return new MainApplicationView();
    }

}
