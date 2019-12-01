package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {


    public Queen(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.PAWN);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol) {
        final List<Move> moves = new ArrayList<>();
        this.getLinearMovesUp(sourceRow, sourceCol, moves);
        this.getLinearMovesDown(sourceRow, sourceCol, moves);
        this.getLinearMovesLeft(sourceRow, sourceCol, moves);
        this.getLinearMovesRight(sourceRow, sourceCol, moves);
        this.getLinearMovesUpRight(sourceRow, sourceCol, moves);
        this.getLinearMovesDownRight(sourceRow, sourceCol, moves);
        this.getLinearMovesUpLeft(sourceRow, sourceCol, moves);
        this.getLinearMovesDownLeft(sourceRow, sourceCol, moves);
        return moves;
    }

}
