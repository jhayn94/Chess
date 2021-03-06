package chess.view.core;

import chess.view.util.ShadowRectangle;
import javafx.geometry.Bounds;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * This class is intended to be the top level root element of any stage created
 * in the application. It defines a shadowed rectangle around the window to appear like
 * traditional Windows programs.
 */
public class ApplicationWindow extends StackPane {

    private static final String CSS_CLASS = "chess-transparent-pane";

    private static final int SHADOW_WIDTH = 15;

    private final ShadowRectangle shadowRectangle;

    private final Region applicationView;

    public ApplicationWindow(final ShadowRectangle shadowRectangle, final ApplicationRootPane applicationView) {
        this.applicationView = applicationView;
        this.shadowRectangle = shadowRectangle;
    }

    public void configure() {
        this.getStyleClass().add(CSS_CLASS);
        this.getChildren().addAll(this.shadowRectangle, this.applicationView);
    }

    /**
     * Resizes elements to make the shadow visible.
     */
    @Override
    public void layoutChildren() {
        final Bounds b = super.getLayoutBounds();
        final double w = b.getWidth();
        final double h = b.getHeight();
        this.shadowRectangle.setWidth(w - SHADOW_WIDTH * 2);
        this.shadowRectangle.setHeight(h - SHADOW_WIDTH * 2);
        this.shadowRectangle.setX(SHADOW_WIDTH);
        this.shadowRectangle.setY(SHADOW_WIDTH);
        this.applicationView.resize(w - SHADOW_WIDTH * 2, h - SHADOW_WIDTH * 2);
        this.applicationView.setLayoutX(SHADOW_WIDTH);
        this.applicationView.setLayoutY(SHADOW_WIDTH);
    }
}
