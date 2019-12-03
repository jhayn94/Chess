package chess.state.model;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.BoardHistory;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.state.GameState;

/**
 * This class updates the state of the application when the user invokes an
 * "undo", either through the keyboard or a button press in the UI.
 */
public class UndoActionState extends GameState {

    public UndoActionState(final ApplicationStateContext context,
                           final ModelFactory modelFactory, final ChessModelUtils utils) {
        super(context, utils, modelFactory);
    }

    @Override
    public void onEnter() {
        final BoardHistory history = this.context.getHistory();
        if (!history.isUndoStackEmpty()) {
            final ChessBoardModel board = history.getBoardForUndo();
            history.addToRedoStack(board);
            this.context.setBoard(board);
            this.resetAppToMatchBoard();
            this.updateUndoRedoButtons();
        }

    }

}
