package chess.model;

public class Move {


    private final int destRow;

    private final int destCol;

    public Move(final int destRow, final int destCol) {
        this.destRow = destRow;
        this.destCol = destCol;
    }

    public int getDestRow() {
        return this.destRow;
    }

    public int getDestCol() {
        return this.destCol;
    }
}
