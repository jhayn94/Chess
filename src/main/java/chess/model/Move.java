package chess.model;

public class Move {

    public enum MoveType {
        NORMAL, EN_PASSANT, CASTLE_LEFT, CASTLE_RIGHT
    }

    private final int destRow;

    private final int destCol;

    private final MoveType moveType;

    public Move(final int destRow, final int destCol) {
        this.destRow = destRow;
        this.destCol = destCol;
        this.moveType = MoveType.NORMAL;
    }

    public Move(final int destRow, final int destCol, final MoveType moveType) {
        this.destRow = destRow;
        this.destCol = destCol;
        this.moveType = moveType;
    }

    public int getDestRow() {
        return this.destRow;
    }

    public int getDestCol() {
        return this.destCol;
    }

    public MoveType getMoveType() {
        return this.moveType;
    }
}
