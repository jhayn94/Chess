package chess.controller;

import chess.model.BoardHistory;
import chess.model.ChessBoardModel;
import chess.state.GameState;
import chess.view.core.ChessBoardView;
import chess.view.menu.EditMenu;
import chess.view.menu.button.ContextMenuButton;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

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

    private BoardHistory history;

    public ApplicationStateContext() {
        this.currentState = null;
        this.selectedCellRow = -1;
        this.selectedCellCol = -1;
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

    public ChessBoardModel getBoard() {
        return this.chessBoardModel;
    }

    public void setBoard(final ChessBoardModel chessBoardModel) {
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

    public BoardHistory getHistory() {
        return this.history;
    }

    public void setHistory(final BoardHistory history) {
        this.history = history;
    }
}
