package chess.config;

import chess.controller.ApplicationWindowStateContext;
import chess.state.window.ClosedState;
import chess.state.window.MinimizedState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class StateFactory {

    private final ApplicationWindowStateContext context;

    public StateFactory(final ApplicationWindowStateContext context) {
        this.context = context;
    }

    // TODO - in the future, factory method probably makes more sense?
    // e.g. ApplicationWindowState createState(Class<T>...)

    @Bean
    public MinimizedState minimizedState() {
        return new MinimizedState(this.context);
    }

    public ClosedState closedState() {return new ClosedState(this.context); }

}
