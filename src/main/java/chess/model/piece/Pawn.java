package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    private static final int PAWN_START_ROW_BOTTOM = 6;

    private static final int PAWN_START_ROW_TOP = 1;

    public Pawn(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.PAWN);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol) {
        final List<Move> moves = new ArrayList<>();
        // TODO - this could be merged into one method; only changes are the sign of the offsets
        //  to source cells , and the start row.
        if (this.color == Color.WHITE && this.board.isPlayerOneWhite() ||
                this.color == Color.BLACK && !this.board.isPlayerOneWhite()) {
            this.getMovesAsPlayerOne(sourceRow, sourceCol, moves);
        } else {
            this.getMovesAsPlayerTwo(sourceRow, sourceCol, moves);
        }

        return this.filterOutOfBoundsMoves(moves);
    }

    /**
     * Gets the moves as the player on the bottom half of the screen
     *
     * @param sourceRow - current row of the piece
     * @param sourceCol - current column of the piece
     * @param moves     - current list of found moves.
     */
    private void getMovesAsPlayerOne(final int sourceRow, final int sourceCol, final List<Move> moves) {
        // First move.
        if (sourceRow == PAWN_START_ROW_BOTTOM && this.board.isCellEmpty(sourceRow - 2, sourceCol)) {
            moves.add(new Move(sourceRow - 2, sourceCol));
        }
        // Basic move.
        if (this.board.isCellEmpty(sourceRow - 1, sourceCol)) {
            moves.add(new Move(sourceRow - 1, sourceCol));
        }
        // Capture moves.
        final Color leftCaptureCellColor = this.board.getPieceColorForCell(sourceRow - 1,
                sourceCol - 1);
        if (Color.areOpposingColors(this.color, leftCaptureCellColor)) {
            moves.add(new Move(sourceRow - 1, sourceCol - 1));
        }
        final Color rightCaptureCellColor = this.board.getPieceColorForCell(sourceRow - 1,
                sourceCol + 1);
        if (Color.areOpposingColors(this.color, rightCaptureCellColor)) {
            moves.add(new Move(sourceRow - 1, sourceCol + 1));
        }
        // TODO - enpassant
    }

    /**
     * Gets the moves as the player on the top half of the screen
     *
     * @param sourceRow - current row of the piece
     * @param sourceCol - current column of the piece
     * @param moves     - current list of found moves.
     */
    private void getMovesAsPlayerTwo(final int sourceRow, final int sourceCol, final List<Move> moves) {
        // First move.
        if (sourceRow == PAWN_START_ROW_TOP && this.board.isCellEmpty(sourceRow + 2, sourceCol)) {
            moves.add(new Move((sourceRow + 2), sourceCol));
        }
        // Basic move.
        if (this.board.isCellEmpty(sourceRow + 1, sourceCol)) {
            moves.add(new Move((sourceRow + 1), sourceCol));
        }
        // Capture moves.
        final Color leftCaptureCellColor = this.board.getPieceColorForCell(sourceRow + 1,
                sourceCol - 1);
        if (Color.areOpposingColors(this.color, leftCaptureCellColor)) {
            moves.add(new Move(sourceRow + 1, sourceCol - 1));
        }
        final Color rightCaptureCellColor = this.board.getPieceColorForCell(sourceRow + 1,
                sourceCol + 1);
        if (Color.areOpposingColors(this.color, rightCaptureCellColor)) {
            moves.add(new Move(sourceRow + 1, sourceCol + 1));
        }
        // TODO - enpassant
    }

}
