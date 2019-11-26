package chess.view.menu.button;

import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractMenuButton extends Button {

	private static final String MENU_BUTTON_CSS_CLASS = "chess-system-menu-button";

	protected String resourcePath;

	protected ImageView iconView;

	public AbstractMenuButton(final String resourcePath) {
		this.resourcePath = resourcePath;
	}

	protected void configure() {
		this.setFocusTraversable(false);
		this.getStyleClass().add(MENU_BUTTON_CSS_CLASS);
		final Image icon = new Image(this.getClass().getResourceAsStream(this.resourcePath));
		this.iconView = new ImageView(icon);
		this.iconView.setCache(true);
		this.iconView.setCacheHint(CacheHint.SPEED);
		this.setIconColor();
		this.setGraphic(this.iconView);
	}

	protected void setIconColor() {
		final ColorAdjust monochrome = new ColorAdjust();
		// This HSB setup approximates the color 'chess-color-dark-red' in the
		// CSS file. For some reason, the traditional HSB setup does not work.
		monochrome.setHue(.8);
		monochrome.setSaturation(.7);
		monochrome.setBrightness(-.325);
		this.iconView.setEffect(monochrome);
	}

}
