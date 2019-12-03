package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public static final int KING_START_ROW_BOTTOM = 7;

    public static final int KING_START_ROW_TOP = 0;

    public static final int KING_START_COL = 4;

    public King(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.PAWN);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol, final boolean filterFriendlyPieces) {
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(sourceRow + 1, sourceCol));
        moves.add(new Move(sourceRow - 1, sourceCol));
        moves.add(new Move(sourceRow, sourceCol + 1));
        moves.add(new Move(sourceRow, sourceCol - 1));
        moves.add(new Move(sourceRow + 1, sourceCol + 1));
        moves.add(new Move(sourceRow + 1, sourceCol - 1));
        moves.add(new Move(sourceRow - 1, sourceCol + 1));
        moves.add(new Move(sourceRow - 1, sourceCol - 1));
        moves = this.filterOutOfBoundsMoves(moves);

        this.addCastlingMoves(sourceRow, sourceCol, moves);
        if (filterFriendlyPieces) {
            moves = this.filterFriendlyPieces(moves);
        }
        return moves;
    }

    private void addCastlingMoves(final int sourceRow, final int sourceCol, final List<Move> moves) {
        if (this.board.isCellEmpty(sourceRow, sourceCol + 1) && this.board.isCellEmpty(sourceRow,
                sourceCol + 2)) {
            this.addCastleRight(sourceRow, sourceCol, moves);
        }
        if (this.board.isCellEmpty(sourceRow, sourceCol - 1) && this.board.isCellEmpty(sourceRow,
                sourceCol - 2) && this.board.isCellEmpty(sourceRow, sourceCol - 3)) {
            this.addCastleLeft(sourceRow, sourceCol, moves);
        }

    }

    private void addCastleRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        if (this.playerOneKingCanCastle(sourceRow, sourceCol) || this.playerTwoKingCanCastle(sourceRow, sourceCol)) {
            moves.add(new Move(sourceRow, sourceCol + 2, Move.MoveType.CASTLE_RIGHT));
        }
    }

    private void addCastleLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        if (this.playerOneKingCanCastle(sourceRow, sourceCol) || this.playerTwoKingCanCastle(sourceRow, sourceCol)) {
            moves.add(new Move(sourceRow, sourceCol - 2, Move.MoveType.CASTLE_LEFT));
        }
    }

    private boolean playerOneKingCanCastle(final int sourceRow, final int sourceCol) {
        return sourceCol == KING_START_COL && sourceRow == KING_START_ROW_BOTTOM && (this.color == Color.WHITE && this.board.isPlayerOneWhite())
                || (this.color == Color.BLACK && !this.board.isPlayerOneWhite());
    }

    private boolean playerTwoKingCanCastle(final int sourceRow, final int sourceCol) {
        return sourceCol == KING_START_COL && sourceRow == KING_START_ROW_TOP && (this.color == Color.BLACK && this.board.isPlayerOneWhite())
                || (this.color == Color.WHITE && !this.board.isPlayerOneWhite());
    }

}
