package chess.model;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.piece.ChessPiece;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

import static chess.model.ChessBoardModel.LEFT_ROOK_CASTLE_DEST_COL;
import static chess.model.ChessBoardModel.RIGHT_ROOK_CASTLE_DEST_COL;
import static chess.model.piece.Rook.LEFT_ROOK_START_COL;
import static chess.model.piece.Rook.PLAYER_ONE_ROOK_START_ROW;
import static chess.model.piece.Rook.PLAYER_TWO_ROOK_START_ROW;
import static chess.model.piece.Rook.RIGHT_ROOK_START_COL;

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
     * @param move               - move to make.
     * @param isPlayerOneTurn    - is it the first player's turn?
     * @param rowToClear         - row to make empty after the move.
     * @param colToClear         - column to make empty after the move.
     * @param chessBoardModel    - source board to use in copy.
     * @param selectedPieceColor - color of piece which will be moved.
     * @param pieceType          - type of piece that will be moved.
     * @return copy of the board with the given move applied.
     */
    public ChessBoardModel applyMoveToCopiedBoard(final Move move, final boolean isPlayerOneTurn,
                                                  final int rowToClear, final int colToClear,
                                                  final ChessBoardModel chessBoardModel,
                                                  final Color selectedPieceColor, final ChessPiece.PieceType pieceType) {
        final ChessBoardModel tempChessBoard = chessBoardModel.createCopy();
        tempChessBoard.setPieceForCell(move.getDestRow(), move.getDestCol(), pieceType.getPieceCode(), selectedPieceColor);
        tempChessBoard.setPieceForCell(rowToClear, colToClear, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
        if (Move.MoveType.CASTLE_RIGHT == move.getMoveType()) {
            if (isPlayerOneTurn) {
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, RIGHT_ROOK_START_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, RIGHT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            } else {
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, RIGHT_ROOK_START_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, RIGHT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            }
        } else if (Move.MoveType.CASTLE_LEFT == move.getMoveType()) {
            if (isPlayerOneTurn) {
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            } else {
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, LEFT_ROOK_START_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            }
        } else if (Move.MoveType.EN_PASSANT == move.getMoveType()) {
            if (isPlayerOneTurn) {
                tempChessBoard.setPieceForCell(move.getDestRow() + 1, move.getDestCol(), ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
            } else {
                tempChessBoard.setPieceForCell(move.getDestRow() - 1, move.getDestCol(),
                        ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
            }
        }

        return tempChessBoard;
    }

    /**
     * Returns true iff the given color is in checkmate.
     *
     * @param isPlayerOneTurn - is it player one's turn?
     * @param board           - chess board model.
     * @param color           - the color to check.
     * @return - true iff the given color is in checkmate.
     */
    public boolean isColorInCheckMate(final boolean isPlayerOneTurn, final ChessBoardModel board,
                                      final Color color) {
        return this.isColorInCheck(board, color) && !this.playerHasLegalMove(isPlayerOneTurn, board, color);
    }

    /**
     * Returns true iff the given color is in stalemate.
     *
     * @param isPlayerOneTurn - is it player one's turn?
     * @param board           - chess board model.
     * @param color           - the color to check.
     * @return - true iff the given color is in stalemate.
     */
    public boolean isColorInStalemate(final boolean isPlayerOneTurn, final ChessBoardModel board,
                                      final Color color) {
        return !this.isColorInCheck(board, color) && !this.playerHasLegalMove(isPlayerOneTurn, board, color);
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
     * @param context         - state context for the application.
     * @param boardBeforeMove - board before the move.
     * @param boardAfterMove  - board after the move.
     * @param color           - color making the move.
     * @param move            - move to check.
     * @return - true iff the given move is legal.
     */
    public boolean isMoveLegal(final ApplicationStateContext context, final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove,
                               final Color color, final Move move) {
        if (!this.isColorInCheck(boardAfterMove, color)) {
            if (Move.MoveType.CASTLE_LEFT == move.getMoveType()) {
                return this.isLeftCastleLegal(context, boardBeforeMove, boardAfterMove, color, move);
            } else if (Move.MoveType.CASTLE_RIGHT == move.getMoveType()) {
                return this.isRightCastleLegal(context, boardBeforeMove, boardAfterMove, color, move);
            } else if (Move.MoveType.EN_PASSANT == move.getMoveType()) {
                return this.isEnpassantLegal(context, color, move);
            }
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
     * @param isPlayerOneTurn - is it player one's turn?
     * @param board           - chess board object.
     * @param board           - chess board object.
     * @param color           - color to check.
     * @return true iff the given color has at least 1 move.
     */
    public boolean playerHasLegalMove(final boolean isPlayerOneTurn, final ChessBoardModel board,
                                      final Color color) {
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (pieceColorForCell == color) {
                    if (this.canPieceMove(isPlayerOneTurn, board, color, row, col)) {
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
     * @param isPlayerOneTurn - is it player one's turn?
     * @param board           - chess board object.
     * @param color           - color of piece to check.
     * @param row             - row to check.
     * @param col             - column to check.
     * @return - true iff the piece at the given position has any legal moves.
     */
    public boolean canPieceMove(final boolean isPlayerOneTurn, final ChessBoardModel board, final Color color,
                                final int row, final int col) {
        final int pieceForCell = board.getPieceForCell(row, col);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(pieceForCell);
        final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                color);
        final List<Move> moves = chessPiece.getMoves(row, col, true);
        for (final Move move : moves) {
            final ChessBoardModel boardWithNextMove = this.applyMoveToCopiedBoard(move, isPlayerOneTurn, row,
                    col, board, color,
                    pieceType);
            if (!this.isColorInCheck(boardWithNextMove, color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff the given right castle move is legal.
     *
     * @param context         - state context for the application.
     * @param boardBeforeMove - board before the move.
     * @param boardAfterMove  - board after the move, if proven to be valid.
     * @param color           - color making the move.
     * @param move            - move to check for validity.
     * @return - true iff the given right castle move is legal.
     */
    private boolean isRightCastleLegal(final ApplicationStateContext context, final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove,
                                       final Color color, final Move move) {
        final Set<ApplicationStateContext.MovedPieces> movedPieces = context.getMovedPieces();
        final boolean haveRookOrKingMoved = this.haveRookOrKingMoved(boardBeforeMove, color, move, movedPieces);

        return !haveRookOrKingMoved && !this.canColorThreatenCell(boardAfterMove, Color.getOpposingColor(color),
                move.getDestRow(), move.getDestCol() - 1) && !this.isColorInCheck(boardBeforeMove, color);
    }

    /**
     * Returns true iff the given left castle move is legal.
     *
     * @param context         - state context for the application.
     * @param boardBeforeMove - board before the move.
     * @param boardAfterMove  - board after the move, if proven to be valid.
     * @param color           - color making the move.
     * @param move            - move to check for validity.
     * @return - true iff the given left castle move is legal.
     */
    private boolean isLeftCastleLegal(final ApplicationStateContext context,
                                      final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove,
                                      final Color color, final Move move) {
        final Set<ApplicationStateContext.MovedPieces> movedPieces = context.getMovedPieces();
        final boolean haveRookOrKingMoved = this.haveRookOrKingMoved(boardBeforeMove, color, move, movedPieces);
        return !haveRookOrKingMoved && !this.canColorThreatenCell(boardAfterMove, Color.getOpposingColor(color),
                move.getDestRow(), move.getDestCol() + 1) && !this.isColorInCheck(boardBeforeMove, color);
    }

    /**
     * Returns true iff the given enpassant move is legal.
     *
     * @param context - state context for the application.
     * @param color   - color making the move.
     * @param move    - move to check for validity.
     * @return - true iff the given enpassant move is legal.
     */
    private boolean isEnpassantLegal(final ApplicationStateContext context,
                                     final Color color, final Move move) {
        final Pair<Color, Integer> enpassant = context.getEnpassant();
        return Color.areOpposingColors(enpassant.getKey(), color) && move.getDestCol() == enpassant.getValue();
    }

    /**
     * Returns true if either the king, or one of the rooks have moved (based on the attempted move).
     * Essentially, this returns true iff any piece has moved which should prevent the given move from occurring.
     *
     * @param board       - chess board object.
     * @param color       - color making the move.
     * @param move        - move to check for validity.
     * @param movedPieces - set of specifically tracked moved pieces.
     * @return - true iff any piece has moved which should prevent the given move from occurring.
     */
    private boolean haveRookOrKingMoved(final ChessBoardModel board, final Color color, final Move move,
                                        final Set<ApplicationStateContext.MovedPieces> movedPieces) {
        boolean haveRookOrKingMoved;
        if (this.isForPlayerOne(board, color)) {
            haveRookOrKingMoved = movedPieces.contains(ApplicationStateContext.MovedPieces.BOTTOM_KING);
        } else {
            haveRookOrKingMoved = movedPieces.contains(ApplicationStateContext.MovedPieces.TOP_KING);
        }
        if (Move.MoveType.CASTLE_LEFT == move.getMoveType()) {
            if (this.isForPlayerOne(board, color)) {
                haveRookOrKingMoved = haveRookOrKingMoved || movedPieces.contains(ApplicationStateContext.MovedPieces.BOTTOM_LEFT_ROOK);
            } else {
                haveRookOrKingMoved =
                        haveRookOrKingMoved || movedPieces.contains(ApplicationStateContext.MovedPieces.TOP_LEFT_ROOK);
            }
        } else if (Move.MoveType.CASTLE_RIGHT == move.getMoveType()) {
            if (this.isForPlayerOne(board, color)) {
                haveRookOrKingMoved = haveRookOrKingMoved ||
                        movedPieces.contains(ApplicationStateContext.MovedPieces.BOTTOM_RIGHT_ROOK);
            } else {
                haveRookOrKingMoved = haveRookOrKingMoved || movedPieces.contains(ApplicationStateContext.MovedPieces.TOP_RIGHT_ROOK);
            }
        }
        return haveRookOrKingMoved;
    }

    /**
     * Returns true iff the given board settings + color represent player one.
     *
     * @param board - chess board object.
     * @param color - color whose turn it is.
     * @return - true iff the given board settings + color represent player one.
     */
    public boolean isForPlayerOne(final ChessBoardModel board, final Color color) {
        return (Color.WHITE == color && board.isPlayerOneWhite()) || (Color.BLACK == color && !board.isPlayerOneWhite());
    }
}
