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
    public List<Move> getMoves(final int sourceRow, final int sourceCol, final boolean filterFriendlyPieces) {
        List<Move> moves = new ArrayList<>();
        // TODO - this could be merged into one method; only changes are the sign of the offsets
        //  to source cells , and the start row.
        if (this.color == Color.WHITE && this.board.isPlayerOneWhite() ||
                this.color == Color.BLACK && !this.board.isPlayerOneWhite()) {
            this.getMovesAsPlayerOne(sourceRow, sourceCol, moves);
        } else {
            this.getMovesAsPlayerTwo(sourceRow, sourceCol, moves);
        }

        moves = this.filterOutOfBoundsMoves(moves);
        if (filterFriendlyPieces) {
            moves = this.filterFriendlyPieces(moves);
        }
        return moves;
    }

    /**
     * Gets the moves as the player on the bottom half of the screen.
     *
     * @param sourceRow - current row of the piece
     * @param sourceCol - current column of the piece
     * @param moves     - current list of found moves.
     */
    private void getMovesAsPlayerOne(final int sourceRow, final int sourceCol, final List<Move> moves) {
        // First move.
        if (sourceRow == PAWN_START_ROW_BOTTOM && this.board.isCellEmpty(sourceRow - 2, sourceCol)
                && this.board.isCellEmpty(sourceRow - 1, sourceCol)) {
            moves.add(new Move(sourceRow - 2, sourceCol));
        }
        // Basic move.
        if (this.board.isCellEmpty(sourceRow - 1, sourceCol)) {
            moves.add(new Move(sourceRow - 1, sourceCol));
        }
        // Capture moves. Remember that moves to cells with friendly pieces are optionally filtered out later.
        final Color leftCapturePieceColor = this.board.getPieceColorForCell(sourceRow - 1,
                sourceCol - 1);
        if (Color.NONE != leftCapturePieceColor) {
            moves.add(new Move(sourceRow - 1, sourceCol - 1));
        }
        final Color rightCapturePieceColor = this.board.getPieceColorForCell(sourceRow - 1,
                sourceCol + 1);
        if (Color.NONE != rightCapturePieceColor) {
            moves.add(new Move(sourceRow - 1, sourceCol + 1));
        }
        // En passant moves. Note that friendly pieces are filtered out here as there is no concept of
        // "protecting" your other pieces with an en passant move.
        final Color leftEnPassantCapturePieceColor = this.board.getPieceColorForCell(sourceRow,
                sourceCol - 1);
        final int pieceForLeftEnPassantCaptureCell =
                Math.abs(this.board.getPieceForCell(sourceRow, sourceCol - 1));
        if (pieceForLeftEnPassantCaptureCell == 1 && Color.areOpposingColors(this.color,
                leftEnPassantCapturePieceColor)) {
            moves.add(new Move(sourceRow - 1, sourceCol - 1, Move.MoveType.EN_PASSANT));
        }
        final Color rightEnPassantCapturePieceColor = this.board.getPieceColorForCell(sourceRow,
                sourceCol + 1);
        final int pieceForRightEnPassantCaptureCell =
                Math.abs(this.board.getPieceForCell(sourceRow, sourceCol + 1));
        if (pieceForRightEnPassantCaptureCell == 1 && Color.areOpposingColors(this.color,
                rightEnPassantCapturePieceColor)) {
            moves.add(new Move(sourceRow - 1, sourceCol + 1, Move.MoveType.EN_PASSANT));
        }
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
        if (sourceRow == PAWN_START_ROW_TOP && this.board.isCellEmpty(sourceRow + 2, sourceCol)
                && this.board.isCellEmpty(sourceRow + 1, sourceCol)) {
            moves.add(new Move((sourceRow + 2), sourceCol));
        }
        // Basic move.
        if (this.board.isCellEmpty(sourceRow + 1, sourceCol)) {
            moves.add(new Move((sourceRow + 1), sourceCol));
        }
        // Capture moves. Remember that moves to cells with friendly pieces are optionally filtered out later.
        final Color leftCapturePieceColor = this.board.getPieceColorForCell(sourceRow + 1,
                sourceCol - 1);
        if (Color.NONE != leftCapturePieceColor) {
            moves.add(new Move(sourceRow + 1, sourceCol - 1));
        }
        final Color rightCapturePieceColor = this.board.getPieceColorForCell(sourceRow + 1,
                sourceCol + 1);
        if (Color.NONE != rightCapturePieceColor) {
            moves.add(new Move(sourceRow + 1, sourceCol + 1));
        }
        // En passant moves. Note that friendly pieces are filtered out here as there is no concept of
        // "protecting" your other pieces with an en passant move.
        final Color leftEnPassantCapturePieceColor = this.board.getPieceColorForCell(sourceRow,
                sourceCol - 1);
        final int pieceForLeftEnPassantCaptureCell =
                Math.abs(this.board.getPieceForCell(sourceRow, sourceCol - 1));
        if (pieceForLeftEnPassantCaptureCell == 1 && Color.areOpposingColors(this.color,
                leftEnPassantCapturePieceColor)) {
            moves.add(new Move(sourceRow + 1, sourceCol - 1, Move.MoveType.EN_PASSANT));
        }
        final Color rightEnPassantCapturePieceColor = this.board.getPieceColorForCell(sourceRow,
                sourceCol + 1);
        final int pieceForRightEnPassantCaptureCell = Math.abs(this.board.getPieceForCell(sourceRow,
                sourceCol + 1));
        if (pieceForRightEnPassantCaptureCell == 1 && Color.areOpposingColors(this.color,
                rightEnPassantCapturePieceColor)) {
            moves.add(new Move(sourceRow + 1, sourceCol + 1, Move.MoveType.EN_PASSANT));
        }
    }

}
