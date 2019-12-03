package chess.model;

public class MoveWithSource extends Move {

    private final int srcRow;

    private final int srcCol;

    public MoveWithSource(final int destRow, final int destCol, final int srcRow, final int srcCol) {
        this(destRow, destCol, srcRow, srcCol, MoveType.NORMAL);
    }

    public MoveWithSource(final int destRow, final int destCol, final int srcRow, final int srcCol, final MoveType moveType) {
        super(destRow, destCol, moveType);
        this.srcRow = srcRow;
        this.srcCol = srcCol;
    }

    public int getSrcRow() {
        return this.srcRow;
    }

    public int getSrcCol() {
        return this.srcCol;
    }

    @Override
    public String toString() {
        return "MoveWithSource: srcRow=" + this.srcRow + ", srcCol=" + this.srcCol
                + ", destRow=" + this.getDestRow() + ", destCol=" + this.getDestCol()
                + ", type=" + this.getMoveType();
    }
}
