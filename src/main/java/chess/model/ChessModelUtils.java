package chess.model;

import chess.config.ModelFactory;
import chess.model.piece.ChessPiece;

import java.util.List;

public class ChessModelUtils {

    private final ModelFactory modelFactory;

    public ChessModelUtils(final ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    /**
     * Returns true iff the given color is in check.
     *
     * @param board - chess board model.
     * @param color - the color to check.
     * @return - true iff the given color is in check.
     */
    public boolean isColorInCheck(final ChessBoardModel board, final Color color) {
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

        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (pieceColorForCell != Color.NONE && pieceColorForCell != color) {
                    if (this.isPieceThreateningOpposingKing(board, kingRow, kingCol, row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isPieceThreateningOpposingKing(final ChessBoardModel board, final int kingRow,
                                                   final int kingCol, final int row, final int col) {
        final int pieceForCell = board.getPieceForCell(row, col);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(pieceForCell);
        final Color pieceColorForCell = board.getPieceColorForCell(row, col);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                pieceColorForCell);
        final List<Move> moves = chessPiece.getMoves(row, col);
        for (final Move move : moves) {
            if (move.getDestRow() == kingRow && move.getDestCol() == kingCol) {
                return true;
            }
        }
        return false;
    }
}
