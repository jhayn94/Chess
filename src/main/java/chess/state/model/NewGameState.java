package chess.state.model;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.model.Color;
import chess.model.piece.ChessPiece;
import chess.state.GameState;
import javafx.util.Pair;

/**
 * This class updates the state of the application when the user starts a new game, either with
 * CTRL + N, or via the context menu.
 */
public class NewGameState extends GameState {

    public NewGameState(final ApplicationStateContext context,
                        final ModelFactory modelFactory, final ChessModelUtils utils) {
        super(context, utils, modelFactory);
        context.setIsPlayer1sTurn(true);
    }

    @Override
    public void onEnter() {
        this.clearBoard();
        this.clearHighlightedCells(true);

        final Color playerTwoColor = this.context.getChessBoardModel().isPlayerOneWhite()
                ? Color.BLACK : Color.WHITE;
        this.updateBoardWithPiece(0, 0, ChessPiece.PieceType.ROOK, playerTwoColor);
        this.updateBoardWithPiece(0, 1, ChessPiece.PieceType.KNIGHT, playerTwoColor);
        this.updateBoardWithPiece(0, 2, ChessPiece.PieceType.BISHOP, playerTwoColor);
        this.updateBoardWithPiece(0, 3, ChessPiece.PieceType.QUEEN, playerTwoColor);
        this.updateBoardWithPiece(0, 4, ChessPiece.PieceType.KING, playerTwoColor);
        this.updateBoardWithPiece(0, 5, ChessPiece.PieceType.BISHOP, playerTwoColor);
        this.updateBoardWithPiece(0, 6, ChessPiece.PieceType.KNIGHT, playerTwoColor);
        this.updateBoardWithPiece(0, 7, ChessPiece.PieceType.ROOK, playerTwoColor);

        final Color playerOneColor = Color.getOpposingColor(playerTwoColor);
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

        this.context.setIsPlayer1sTurn(this.context.getChessBoardModel().isPlayerOneWhite());
        this.context.setSelectedRow(-1);
        this.context.setSelectedCol(-1);
        this.context.getMovedPieces().clear();
        this.context.setEnpassant(new Pair<>(Color.NONE, -1));
    }

}
