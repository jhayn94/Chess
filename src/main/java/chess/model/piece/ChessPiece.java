package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;
import chess.view.util.ResourceConstants;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a chess piece on a board.
 */
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

    /**
     * Returns a list of moves the piece can make. The returned list considers board boundaries and
     * placements of other pieces, but does not review if the player is in check.
     *
     * @param sourceRow            - start row of piece.
     * @param sourceCol            - start column of piece.
     * @param filterFriendlyPieces - if true, moves returned will not include cells covered by pieces of the
     *                             same color.
     * @return - a list of moves the piece can make.
     */
    public abstract List<Move> getMoves(final int sourceRow, final int sourceCol, boolean filterFriendlyPieces);

    /**
     * Filters out moves which exceed the bounds of the chess board.
     * @param moves - unfiltered list of moves.
     * @return - a filtered list of moves.
     */
    protected List<Move> filterOutOfBoundsMoves(final List<Move> moves) {
        return moves.stream().filter(move -> -1 < move.getDestRow() && move.getDestRow() < ChessBoardModel.BOARD_SIZE
                && -1 < move.getDestCol() && move.getDestCol() < ChessBoardModel.BOARD_SIZE).collect(Collectors.toList());
    }

    /**
     * Filters out moves which are to cells with friendly pieces.
     * @param moves - unfiltered list of moves.
     * @return - a filtered list of moves.
     */
    protected List<Move> filterFriendlyPieces(final List<Move> moves) {
        return moves.stream().filter(move -> this.color != this.board.getPieceColorForCell(move.getDestRow(),
                move.getDestCol())).collect(Collectors.toList());
    }

    /**
     * Indicates the type of piece that this is.
     */
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

    /**
     * Gets all moves the piece can make in a straight line up until it hits another piece. Note that this could
     * be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesUp(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow - 1;
        boolean checkNextMove = this.indexInBounds(nextRow);
        while (checkNextMove && this.indexInBounds(nextRow)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, sourceCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(nextRow, sourceCol));
            } else {
                moves.add(new Move(nextRow, sourceCol));
                checkNextMove = false;
            }
            nextRow--;
        }
    }


    /**
     * Gets all moves the piece can make in a straight line down until it hits another piece. Note that this could
     * be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesDown(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow + 1;
        boolean checkNextMove = this.indexInBounds(nextRow);
        while (checkNextMove && this.indexInBounds(nextRow)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, sourceCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(nextRow, sourceCol));
            } else {
                moves.add(new Move(nextRow, sourceCol));
                checkNextMove = false;
            }
            nextRow++;
        }
    }

    /**
     * Gets all moves the piece can make in a straight line left until it hits another piece. Note that this could
     * be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextCol = sourceCol - 1;
        boolean checkNextMove = this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(sourceRow, nextCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(sourceRow, nextCol));
            } else {
                moves.add(new Move(sourceRow, nextCol));
                checkNextMove = false;
            }
            nextCol--;
        }
    }

    /**
     * Gets all moves the piece can make in a straight line right until it hits another piece. Note that this could
     * be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextCol = sourceCol + 1;
        boolean checkNextMove = this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(sourceRow, nextCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(sourceRow, nextCol));
            } else {
                moves.add(new Move(sourceRow, nextCol));
                checkNextMove = false;
            }
            nextCol++;
        }
    }

    /**
     * Gets all moves the piece can make in a diagonal line up and to the right until it hits another piece. Note
     * that this could be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesUpRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow - 1;
        int nextCol = sourceCol + 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
            } else {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            }
            nextRow--;
            nextCol++;
        }
    }

    /**
     * Gets all moves the piece can make in a diagonal line down and to the right until it hits another piece. Note
     * that this could be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesDownRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow + 1;
        int nextCol = sourceCol + 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
            } else {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            }
            nextRow++;
            nextCol++;
        }
    }

    /**
     * Gets all moves the piece can make in a diagonal line up and to the left until it hits another piece. Note
     * that this could be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesUpLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow - 1;
        int nextCol = sourceCol - 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
            } else {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            }
            nextRow--;
            nextCol--;
        }
    }

    /**
     * Gets all moves the piece can make in a diagonal line down to the left until it hits another piece. Note
     * that this could be a friendly piece. See this::filterFriendlyPieces for filtering this out.
     * @param sourceRow - source row of the move.
     * @param sourceCol - source column of the move.
     * @param moves - current list of moves to add to.
     */
    protected void getLinearMovesDownLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        int nextRow = sourceRow + 1;
        int nextCol = sourceCol - 1;
        boolean checkNextMove = this.indexInBounds(nextRow) && this.indexInBounds(nextCol);
        while (checkNextMove && this.indexInBounds(nextRow) && this.indexInBounds(nextCol)) {
            final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
            if (Color.NONE == pieceColorForCell) {
                moves.add(new Move(nextRow, nextCol));
            } else {
                moves.add(new Move(nextRow, nextCol));
                checkNextMove = false;
            }
            nextRow++;
            nextCol--;
        }
    }

    /**
     * Returns true iff the given index is in the bounds of the board.
     * @param index - position on board to check.
     * @return - true iff the given index is in the bounds of the board.
     */
    protected boolean indexInBounds(final int index) {
        return -1 < index && index < ChessBoardModel.BOARD_SIZE;
    }

}
