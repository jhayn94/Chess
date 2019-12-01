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
    public ChessBoardModel applyMoveToCopiedBoard(final int rowToSet, final int colToSet,
                                                  final int rowToClear, final int colToClear,
                                                  final ChessBoardModel chessBoardModel,
                                                  final Color selectedPieceColor,
                                                  final ChessPiece.PieceType pieceType) {
        final ChessBoardModel tempChessBoard = chessBoardModel.createCopy();
        tempChessBoard.setPieceForCell(rowToSet, colToSet, pieceType.getPieceCode(), selectedPieceColor);
        tempChessBoard.setPieceForCell(rowToClear, colToClear, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
        return tempChessBoard;
    }

    public boolean isColorInCheckMate(final ChessBoardModel board, final Color color) {
        final boolean colorInCheck = this.isColorInCheck(board, color);
        if (!colorInCheck) {
            return false;
        }

        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (pieceColorForCell == color) {
                    if (this.canPiecePreventCheckmate(board, color, row, col, pieceColorForCell)) {
                        return false;
                    }
                }
            }
        }

        return true;
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
                if (pieceColorForCell == Color.getOpposingColor(color)) {
                    if (this.isPieceThreateningOpposingKing(board, kingRow, kingCol, row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canPiecePreventCheckmate(final ChessBoardModel board, final Color color,
                                             final int row, final int col,
                                             final Color pieceColorForCell) {
        final int pieceForCell = board.getPieceForCell(row, col);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(pieceForCell);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                pieceColorForCell);
        final List<Move> moves = chessPiece.getMoves(row, col);
        for (final Move move : moves) {
            final ChessBoardModel boardWithNextMove = this.applyMoveToCopiedBoard(move.getDestRow(),
                    move.getDestCol(), row, col, board, color, pieceType);
            if (!this.isColorInCheck(boardWithNextMove, color)) {
                return true;
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
