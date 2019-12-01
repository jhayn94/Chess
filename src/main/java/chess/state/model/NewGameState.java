package chess.state.model;

import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.piece.ChessPiece;
import chess.state.GameState;

/**
 * This class updates the state of the application when the user starts a new game, either with
 * CTRL + N, or via the context menu.
 */
public class NewGameState extends GameState {

    public NewGameState(final ApplicationStateContext context) {
        super(context);
    }

    @Override
    public void onEnter() {
        this.clearBoard();

        final Color playerTwoColor = Color.BLACK;
        this.updateBoardWithPiece(0, 0, ChessPiece.PieceType.ROOK, playerTwoColor);
        this.updateBoardWithPiece(0, 1, ChessPiece.PieceType.KNIGHT, playerTwoColor);
        this.updateBoardWithPiece(0, 2, ChessPiece.PieceType.BISHOP, playerTwoColor);
        this.updateBoardWithPiece(0, 3, ChessPiece.PieceType.QUEEN, playerTwoColor);
        this.updateBoardWithPiece(0, 4, ChessPiece.PieceType.KING, playerTwoColor);
        this.updateBoardWithPiece(0, 5, ChessPiece.PieceType.BISHOP, playerTwoColor);
        this.updateBoardWithPiece(0, 6, ChessPiece.PieceType.KNIGHT, playerTwoColor);
        this.updateBoardWithPiece(0, 7, ChessPiece.PieceType.ROOK, playerTwoColor);

        final Color playerOneColor = Color.WHITE;
        this.updateBoardWithPiece(7, 0, ChessPiece.PieceType.ROOK, playerOneColor);
        this.updateBoardWithPiece(7, 1, ChessPiece.PieceType.KNIGHT, playerOneColor);
        this.updateBoardWithPiece(7, 2, ChessPiece.PieceType.BISHOP, playerOneColor);
        this.updateBoardWithPiece(7, 3, ChessPiece.PieceType.QUEEN, playerOneColor);
        this.updateBoardWithPiece(7, 4, ChessPiece.PieceType.KING, playerOneColor);
        this.updateBoardWithPiece(7, 5, ChessPiece.PieceType.BISHOP, playerOneColor);
        this.updateBoardWithPiece(7, 6, ChessPiece.PieceType.KNIGHT, playerOneColor);
        this.updateBoardWithPiece(7, 7, ChessPiece.PieceType.ROOK, playerOneColor);

        for (int col = 0; col < ChessBoardModel.BOARD_SIZE; col++) {
            this.updateBoardWithPiece(1, col, ChessPiece.PieceType.PAWN, playerTwoColor);
            this.updateBoardWithPiece(6, col, ChessPiece.PieceType.PAWN, playerOneColor);
        }

    }

}
