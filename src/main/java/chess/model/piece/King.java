package chess.model.piece;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.Move;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public King(final Color color, final ChessBoardModel board) {
        super(color, board, PieceType.PAWN);
    }

    @Override
    public List<Move> getMoves(final int sourceRow, final int sourceCol) {
        final List<Move> moves = new ArrayList<>();
        this.getMoveUp(sourceRow, sourceCol, moves);
        this.getMoveDown(sourceRow, sourceCol, moves);
        this.getMoveLeft(sourceRow, sourceCol, moves);
        this.getMoveRight(sourceRow, sourceCol, moves);
        this.getMoveUpRight(sourceRow, sourceCol, moves);
        this.getMoveDownRight(sourceRow, sourceCol, moves);
        this.getMoveUpLeft(sourceRow, sourceCol, moves);
        this.getMoveDownLeft(sourceRow, sourceCol, moves);
        return this.filterOutOfBoundsMoves(moves);
    }

    protected void getMoveUp(final int sourceRow, final int sourceCol, final List<Move> moves) {
        final int nextRow = sourceRow - 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, sourceCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(nextRow, sourceCol));
        }
    }

    protected void getMoveDown(final int sourceRow, final int sourceCol, final List<Move> moves) {
        final int nextRow = sourceRow + 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, sourceCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(nextRow, sourceCol));
        }
    }

    protected void getMoveLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        final int nextCol = sourceCol - 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(sourceRow, nextCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(sourceRow, nextCol));
        }
    }

    protected void getMoveRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        final int nextCol = sourceCol + 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(sourceRow, nextCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(sourceRow, nextCol));
        }
    }

    protected void getMoveUpRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        final int nextRow = sourceRow - 1;
        final int nextCol = sourceCol + 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(nextRow, nextCol));
        }
    }

    protected void getMoveDownRight(final int sourceRow, final int sourceCol, final List<Move> moves) {
        final int nextRow = sourceRow + 1;
        final int nextCol = sourceCol + 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(nextRow, nextCol));
        }
    }

    protected void getMoveUpLeft(final int sourceRow, final int sourceCol, final List<Move> moves) {
        final int nextRow = sourceRow - 1;
        final int nextCol = sourceCol - 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(nextRow, nextCol));
        }
    }

    protected void getMoveDownLeft(final int sourceRow, final int sourceCol,
                                   final List<Move> moves) {
        final int nextRow = sourceRow + 1;
        final int nextCol = sourceCol - 1;
        final Color pieceColorForCell = this.board.getPieceColorForCell(nextRow, nextCol);
        if (Color.NONE == pieceColorForCell || this.color != pieceColorForCell) {
            moves.add(new Move(nextRow, nextCol));
        }
    }

}
