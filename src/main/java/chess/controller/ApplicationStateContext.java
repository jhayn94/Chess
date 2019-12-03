package chess.controller;

import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.state.GameState;
import chess.view.core.ChessBoardView;
import chess.view.menu.EditMenu;
import chess.view.menu.button.ContextMenuButton;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationStateContext {

    private GameState currentState;

    // View components.
    private Stage primaryStage;

    private ChessBoardView chessBoardView;

    private EditMenu editMenu;

    private ContextMenuButton contextMenuButton;

    // Model Components.

    private ChessBoardModel chessBoardModel;

    private int selectedCellRow;

    private int selectedCellCol;

    private boolean isPlayer1sTurn;

    // Tracks which pieces have moved that affect eligibility for castling.
    private final Set<MovedPieces> movedPieces;

    public enum MovedPieces {
        BOTTOM_KING, TOP_KING, BOTTOM_LEFT_ROOK, BOTTOM_RIGHT_ROOK, TOP_LEFT_ROOK, TOP_RIGHT_ROOK;
    }

    // Tracks if a pawn last moved two cells, making it able to be captured via en passant.
    private Pair<Color, Integer> enpassant;

    public ApplicationStateContext() {
        this.currentState = null;
        this.selectedCellRow = -1;
        this.selectedCellCol = -1;
        this.enpassant = new Pair<>(Color.NONE, -1);
        this.movedPieces = new HashSet<>();
    }

    public void changeState(final GameState newState) {
        this.currentState = newState;
        this.currentState.onEnter();
    }

    public void setPrimaryStage(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void setEditMenu(final EditMenu editMenu) {
        this.editMenu = editMenu;
    }

    public void setContextMenuButton(final ContextMenuButton contextMenuButton) {
        this.contextMenuButton = contextMenuButton;
    }

    public ContextMenuButton getContextMenuButton() {
        return this.contextMenuButton;
    }

    public EditMenu getEditMenu() {
        return this.editMenu;
    }

    public ChessBoardView getChessBoardView() {
        return this.chessBoardView;
    }

    public void setChessBoardView(final ChessBoardView chessBoardView) {
        this.chessBoardView = chessBoardView;
    }

    public ChessBoardModel getChessBoardModel() {
        return this.chessBoardModel;
    }

    public void setChessBoardModel(final ChessBoardModel chessBoardModel) {
        this.chessBoardModel = chessBoardModel;
    }

    public void setSelectedRow(final int selectedCellRow) {
        this.selectedCellRow = selectedCellRow;
    }

    public void setSelectedCol(final int selectedCellCol) {
        this.selectedCellCol = selectedCellCol;
    }

    public int getSelectedCellRow() {
        return this.selectedCellRow;
    }

    public int getSelectedCellCol() {
        return this.selectedCellCol;
    }

    public boolean isPlayer1sTurn() {
        return this.isPlayer1sTurn;
    }

    public void setIsPlayer1sTurn(final boolean isPlayer1sTurn) {
        this.isPlayer1sTurn = isPlayer1sTurn;
    }

    public Set<MovedPieces> getMovedPieces() {
        return this.movedPieces;
    }

    public Pair<Color, Integer> getEnpassant() {
        return this.enpassant;
    }

    public void setEnpassant(final Pair<Color, Integer> enpassant) {
        this.enpassant = enpassant;
    }
}
