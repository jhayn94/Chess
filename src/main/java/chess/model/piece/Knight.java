package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    public Knight(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.PAWN);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol, final boolean filterFriendlyPieces) {
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(sourceRow + 2, sourceCol - 1));
        moves.add(new Move(sourceRow + 2, sourceCol + 1));
        moves.add(new Move(sourceRow - 2, sourceCol - 1));
        moves.add(new Move(sourceRow - 2, sourceCol + 1));
        moves.add(new Move(sourceRow + 1, sourceCol - 2));
        moves.add(new Move(sourceRow + 1, sourceCol + 2));
        moves.add(new Move(sourceRow - 1, sourceCol - 2));
        moves.add(new Move(sourceRow - 1, sourceCol + 2));
        moves = this.filterOutOfBoundsMoves(moves);
        if (filterFriendlyPieces) {
            moves = this.filterFriendlyPieces(moves);
        }
        return moves;
    }

}
