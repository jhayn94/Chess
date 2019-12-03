package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public static final int PLAYER_ONE_ROOK_START_ROW = 7;

    public static final int PLAYER_TWO_ROOK_START_ROW = 0;

    public static final int RIGHT_ROOK_START_COL = 7;

    public static final int LEFT_ROOK_START_COL = 0;


    public Rook(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.ROOK);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol, final boolean filterFriendlyPieces) {
        List<Move> moves = new ArrayList<>();
        this.getLinearMovesUp(sourceRow, sourceCol, moves);
        this.getLinearMovesDown(sourceRow, sourceCol, moves);
        this.getLinearMovesLeft(sourceRow, sourceCol, moves);
        this.getLinearMovesRight(sourceRow, sourceCol, moves);
        if (filterFriendlyPieces) {
            moves = this.filterFriendlyPieces(moves);
        }
        return moves;
    }

}
