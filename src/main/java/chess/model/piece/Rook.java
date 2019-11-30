package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {


    public Rook(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.ROOK);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol) {
        final List<Move> moves = new ArrayList<>();

        return moves;
    }

}
