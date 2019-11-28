package chess.config;

import chess.controller.ApplicationStateContext;
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

    private final ApplicationStateContext stateContext;

    public StateFactory(final ApplicationStateContext stateContext) {
        this.stateContext = stateContext;
    }

    // TODO - in the future, does a factory method make more sense?
    // e.g. ApplicationWindowState createState(Class<T>...)

    @Bean
    public MinimizedState minimizedState() {
        return new MinimizedState(this.stateContext);
    }

    @Bean
    public ClosedState closedState() {
        return new ClosedState(this.stateContext);
    }

    @Bean
    public NewGameState newGameState() {
        return new NewGameState(this.stateContext);
    }

    @Bean
    public UndoActionState undoState() {
        return new UndoActionState(this.stateContext);
    }

    @Bean
    public RedoActionState redoState() {
        return new RedoActionState(this.stateContext);
    }

}
