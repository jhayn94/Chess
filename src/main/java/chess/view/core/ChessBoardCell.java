package chess.view.core;

import chess.model.ChessBoardModel;
import chess.model.Color;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ChessBoardCell extends StackPane {

    public enum ReasonForChange {
        CLICKED_TO_SELECT, CLICKED_TO_UNSELECT, ARROWED_OFF_OF_CELL, NEW_SELECTION_CLICKED, ARROWED_ON_TO_CELL, NONE;
    }

    private static final String SELECTED_CELL_CSS_CLASS = "chess-selected-cell";

    private static final String CSS_CLASS = "chess-board-cell";

    private static final String CSS_CLASS_ALTERNATE = "chess-board-cell-alternate";

    private static final String CSS_CLASS_BORDER_TOP = "chess-board-cell-border-top";

    private static final String CSS_CLASS_BORDER_BOTTOM = "chess-board-cell-border-bottom";

    private static final String CSS_CLASS_BORDER_LEFT = "chess-board-cell-border-left";

    private static final String CSS_CLASS_BORDER_RIGHT = "chess-board-cell-border-right";

    private static final String CSS_CLASS_BORDER_TOP_RIGHT = "chess-board-cell-border-top-right";

    private static final String CSS_CLASS_BORDER_BOTTOM_RIGHT = "chess-board-cell-border-bottom" +
            "-right";

    private static final String CSS_CLASS_BORDER_TOP_LEFT = "chess-board-cell-border-top-left";

    private static final String CSS_CLASS_BORDER_BOTTOM_LEFT = "chess-board-cell-border-bottom" +
            "-left";

    private static final int CELL_HEIGHT = 62;

    public static final int CELL_WIDTH = 62;

    private int row;

    private int col;

    private Pane cellIsSelectedIndicator;

    private ImageView imageView;

    /**
     * Sets a pane on top of all other elements to have or not have a special CSS
     * class that denotes the cell as selected by the user.
     */
    public void setIsSelected(final boolean isSelected) {
        if (isSelected) {
            this.cellIsSelectedIndicator.getStyleClass().add(SELECTED_CELL_CSS_CLASS);
        } else {
            this.cellIsSelectedIndicator.getStyleClass().remove(SELECTED_CELL_CSS_CLASS);
        }
    }

    /**
     * Sets the image of this cell.
     */
    public void setImage(final String iconUrl, final Color color) {
        final Image image = new Image(this.getClass().getResourceAsStream(iconUrl));
        this.imageView.setImage(image);
        final ColorAdjust monochrome = new ColorAdjust();
        if (Color.BLACK == color) {
            monochrome.setBrightness(-.7);
        } else {
//            monochrome.setHue(-.5);
            monochrome.setSaturation(-.5);
            monochrome.setBrightness(-.2);
        }
        this.imageView.setEffect(monochrome);
    }

    public void configure() {
        this.addEventHandlers();
        this.setMinWidth(CELL_WIDTH);
        this.setMaxWidth(CELL_WIDTH);
        this.setMinHeight(CELL_HEIGHT);
        this.setMaxHeight(CELL_HEIGHT);
        this.createChildElements();
    }

    public void updateCellStyle() {
        final ObservableList<String> styleClass = this.getStyleClass();
        if ((this.row + this.col) % 2 == 1) {
            styleClass.add(CSS_CLASS);
        } else {
            styleClass.add(CSS_CLASS_ALTERNATE);
        }
        if (this.row == 0) {
            if (this.col == 0) {
                styleClass.add(CSS_CLASS_BORDER_TOP_LEFT);
            } else if (this.col == ChessBoardModel.BOARD_SIZE - 1) {
                styleClass.add(CSS_CLASS_BORDER_TOP_RIGHT);
            } else {
                styleClass.add(CSS_CLASS_BORDER_TOP);
            }
        } else if (this.row == ChessBoardModel.BOARD_SIZE - 1) {
            if (this.col == 0) {
                styleClass.add(CSS_CLASS_BORDER_BOTTOM_LEFT);
            } else if (this.col == ChessBoardModel.BOARD_SIZE - 1) {
                styleClass.add(CSS_CLASS_BORDER_BOTTOM_RIGHT);
            } else {
                styleClass.add(CSS_CLASS_BORDER_BOTTOM);
            }
        } else if (this.col == 0) {
            styleClass.add(CSS_CLASS_BORDER_LEFT);
        } else if (this.col == ChessBoardModel.BOARD_SIZE - 1) {
            styleClass.add(CSS_CLASS_BORDER_RIGHT);
        }
    }

    private void createChildElements() {
        this.imageView = new ImageView();
        this.imageView.setFitHeight(CELL_HEIGHT - 14);
        this.imageView.setFitWidth(CELL_WIDTH - 14);
        this.cellIsSelectedIndicator = new Pane();
        this.getChildren().addAll(this.imageView);
    }

    private EventHandler<MouseEvent> onClick() {
        return event -> {
            if (MouseButton.PRIMARY == event.getButton()) {
                System.out.println("CLICKED CELL " + this.row + " " + this.col);
//                ModelController.getInstance().transitionToClickedCellState(this.row, this.col, event);
            }
        };
    }

    /**
     * This method resets the cell's event handlers to the current state's handler.
     * When the cell's state changes, event handlers have to be re-registered for
     * the new state to be used.
     */
    // TODO what does this mean? Does it apply?
    private void addEventHandlers() {
        this.setEventHandler(MouseEvent.MOUSE_CLICKED, this.onClick());
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setRow(final int row) {
        this.row = row;
    }

    public void setCol(final int col) {
        this.col = col;
    }
}
