package chess.view.core;

import chess.config.LayoutFactory;
import chess.config.StateFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * This class corresponds to the view in the center of the screen. It contains
 * all other view elements on this side of the application.
 */
public class ChessBoardView extends GridPane {

    private static final String CSS_CLASS = "chess-transparent-pane";

    private static final int DEFAULT_WIDTH =
            ChessBoardCell.CELL_WIDTH * ChessBoardModel.BOARD_SIZE + 20;

    private static final int NUM_CELLS_TOTAL = ChessBoardModel.BOARD_SIZE * ChessBoardModel.BOARD_SIZE;

    private final ApplicationStateContext stateContext;

    private final StateFactory stateFactory;

    private final LayoutFactory layoutFactory;

    public ChessBoardView(final ApplicationStateContext stateContext, final StateFactory stateFactory,
                          final LayoutFactory layoutFactory) {
        this.stateContext = stateContext;
        this.stateFactory = stateFactory;
        this.layoutFactory = layoutFactory;
    }

    public void configure() {
        this.getStyleClass().add(CSS_CLASS);
        this.setPadding(new Insets(15));
        this.setMinWidth(DEFAULT_WIDTH);
        this.setMaxWidth(DEFAULT_WIDTH);
        this.setOnKeyPressed(this.onKeyPressed());
        this.createChildElements();
        this.stateContext.setChessBoardView(this);
        this.requestFocus();
    }

    private void createChildElements() {
        for (int index = 1; index <= NUM_CELLS_TOTAL; index++) {
            // Integer division intentional!
            final int rowIndex = (index - 1) / ChessBoardModel.BOARD_SIZE;
            final int colIndex = (index - 1) % ChessBoardModel.BOARD_SIZE;
            final ChessBoardCell chessBoardCell =
                    this.layoutFactory.chessBoardCell();
            // TODO - if something is wrong, try flipping these?
            chessBoardCell.setRow(rowIndex);
            chessBoardCell.setCol(colIndex);
            this.add(chessBoardCell, colIndex, rowIndex);
        }
    }

    /**
     * Handles all keyboard inputs for the application. The technical challenge with
     * keyboard inputs is that the node must be focused to receive input, and only
     * one node can be focused at once. This makes it impossible to listen on
     * multiple nodes at once.
     */
    private EventHandler<? super KeyEvent> onKeyPressed() {
        return event -> {
            final KeyCode keyCode = event.getCode();
            if (KeyCode.M == keyCode && event.isControlDown()) {
                this.stateContext.changeState(this.stateFactory.toggleContextMenuState());
            }
        };
    }

}
