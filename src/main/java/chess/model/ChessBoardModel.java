package chess.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChessBoardModel {

    public static final int BOARD_SIZE = 8;

    private final boolean isPlayerOneWhite;

    int[][] board;

    public ChessBoardModel(final boolean isPlayerOneWhite) {
        this.isPlayerOneWhite = isPlayerOneWhite;
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * Filters a list of given moves to ignore moves where the source and destination cells have
     * a piece of the same color.
     *
     * @param moves               - initial list of moves
     * @param filterByBlockedPath - if true, also filters out moves where the path between source
     *                            and destination is blocked.
     */
    public List<Move> filterCellsOccupiedBySameColor(final int sourceRow,
                                                     final int sourceCol, final List<Move> moves,
                                                     final boolean filterByBlockedPath) {
        final int sourcePiece = this.board[sourceCol][sourceRow];
        final boolean isSourcePieceWhite = sourcePiece < 0;

        return moves.stream().filter(move -> {
            final int destPiece = this.board[move.getDestCol()][move.getDestRow()];
            final boolean isDestPieceWhite = destPiece < 0;
            return isSourcePieceWhite == isDestPieceWhite;
        }).collect(Collectors.toList());
    }

    /**
     * Returns the color of piece in this cell.
     * @param row - row to check.
     * @param col - column to check.
     * @return - color of piece in this cell, Color.NONE if nothing is there.
     */
    public Color getPieceColorForCell(final int row, final int col) {
        final int piece = this.board[col][row];
        if (piece == 0) {
            return Color.NONE;
        }
        return piece < 0 ? Color.WHITE : Color.BLACK;
    }

    public boolean isCellEmpty(final int row, final int col) {
        return this.board[col][row] == 0;
    }

    public boolean isPlayerOneWhite() {
        return this.isPlayerOneWhite;
    }
}
