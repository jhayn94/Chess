package chess.model;

import chess.model.piece.ChessPiece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessAI {

    private static final int PAWN_VALUE = 100;

    private static final int KNIGHT_VALUE = 350;

    private static final int BISHOP_VALUE = 350;

    private static final int ROOK_VALUE = 525;

    private static final int QUEEN_VALUE = 1000;

    private static final int KING_VALUE = 10000;

    private static final int MAX_SEARCH_TIME = 15;

    private static final int MAX_SEARCH_DEPTH = 2;

    private static final int WINNING_SCORE = 1000000;

    private static final int LOSING_SCORE = -1000000;

    private final ChessBoardModel boardAtStart;

    private final Color color;

    private final ChessModelUtils utils;

    private final boolean maxDepthExceeded;

    private Map<Integer, Integer> pieceValues;

    public ChessAI(final ChessBoardModel board, final Color color, final ChessModelUtils utils) {
        this.boardAtStart = board;
        this.color = color;
        this.utils = utils;
        this.maxDepthExceeded = false;
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

        // TODO - sort by captures?
//        Collections.shuffle(allMoves);
        return this.getBestScoringMove(allMoves, this.boardAtStart);
    }

    private MoveWithSource getBestScoringMove(final List<MoveWithSource> allMoves, final ChessBoardModel board) {
        int bestScore = Integer.MIN_VALUE;
        MoveWithSource bestMove = null;
        for (final MoveWithSource move : allMoves) {

            final int scoreForMove = this.getScoreForMove(move, board.createCopy(), 0, this.color);
            if (scoreForMove > bestScore) {
                bestScore = scoreForMove;
                bestMove = move;
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
        if (this.utils.isColorInStalemate(newBoard, opposingColor) || this.utils.isColorInStalemate(newBoard,
                colorToMove)) {
            // Either player stalemates = same neutral heuristic.
            return 0;
        } else if (this.utils.isColorInCheckMate(newBoard, colorToMove)) {
            // My color losing should be considered as bad as possible.
            return LOSING_SCORE;
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
