package chess.model;

import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ChessBoardModel {

    public static final int BOARD_SIZE = 8;

    public static final int RIGHT_ROOK_CASTLE_DEST_COL = 5;

    public static final int LEFT_ROOK_CASTLE_DEST_COL = 3;

    private boolean isPlayer1sTurn;

    // Tracks which pieces have moved that affect eligibility for castling.
    private Set<MovedPieces> movedPieces;

    // Tracks if a pawn last moved two cells, making it able to be captured via en passant.
    private Pair<Color, Integer> enpassant;

    private final boolean isPlayerOneWhite;

    private int[][] board;

    public ChessBoardModel(final boolean isPlayerOneWhite) {
        this.isPlayerOneWhite = isPlayerOneWhite;
        this.enpassant = new Pair<>(Color.NONE, -1);
        this.movedPieces = new HashSet<>();
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

    /**
     * Returns an aliasing proof copy of this.
     *
     * @return - an aliasing proof copy of this
     */
    public ChessBoardModel createCopy() {
        final ChessBoardModel other = new ChessBoardModel(this.isPlayerOneWhite);
        other.board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                other.board[col][row] = this.board[col][row];
            }
        }
        other.enpassant = new Pair<>(this.enpassant.getKey(), this.enpassant.getValue());
        other.movedPieces = new HashSet<>(this.movedPieces);
        other.isPlayer1sTurn = this.isPlayer1sTurn;
        return other;
    }

    public boolean isPlayerOneWhite() {
        return this.isPlayerOneWhite;
    }

    public Set<MovedPieces> getMovedPieces() {
        return this.movedPieces;
    }

    public Pair<Color, Integer> getEnpassant() {
        return this.enpassant;
    }

    public void setEnpassant(final Pair<Color, Integer> enpassant) {
        this.enpassant = enpassant;
    }

    public boolean isPlayer1sTurn() {
        return this.isPlayer1sTurn;
    }

    public void setIsPlayer1sTurn(final boolean isPlayer1sTurn) {
        this.isPlayer1sTurn = isPlayer1sTurn;
    }
}
