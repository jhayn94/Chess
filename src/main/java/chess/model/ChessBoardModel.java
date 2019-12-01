package chess.model;

import org.springframework.stereotype.Component;

@Component
public class ChessBoardModel {

    public static final int BOARD_SIZE = 8;

    private final boolean isPlayerOneWhite;

    int[][] board;

    public ChessBoardModel(final boolean isPlayerOneWhite) {
        this.isPlayerOneWhite = isPlayerOneWhite;
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                this.board[col][row] = 0;
            }
        }
    }

    public int getPieceForCell(final int row, final int col) {
        return this.board[col][row];
    }

    public void setPieceForCell(final int row, final int col, final int piece, final Color color) {
        this.board[col][row] = piece;
        if (color == Color.BLACK) {
            this.board[col][row] *= -1;
        }
    }

    /**
     * Returns the color of piece in this cell. If the given row or column are out of the bounds
     * of the board, returns Color.NONE.
     *
     * @param row - row to check.
     * @param col - column to check.
     * @return - color of piece in this cell, Color.NONE if nothing is there.
     */
    public Color getPieceColorForCell(final int row, final int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return Color.NONE;
        }
        final int piece = this.board[col][row];
        if (piece == 0) {
            return Color.NONE;
        }
        return piece < 0 ? Color.BLACK : Color.WHITE;
    }

    /**
     * Returns true iff the given cell is empty. If the given row or column are out of the bounds
     * of the board, returns Color.NONE.
     *
     * @param row - row to check.
     * @param col - column to check.
     * @return - true iff the given cell is empty.
     */
    public boolean isCellEmpty(final int row, final int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false;
        }
        return this.board[col][row] == 0;
    }

    public boolean isPlayerOneWhite() {
        return this.isPlayerOneWhite;
    }
}
