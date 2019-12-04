package chess.model;

import chess.model.piece.ChessPiece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ChessAI {

    private static final int PAWN_VALUE = 100;

    private static final int KNIGHT_VALUE = 350;

    private static final int BISHOP_VALUE = 350;

    private static final int ROOK_VALUE = 525;

    private static final int QUEEN_VALUE = 1000;

    private static final int KING_VALUE = 10000;

    private static final int MAX_SEARCH_DEPTH = 75;

    private static final int WINNING_SCORE = 1000000;

    private static final int LOSING_SCORE = -1 * WINNING_SCORE;

    private final ChessBoardModel boardAtStart;

    private final Color color;

    private final ChessModelUtils utils;

    private Map<Integer, Integer> pieceValues;

    private Map<MoveWithSource, Integer> topLevelMoves;

    public ChessAI(final ChessBoardModel board, final Color color, final ChessModelUtils utils) {
        this.boardAtStart = board;
        this.color = color;
        this.utils = utils;
        this.setPieceValues();
    }

    private void setPieceValues() {
        this.pieceValues = new HashMap<>();
        this.pieceValues.put(ChessPiece.PieceType.NONE.getPieceCode(), 0);
        this.pieceValues.put(ChessPiece.PieceType.PAWN.getPieceCode(), PAWN_VALUE);
        this.pieceValues.put(ChessPiece.PieceType.ROOK.getPieceCode(), ROOK_VALUE);
        this.pieceValues.put(ChessPiece.PieceType.KNIGHT.getPieceCode(), KNIGHT_VALUE);
        this.pieceValues.put(ChessPiece.PieceType.BISHOP.getPieceCode(), BISHOP_VALUE);
        this.pieceValues.put(ChessPiece.PieceType.QUEEN.getPieceCode(), QUEEN_VALUE);
        this.pieceValues.put(ChessPiece.PieceType.KING.getPieceCode(), KING_VALUE);
    }

    public MoveWithSource findMove() {
        final List<MoveWithSource> allMoves = this.utils.getAllMoves(this.boardAtStart, this.color);
        if (allMoves.isEmpty()) {

            return null;
        }
        this.topLevelMoves = new HashMap<>(allMoves.size());

        // This helps to avoid the AI swapping a piece back and forth when it can't capture anything.
        // TODO - sort by captures?
        System.out.println("==============================");
        this.getBestMoveFromBoard(this.boardAtStart, 0, new AtomicInteger(Integer.MIN_VALUE),
                new AtomicInteger(Integer.MAX_VALUE), this.color);

        final MoveWithSource bestMove =
                this.topLevelMoves.entrySet().stream().max((entry1, entry2) -> entry1.getValue() >= entry2.getValue() ?
                1 : -1).orElseThrow(NullPointerException::new).getKey();

        return bestMove;
    }

    private int getBestMoveFromBoard(final ChessBoardModel board, final int depth, final AtomicInteger alpha,
                                     final AtomicInteger beta, final Color colorToMove) {

        if (this.utils.isColorInStalemate(board, colorToMove)) {
            // Either player stalemates = same neutral heuristic.
            return 0;
        } else if (this.utils.isColorInCheckMate(board, colorToMove)) {
            return this.color == colorToMove ? LOSING_SCORE : WINNING_SCORE;
        } else if (depth > MAX_SEARCH_DEPTH) {
            return this.getQuickScoreForMove(board);
        }

        final List<MoveWithSource> allMoves = this.utils.getAllMoves(board, colorToMove);
        final Color opposingColor = Color.getOpposingColor(colorToMove);
        if (this.color == colorToMove) {
            int bestScore = Integer.MIN_VALUE;
            for (final MoveWithSource move : allMoves) {
                final int movedPieceCode = board.getPieceForCell(move.getSrcRow(), move.getSrcCol());
                final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(movedPieceCode);
                final ChessBoardModel newBoard = this.utils.applyMoveToCopiedBoard(move, move.getSrcRow(),
                        move.getSrcCol(), board, colorToMove, pieceType);
                final int scoreForMove = this.getBestMoveFromBoard(newBoard, depth + 1, alpha, beta, opposingColor);
                bestScore = Math.max(bestScore, scoreForMove);
                alpha.set(Math.max(alpha.get(), scoreForMove));
                if (depth == 0) {
                    this.topLevelMoves.put(move, scoreForMove);
                }
                if (alpha.get() >= beta.get() && depth > 0) {
                    break;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (final MoveWithSource move : allMoves) {
                final int movedPieceCode = board.getPieceForCell(move.getSrcRow(), move.getSrcCol());
                final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(movedPieceCode);
                final ChessBoardModel newBoard = this.utils.applyMoveToCopiedBoard(move, move.getSrcRow(),
                        move.getSrcCol(), board, colorToMove, pieceType);
                final int scoreForMove = this.getBestMoveFromBoard(newBoard, depth + 1, alpha, beta, opposingColor);
                bestScore = Math.min(bestScore, scoreForMove);
                beta.set(Math.min(beta.get(), bestScore));
                if (alpha.get() >= beta.get() && depth > 1) {
                    break;
                }
            }
            return bestScore;
        }

    }

    private int getQuickScoreForMove(final ChessBoardModel board) {
        int score = 0;
        for (int row = 0; row < ChessBoardModel.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
                final Color pieceColorForCell = board.getPieceColorForCell(row, col);
                final int pieceCode = Math.abs(board.getPieceForCell(row, col));
                if (pieceColorForCell == this.color) {
                    score += this.pieceValues.get(pieceCode);
                } else if (pieceColorForCell == Color.getOpposingColor(this.color)) {
                    score -= this.pieceValues.get(pieceCode);
                }
            }
        }
        return score;
    }
}