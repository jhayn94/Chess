package chess.config;

import chess.controller.ApplicationStateContext;
import chess.state.action.ClickedCellState;
import chess.state.action.ToggleContextMenuState;
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

    private final ModelFactory modelFactory;

    public StateFactory(final ApplicationStateContext stateContext, final ModelFactory modelFactory) {
        this.stateContext = stateContext;
        this.modelFactory = modelFactory;
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

    @Bean
    public ToggleContextMenuState toggleContextMenuState() {
        return new ToggleContextMenuState(this.stateContext);
    }

    @Bean
    public ClickedCellState clickedCellState() {
        return new ClickedCellState(this.stateContext, this.modelFactory);
    }
}
