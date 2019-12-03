package chess.model;

import chess.config.ModelFactory;
import chess.model.piece.ChessPiece;
import javafx.util.Pair;

import java.util.List;

/**
 * A class for frequently used chess board-based methods.
 */
public class ChessModelUtils {

    private final ModelFactory modelFactory;

    public ChessModelUtils(final ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    /**
     * Creates a copy of the given board, applies a move, then returns the new board.
     *
     * @param rowToSet           - row to move the piece to.
     * @param colToSet           - column to move the piece to.
     * @param rowToClear         - row to make empty after the move.
     * @param colToClear         - column to make empty after the move.
     * @param chessBoardModel    - source board to use in copy.
     * @param selectedPieceColor - color of piece which will be moved.
     * @param pieceType          - type of piece that will be moved.
     * @return copy of the board with the given move applied.
     */
    public ChessBoardModel applyMoveToCopiedBoard(final int rowToSet, final int colToSet, final int rowToClear,
                                                  final int colToClear, final ChessBoardModel chessBoardModel,
                                                  final Color selectedPieceColor, final ChessPiece.PieceType pieceType) {
        final ChessBoardModel tempChessBoard = chessBoardModel.createCopy();
        tempChessBoard.setPieceForCell(rowToSet, colToSet, pieceType.getPieceCode(), selectedPieceColor);
        tempChessBoard.setPieceForCell(rowToClear, colToClear, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);

        // TODO - castling and enpassant checks.
        return tempChessBoard;
    }

    /**
     * Returns true iff the given color is in checkmate.
     *
     * @param board - chess board model.
     * @param color - the color to check.
     * @return - true iff the given color is in checkmate.
     */
    public boolean isColorInCheckMate(final ChessBoardModel board, final Color color) {
        return this.isColorInCheck(board, color) && !this.playerHasLegalMove(board, color);
    }

    /**
     * Returns true iff the given color is in stalemate.
     *
     * @param board - chess board model.
     * @param color - the color to check.
     * @return - true iff the given color is in stalemate.
     */
    public boolean isColorInStalemate(final ChessBoardModel board, final Color color) {
        return !this.isColorInCheck(board, color) && !this.playerHasLegalMove(board, color);
    }

    /**
     * Returns true iff the given color is in check.
     *
     * @param board - chess board model.
     * @param color - the color to check.
     * @return - true iff the given color is in check.
     */
    public boolean isColorInCheck(final ChessBoardModel board, final Color color) {
        final Pair<Integer, Integer> kingPosition = this.getKingPosition(board, color);
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (pieceColorForCell == Color.getOpposingColor(color)) {
                    if (this.isPieceThreateningCell(board, kingPosition.getKey(),
                            kingPosition.getValue(), row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true iff the given color could move a piece to the given cell on their next turn.
     *
     * @param board     - chess board object.
     * @param color     - color to check.
     * @param targetRow - row to target.
     * @param targetCol - column to target.
     * @return - true iff the given color could move a piece to the given cell on their next turn.
     */
    public boolean canColorThreatenCell(final ChessBoardModel board, final Color color, final int targetRow,
                                        final int targetCol) {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (pieceColorForCell == color) {
                    if (this.isPieceThreateningCell(board, targetRow,
                            targetCol, row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true iff the given move is legal.
     *
     * @param boardBeforeMove - board before the move.
     * @param boardAfterMove  - board after the move.
     * @param color           - color making the move.
     * @param move            - move to check.
     * @return - true iff the given move is legal.
     */
    public boolean isMoveLegal(final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove,
                               final Color color, final Move move) {
        if (!this.isColorInCheck(boardAfterMove, color)) {
            if (Move.MoveType.CASTLE_LEFT == move.getMoveType()) {
                return this.isLeftCastleLegal(boardBeforeMove, boardAfterMove, color, move);
            } else if (Move.MoveType.CASTLE_RIGHT == move.getMoveType()) {
                return this.isRightCastleLegal(boardBeforeMove, boardAfterMove, color, move);
            }
            // TODO - enpassant
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the position on the board of the king of the given color.
     *
     * @param board - chess board object.
     * @param color - color to find.
     * @return - the position on the board of the king of the given color.
     */
    public Pair<Integer, Integer> getKingPosition(final ChessBoardModel board, final Color color) {
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final int pieceForCell = board.getPieceForCell(row, col);
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (Math.abs(pieceForCell) == ChessPiece.PieceType.KING.getPieceCode()
                        && pieceColorForCell == color) {
                    kingRow = row;
                    kingCol = col;
                }
            }
        }
        return new Pair<>(kingRow, kingCol);
    }

    /**
     * Returns true iff the piece at the given cell is threatening the target cell.
     *
     * @param board     - chess board object.
     * @param targetRow - row to check if piece can threaten.
     * @param targetCol - column to check if piece can threaten.
     * @param row       - row of piece.
     * @param col       - column of piece.
     * @return true iff the piece at the given cell is threatening the target cell.
     */
    public boolean isPieceThreateningCell(final ChessBoardModel board, final int targetRow,
                                          final int targetCol, final int row, final int col) {
        final int pieceForCell = board.getPieceForCell(row, col);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(pieceForCell);
        final Color pieceColorForCell = board.getPieceColorForCell(row, col);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                pieceColorForCell);
        final List<Move> moves = chessPiece.getMoves(row, col, false);
        for (final Move move : moves) {
            if (move.getDestRow() == targetRow && move.getDestCol() == targetCol) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff the given color has at least 1 move.
     *
     * @param board - chess board object.
     * @param color - color to check.
     * @return true iff the given color has at least 1 move.
     */
    public boolean playerHasLegalMove(final ChessBoardModel board, final Color color) {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (pieceColorForCell == color) {
                    if (this.canPieceMove(board, color, row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true iff the piece at the given position has any legal moves (check and stalemate considered).
     *
     * @param board - chess board object.
     * @param color - color of piece to check.
     * @param row   - row to check.
     * @param col   - column to check.
     * @return - true iff the piece at the given position has any legal moves.
     */
    public boolean canPieceMove(final ChessBoardModel board, final Color color,
                                final int row, final int col) {
        final int pieceForCell = board.getPieceForCell(row, col);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(pieceForCell);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                color);
        final List<Move> moves = chessPiece.getMoves(row, col, true);
        for (final Move move : moves) {
            final ChessBoardModel boardWithNextMove = this.applyMoveToCopiedBoard(move.getDestRow(),
                    move.getDestCol(), row, col, board, color, pieceType);
            if (!this.isColorInCheck(boardWithNextMove, color)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightCastleLegal(final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove, final Color color, final Move move) {
        // TODO add king and rook haven't moved checks.
        return !this.canColorThreatenCell(boardAfterMove, Color.getOpposingColor(color), move.getDestRow(),
                move.getDestCol() - 1) && !this.isColorInCheck(boardBeforeMove, color);
    }

    private boolean isLeftCastleLegal(final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove, final Color color, final Move move) {
        // TODO add king and rook haven't moved checks.
        return !this.canColorThreatenCell(boardAfterMove, Color.getOpposingColor(color), move.getDestRow(),
                move.getDestCol() + 1) && !this.isColorInCheck(boardBeforeMove, color);
    }

}
