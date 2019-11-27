package chess.config;

import chess.controller.ApplicationModelStateContext;
import chess.controller.ApplicationViewStateContext;
import chess.state.model.NewGameState;
import chess.state.model.RedoActionState;
import chess.state.model.UndoActionState;
import chess.state.window.ClosedState;
import chess.state.window.MinimizedState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class StateFactory {

    private final ApplicationViewStateContext viewStateContext;

    private final ApplicationModelStateContext modelStateContext;

    public StateFactory(final ApplicationModelStateContext modelStateContext,
                        final ApplicationViewStateContext viewStateContext) {
        this.modelStateContext = modelStateContext;
        this.viewStateContext = viewStateContext;
    }

    // TODO - in the future, does a factory method make more sense?
    // e.g. ApplicationWindowState createState(Class<T>...)

    @Bean
    public MinimizedState minimizedState() {
        return new MinimizedState(this.viewStateContext);
    }

    @Bean
    public ClosedState closedState() {
        return new ClosedState(this.viewStateContext);
    }

    @Bean
    public NewGameState newGameState() {
        return new NewGameState(this.modelStateContext);
    }

    @Bean
    public UndoActionState undoState() {
        return new UndoActionState(this.modelStateContext);
    }

    @Bean
    public RedoActionState redoState() {
        return new RedoActionState(this.modelStateContext);
    }

}
