package chess.state.model;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.BoardHistory;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.model.Color;
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
            this.clearHighlightedCells(true);
            final ChessBoardModel board = history.getBoardForUndo();
            history.addToRedoStack(this.context.getBoard());
            this.context.setBoard(board);
            this.resetAppToMatchBoard();
            this.updateUndoRedoButtons();
            this.doAfterMoveChecks(board, Color.BLACK, board);
            this.doAfterMoveChecks(board, Color.WHITE, board);
        }

    }

}
