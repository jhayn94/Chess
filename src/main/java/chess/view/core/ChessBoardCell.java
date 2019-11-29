package chess.view.core;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ChessBoardCell extends StackPane {

    private static final String SELECTED_CELL_CSS_CLASS = "chess-selected-cell";

    public enum ReasonForChange {
        CLICKED_TO_SELECT, CLICKED_TO_UNSELECT, ARROWED_OFF_OF_CELL, NEW_SELECTION_CLICKED, ARROWED_ON_TO_CELL, NONE;
    }

    private static final String CSS_CLASS = "chess-puzzle-cell";

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
    public void setImage(final String iconUrl) {
        final Image image = new Image(this.getClass().getResourceAsStream(iconUrl));
        this.imageView.setImage(image);
    }

    public void configure() {
        this.addEventHandlers();
        this.setMinWidth(CELL_WIDTH);
        this.setMaxWidth(CELL_WIDTH);
        this.setMinHeight(CELL_HEIGHT);
        this.setMaxHeight(CELL_HEIGHT);
        this.getStyleClass().add(CSS_CLASS);
        this.createChildElements();
    }

    private void createChildElements() {
        this.imageView = new ImageView();
        this.cellIsSelectedIndicator = new Pane();
        this.getChildren().add(this.cellIsSelectedIndicator);
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
