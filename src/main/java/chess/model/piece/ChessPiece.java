package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;
import chess.view.util.ResourceConstants;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ChessPiece {

    public static final int NO_PIECE_CODE = 0;

    public static final int PAWN_CODE = 1;

    public static final int ROOK_CODE = 2;

    public static final int KNIGHT_CODE = 3;

    public static final int BISHOP_CODE = 4;

    public static final int QUEEN_CODE = 5;

    public static final int KING_CODE = 6;

    protected final PieceType pieceType;

    protected final Color color;

    protected final ChessBoardModel board;

    protected ChessPiece(final Color color, final ChessBoardModel board,
                         final PieceType pieceType) {
        this.color = color;
        this.board = board;
        this.pieceType = pieceType;
    }

    public abstract List<Move> getMoves(final int sourceRow, final int sourceCol);

    protected List<Move> filterOutOfBoundsMoves(final List<Move> moves) {
        return moves.stream().filter(move -> -1 < move.getDestRow() && move.getDestRow() < ChessBoardModel.BOARD_SIZE
                && -1 < move.getDestCol() && move.getDestCol() < ChessBoardModel.BOARD_SIZE).collect(Collectors.toList());

    }

    public enum PieceType {

        NONE(ChessPiece.NO_PIECE_CODE, Strings.EMPTY),

        PAWN(ChessPiece.PAWN_CODE, ResourceConstants.PAWN_ICON), ROOK(ChessPiece.ROOK_CODE, ResourceConstants.ROOK_ICON),

        KNIGHT(ChessPiece.KNIGHT_CODE, ResourceConstants.KNIGHT_ICON), BISHOP(ChessPiece.BISHOP_CODE, ResourceConstants.BISHOP_ICON),

        QUEEN(ChessPiece.QUEEN_CODE, ResourceConstants.QUEEN_ICON), KING(ChessPiece.KING_CODE, ResourceConstants.KING_ICON);

        private final int pieceCode;

        private final String resourcePath;

        PieceType(final int pieceCode, final String resourcePath) {
            this.pieceCode = pieceCode;
            this.resourcePath = resourcePath;
        }

        public String getResourcePath() {
            return this.resourcePath;
        }

        public int getPieceCode() {
            return this.pieceCode;
        }

        public static PieceType fromCode(final int code) {
            return Arrays.stream(PieceType.values()).filter(type -> Math.abs(code) == type.pieceCode)
                    .findFirst().orElseThrow(NullPointerException::new);
        }
    }


    protected void getLinearMovesUp(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow - 1;
        boolean checkNextMove = this.indexInBounds(nextRow);
        while (checkNextMove && this.indexInBounds(nextRow)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, sourceCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(nextRow, sourceCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(nextRow, sourceCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextRow--;
        }
    }

    protected void getLinearMovesDown(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow + 1;
        boolean checkNextMove = this.indexInBounds(nextRow);
        while (checkNextMove && this.indexInBounds(nextRow)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, sourceCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(nextRow, sourceCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(nextRow, sourceCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextRow++;
        }
    }

    protected void getLinearMovesLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextCol = sourceCol - 1;
        boolean checkNextMove = this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(sourceRow, nextCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(sourceRow, nextCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(sourceRow, nextCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextCol--;
        }
    }

    protected void getLinearMovesRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextCol = sourceCol + 1;
        boolean checkNextMove = this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(sourceRow, nextCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(sourceRow, nextCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(sourceRow, nextCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextCol++;
        }
    }

    protected void getLinearMovesUpRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow - 1;
        int nextCol = sourceCol + 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(nextRow, nextCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextRow--;
            nextCol++;
        }
    }

    protected void getLinearMovesDownRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow + 1;
        int nextCol = sourceCol + 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(nextRow, nextCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextRow++;
            nextCol++;
        }
    }

    protected void getLinearMovesUpLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow - 1;
        int nextCol = sourceCol - 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(nextRow, nextCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextRow--;
            nextCol--;
        }
    }

    protected void getLinearMovesDownLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow + 1;
        int nextCol = sourceCol - 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell ) {
                moves.add(new Move(nextRow, nextCol));
            } else if (this.color != pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            } else {
                checkNextMove = false;
            }
            nextRow++;
            nextCol--;
        }
    }

    protected boolean indexInBounds(final int nextRow) {
        return -1 < nextRow && nextRow < ChessBoardModel.BOARD_SIZE;
    }

}
