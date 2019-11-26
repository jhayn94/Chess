package chess.core;

import chess.config.LayoutFactory;
import chess.view.core.ApplicationRootPane;
import chess.view.core.RootStackPane;
import chess.view.util.ResourceConstants;
import chess.view.util.WindowHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChessApplication extends Application {

	// TODO - make smaller? Could remove the maximize stuff which simplifies / removes lots of minor bugs.
	private static final double DEFAULT_STAGE_WIDTH = 1292.5;

	private static final int DEFAULT_STAGE_HEIGHT = 690;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChessApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(ChessApplication.class, args);
	}

	private static Stage stage;

	// TODO - why can't this be in ChessConfig?
	@Bean
	public ApplicationRunner applicationRunner(final ApplicationContext ctx) {
		return args -> {
			ChessApplication.launch();
		};
	}

	// TODO - why is this needed here again?
	@Bean
	public LayoutFactory layoutFactory() {
		return new LayoutFactory();
	}

	@Override
	public void start(final Stage stage) {
		this.stage = stage;
		final ApplicationRootPane appRootPane = this.layoutFactory().createApplicationRootPane();
		final RootStackPane rootStackPane = new RootStackPane(appRootPane);
		final Scene scene = this.createScene( rootStackPane);
		this.configureStage(stage, scene,  appRootPane);
		// These messages are just to separate executions if a log file gets re-used.
		LOGGER.info("==============================================");
		LOGGER.info("Application started successfully.");
	}


	/**
	 * Creates and returns a Scene, using the given Parent object as a root element.
	 */
	private Scene createScene(final Region root) {
		final Scene scene = new Scene(root);
//		TODO - finalize stylesheets
		scene.getStylesheets().add(ResourceConstants.APPLICATION_CSS);
		scene.setFill(Color.TRANSPARENT);
		return scene;
	}

	private void configureStage(final Stage stage, final Scene scene, final ApplicationRootPane root) {
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream(ResourceConstants.APPLICATION_ICON)));
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.setMinHeight(DEFAULT_STAGE_HEIGHT);
		stage.setHeight(DEFAULT_STAGE_HEIGHT);
		stage.setMinWidth(DEFAULT_STAGE_WIDTH);
		stage.setWidth(DEFAULT_STAGE_WIDTH);
		stage.setMaximized(false);
		stage.show();
		WindowHelper.addResizeAndDragListener(stage, root);
		// TODO - needed?
		// Initializes the model controller with default states + behaviors.
//		ModelController.getInstance();
	}

	public static Stage getMainStage() {
		return stage;
	}
}
