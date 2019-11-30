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
     * Returns the color of piece in this cell.
     *
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
