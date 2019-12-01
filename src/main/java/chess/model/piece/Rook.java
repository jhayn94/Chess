package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {


    public Rook(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.ROOK);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol) {
        final List<Move> moves = new ArrayList<>();
        this.getMovesUp(sourceRow, sourceCol, moves);
        this.getMovesDown(sourceRow, sourceCol, moves);
        this.getMovesLeft(sourceRow, sourceCol, moves);
        this.getMovesRight(sourceRow, sourceCol, moves);
        return moves;
    }

    private void getMovesUp(final int sourceRow, final int sourceCol, final List<Move> moves) {
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

    private void getMovesDown(final int sourceRow, final int sourceCol, final List<Move> moves) {
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

    private void getMovesLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
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

    private void getMovesRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
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

    private boolean indexInBounds(final int nextRow) {
        return -1 < nextRow && nextRow < ChessBoardModel.BOARD_SIZE;
    }

}
