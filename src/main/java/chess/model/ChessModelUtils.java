package chess.model;

import chess.config.ModelFactory;
import chess.model.piece.ChessPiece;
import chess.model.piece.King;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
     * @param rowToClear         - row to make empty after the move.
     * @param colToClear         - column to make empty after the move.
     * @param board              - source board to use in copy.
     * @param selectedPieceColor - color of piece which will be moved.
     * @param pieceType          - type of piece that will be moved.
     * @return copy of the board with the given move applied.
     */
    public ChessBoardModel applyMoveToCopiedBoard(final Move move, final int rowToClear, final int colToClear,
                                                  final ChessBoardModel board, final Color selectedPieceColor,
                                                  final ChessPiece.PieceType pieceType) {
        final ChessBoardModel tempChessBoard = board.createCopy();
        tempChessBoard.setPieceForCell(move.getDestRow(), move.getDestCol(), pieceType.getPieceCode(), selectedPieceColor);
        tempChessBoard.setPieceForCell(rowToClear, colToClear, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
        if (Move.MoveType.CASTLE_RIGHT == move.getMoveType()) {
            if (tempChessBoard.isPlayer1sTurn()) {
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, RIGHT_ROOK_START_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, RIGHT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            } else {
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, RIGHT_ROOK_START_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, RIGHT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            }
        } else if (Move.MoveType.CASTLE_LEFT == move.getMoveType()) {
            if (tempChessBoard.isPlayer1sTurn()) {
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_ONE_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            } else {
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, LEFT_ROOK_START_COL, ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
                tempChessBoard.setPieceForCell(PLAYER_TWO_ROOK_START_ROW, LEFT_ROOK_CASTLE_DEST_COL,
                        ChessPiece.PieceType.ROOK.getPieceCode(), selectedPieceColor);
            }
        } else if (Move.MoveType.EN_PASSANT == move.getMoveType()) {
            if (tempChessBoard.isPlayer1sTurn()) {
                tempChessBoard.setPieceForCell(move.getDestRow() + 1, move.getDestCol(), ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
            } else {
                tempChessBoard.setPieceForCell(move.getDestRow() - 1, move.getDestCol(),
                        ChessPiece.PieceType.NONE.getPieceCode(), Color.NONE);
            }
        }
        if (ChessPiece.PieceType.PAWN == pieceType && Math.abs(rowToClear - move.getDestRow()) == 2) {
            tempChessBoard.setEnpassant(new Pair<>(selectedPieceColor, move.getDestCol()));
        } else {
            tempChessBoard.setEnpassant(new Pair<>(Color.NONE, -1));
        }
        if (ChessPiece.PieceType.KING == pieceType && rowToClear == King.KING_START_ROW_BOTTOM) {
            tempChessBoard.getMovedPieces().add(MovedPieces.BOTTOM_KING);
        } else if (ChessPiece.PieceType.KING == pieceType && rowToClear == King.KING_START_ROW_TOP) {
            tempChessBoard.getMovedPieces().add(MovedPieces.TOP_KING);
        } else if (ChessPiece.PieceType.ROOK == pieceType) {
            if (rowToClear == PLAYER_ONE_ROOK_START_ROW && colToClear == LEFT_ROOK_START_COL) {
                tempChessBoard.getMovedPieces().add(MovedPieces.BOTTOM_LEFT_ROOK);
            } else if (rowToClear == PLAYER_ONE_ROOK_START_ROW && colToClear == RIGHT_ROOK_START_COL) {
                tempChessBoard.getMovedPieces().add(MovedPieces.BOTTOM_RIGHT_ROOK);
            } else if (rowToClear == PLAYER_TWO_ROOK_START_ROW && colToClear == LEFT_ROOK_START_COL) {
                tempChessBoard.getMovedPieces().add(MovedPieces.TOP_LEFT_ROOK);
            } else if (rowToClear == PLAYER_TWO_ROOK_START_ROW && colToClear == RIGHT_ROOK_START_COL) {
                tempChessBoard.getMovedPieces().add(MovedPieces.TOP_RIGHT_ROOK);
            }
        }
        tempChessBoard.setIsPlayer1sTurn(!tempChessBoard.isPlayer1sTurn());
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
    public boolean isColorInStalemate(final ChessBoardModel board,
                                      final Color color) {
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
            } else if (Move.MoveType.EN_PASSANT == move.getMoveType()) {
                return this.isEnpassantLegal(boardBeforeMove, color, move);
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
     * Returns a list of all moves the given color could make. Since no context of source cells is kept, a
     * special class with source and destination data is used.
     *
     * @param board - chess board object.
     * @param color - color to check for moves.
     * @return all viable moves, no ordering guaranteed.
     */
    public List<MoveWithSource> getAllMoves(final ChessBoardModel board, final Color color) {
        final List<MoveWithSource> allMoves = new ArrayList<>();
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                if (pieceColorForCell == color) {
                    final int pieceForCell = board.getPieceForCell(row, col);
                    final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(pieceForCell);
                    final ChessPiece chessPiece = this.modelFactory.chessPiece(board, pieceType,
                            pieceColorForCell);
                    final List<Move> moves = chessPiece.getMoves(row, col, true);
                    final int srcRow = row;
                    final int srcCol = col;
                    final List<MoveWithSource> aiMoves = moves.stream().map(move ->
                            new MoveWithSource(move.getDestRow(), move.getDestCol(), srcRow, srcCol,
                                    move.getMoveType())
                    ).filter(move -> {
                        final ChessBoardModel boardAfterMove = this.applyMoveToCopiedBoard(move, srcRow, srcCol, board, color, pieceType);
                        return this.isMoveLegal(board, boardAfterMove, color, move);
                    }).collect(Collectors.toList());
                    allMoves.addAll(aiMoves);
                }
            }
        }
        return allMoves;
    }

    /**
     * Returns true iff the given color has at least 1 move.
     *
     * @param board - chess board object.
     * @param color - color to check.
     * @return true iff the given color has at least 1 move.
     */
    public boolean playerHasLegalMove(final ChessBoardModel board,
                                      final Color color) {
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
            final ChessBoardModel boardWithNextMove = this.applyMoveToCopiedBoard(move, row,
                    col, board, color, pieceType);
            if (!this.isColorInCheck(boardWithNextMove, color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff the given right castle move is legal.
     *
     * @param boardBeforeMove - board before the move.
     * @param boardAfterMove  - board after the move, if proven to be valid.
     * @param color           - color making the move.
     * @param move            - move to check for validity.
     * @return - true iff the given right castle move is legal.
     */
    public boolean isRightCastleLegal(final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove,
                                      final Color color, final Move move) {
        final Set<MovedPieces> movedPieces = boardBeforeMove.getMovedPieces();
        final boolean haveRookOrKingMoved = this.haveRookOrKingMoved(boardBeforeMove, color, move, movedPieces);

        return !haveRookOrKingMoved && !this.canColorThreatenCell(boardAfterMove, Color.getOpposingColor(color),
                move.getDestRow(), move.getDestCol() - 1) && !this.isColorInCheck(boardBeforeMove, color);
    }

    /**
     * Returns true iff the given left castle move is legal.
     *
     * @param boardBeforeMove - board before the move.
     * @param boardAfterMove  - board after the move, if proven to be valid.
     * @param color           - color making the move.
     * @param move            - move to check for validity.
     * @return - true iff the given left castle move is legal.
     */
    public boolean isLeftCastleLegal(final ChessBoardModel boardBeforeMove, final ChessBoardModel boardAfterMove,
                                     final Color color, final Move move) {
        final Set<MovedPieces> movedPieces = boardBeforeMove.getMovedPieces();
        final boolean haveRookOrKingMoved = this.haveRookOrKingMoved(boardBeforeMove, color, move, movedPieces);
        return !haveRookOrKingMoved && !this.canColorThreatenCell(boardAfterMove, Color.getOpposingColor(color),
                move.getDestRow(), move.getDestCol() + 1) && !this.isColorInCheck(boardBeforeMove, color);
    }

    /**
     * Returns true iff the given enpassant move is legal.
     *
     * @param board - chess board object.
     * @param color - color making the move.
     * @param move  - move to check for validity.
     * @return - true iff the given enpassant move is legal.
     */
    public boolean isEnpassantLegal(final ChessBoardModel board, final Color color, final Move move) {
        final Pair<Color, Integer> enpassant = board.getEnpassant();
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
    public boolean haveRookOrKingMoved(final ChessBoardModel board, final Color color, final Move move,
                                       final Set<MovedPieces> movedPieces) {
        boolean haveRookOrKingMoved;
        if (this.isForPlayerOne(board, color)) {
            haveRookOrKingMoved = movedPieces.contains(MovedPieces.BOTTOM_KING);
        } else {
            haveRookOrKingMoved = movedPieces.contains(MovedPieces.TOP_KING);
        }
        if (Move.MoveType.CASTLE_LEFT == move.getMoveType()) {
            if (this.isForPlayerOne(board, color)) {
                haveRookOrKingMoved = haveRookOrKingMoved || movedPieces.contains(MovedPieces.BOTTOM_LEFT_ROOK);
            } else {
                haveRookOrKingMoved =
                        haveRookOrKingMoved || movedPieces.contains(MovedPieces.TOP_LEFT_ROOK);
            }
        } else if (Move.MoveType.CASTLE_RIGHT == move.getMoveType()) {
            if (this.isForPlayerOne(board, color)) {
                haveRookOrKingMoved = haveRookOrKingMoved ||
                        movedPieces.contains(MovedPieces.BOTTOM_RIGHT_ROOK);
            } else {
                haveRookOrKingMoved = haveRookOrKingMoved || movedPieces.contains(MovedPieces.TOP_RIGHT_ROOK);
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
