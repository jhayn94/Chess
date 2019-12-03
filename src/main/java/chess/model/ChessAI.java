package chess.model;

import chess.model.piece.ChessPiece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ChessAI {

    private static final int PAWN_VALUE = 100;

    private static final int KNIGHT_VALUE = 350;

    private static final int BISHOP_VALUE = 350;

    private static final int ROOK_VALUE = 525;

    private static final int QUEEN_VALUE = 1000;

    private static final int KING_VALUE = 10000;

    private static final int MAX_SEARCH_TIME = 15;

    private static final int MAX_SEARCH_DEPTH = 3;

    private static final int WINNING_SCORE = 1000000;

    private final ChessBoardModel boardAtStart;

    private final Color color;

    private final ChessModelUtils utils;

    private final ExecutorService threadPool;

    private Map<Integer, Integer> pieceValues;

    public ChessAI(final ChessBoardModel board, final Color color, final ChessModelUtils utils) {
        this.boardAtStart = board;
        this.color = color;
        this.utils = utils;
        this.threadPool = Executors.newFixedThreadPool(50);
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
            throw new IllegalStateException("AI has no moves!");
        }

        // This helps to avoid the AI swapping a piece back and forth when it can't capture anything.
        // TODO - sort by captures?
        Collections.shuffle(allMoves);
        try {
            return this.getBestScoringMove(allMoves, this.boardAtStart);
        } catch (final ExecutionException | InterruptedException e) {
            System.out.println("ERROR:" + e.getMessage());
            e.printStackTrace();
            return allMoves.get(0);
        }
    }

    private MoveWithSource getBestScoringMove(final List<MoveWithSource> allMoves,
                                              final ChessBoardModel board) throws ExecutionException, InterruptedException {
        int bestScore = Integer.MIN_VALUE;
        MoveWithSource bestMove = null;
        final List<Future<Integer>> results = new ArrayList<>();
        final List<MoveWithSource> moves = new ArrayList<>();
        for (final MoveWithSource move : allMoves) {
            moves.add(move);
            final Callable<Integer> thread = () -> this.getScoreForMove(move, board.createCopy(), 0, this.color);
            final Future<Integer> threadResult = this.threadPool.submit(thread);
            results.add(threadResult);

        }
        for (int i = 0; i < allMoves.size(); i++) {
            final int scoreForMove = results.get(i).get();
            if (scoreForMove > bestScore) {
                bestScore = scoreForMove;
                bestMove = moves.get(i);
            }
        }

        return bestMove;
    }

    private int getScoreForMove(final MoveWithSource move, final ChessBoardModel board, final int depth,
                                final Color colorToMove) {
        // If we searched past the depth limit, just return a neutral score.
        if (depth > MAX_SEARCH_DEPTH) {
            return 0;
        }

        // Calculate the immediate impact of this move if no end condition applies.
        final int capturedPieceCode = Math.abs(board.getPieceForCell(move.getDestRow(), move.getDestCol()));
        final int scoreForMove = this.pieceValues.get(capturedPieceCode);

        // Update the board with the move.
        final int movedPieceCode = board.getPieceForCell(move.getSrcRow(), move.getSrcCol());
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(movedPieceCode);
        final ChessBoardModel newBoard = this.utils.applyMoveToCopiedBoard(move, move.getSrcRow(),
                move.getSrcCol(), board, colorToMove, pieceType);

        final Color opposingColor = Color.getOpposingColor(colorToMove);
        if (this.utils.isColorInStalemate(newBoard, opposingColor)) {
            // Either player stalemates = same neutral heuristic.
            return 0;
        } else if (this.utils.isColorInCheckMate(newBoard, opposingColor)) {
            // The opponent losing should be considered as good as possible.
            return WINNING_SCORE;
        }

        // If no end condition applies, recursively search the space further to determine best move.
        final List<MoveWithSource> possibleNextMoves = this.utils.getAllMoves(newBoard,
                opposingColor);

        int probableOpponentResponse = Integer.MIN_VALUE;
        for (final MoveWithSource nextMove : possibleNextMoves) {
            final int scoreForNextMove = this.getScoreForMove(nextMove, newBoard, depth + 1,
                    opposingColor);
            if (scoreForNextMove > probableOpponentResponse) {
                probableOpponentResponse = scoreForNextMove;
            }
        }

        // Invert the score from the prior call. The goal is to maximize the even depths,
        // and minimize the score on the odd depths. Again, this is because we assume both
        // players are trying to win (and negative odds depths mean the opponent is picking
        // his best available move).
        return scoreForMove + (-1 * probableOpponentResponse);

    }

}
