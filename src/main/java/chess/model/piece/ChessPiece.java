package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.List;

public abstract class ChessPiece {

    protected final Color color;

    protected final ChessBoardModel board;

    protected ChessPiece(final Color color, final ChessBoardModel board) {
        this.color = color;
        this.board = board;
    }

    public abstract List<Move> getLegalMoves(final int sourceRow, final int sourceCol);

    public abstract String getIconResourcePath();
}
