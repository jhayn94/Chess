package chess.state.model;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.BoardHistory;
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

    }

    @Override
    public void onEnter() {
        this.context.getBoard().setIsPlayer1sTurn(true);
        this.clearBoard(true);
        this.clearHighlightedCells(true);

        final ChessBoardModel board = this.context.getBoard();
        board.getMovedPieces().clear();
        board.setEnpassant(new Pair<>(Color.NONE, -1));
        this.setBoardPieces(board);

        board.setIsPlayer1sTurn(board.isPlayerOneWhite());
        this.context.setSelectedRow(-1);
        this.context.setSelectedCol(-1);

        this.updateUndoRedoComponents();
    }

    private void setBoardPieces(final ChessBoardModel board) {
        final Color playerTwoColor = board.isPlayerOneWhite()
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
    }

    private void updateUndoRedoComponents() {
        final BoardHistory history = this.context.getHistory();
        history.clearRedoStack();
        history.clearUndoStack();
        this.updateUndoRedoButtons();
    }

}
