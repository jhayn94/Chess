package chess.core;

import chess.config.LayoutFactory;
import chess.config.MenuFactory;
import chess.config.StateFactory;
import chess.controller.ApplicationStateContext;
import chess.view.core.ApplicationRootPane;
import chess.view.core.ApplicationWindow;
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
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

public class ChessApplication extends Application {

    private static final double DEFAULT_STAGE_WIDTH = 970;

    private static final int DEFAULT_STAGE_HEIGHT = 690;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChessApplication.class);

    private ApplicationContext context;

    @Override
    public void init() {
        final ApplicationContextInitializer<GenericApplicationContext> initializer =
                this.initializeBeans();
        this.context = new SpringApplicationBuilder()
                .sources(SpringApplicationLauncher.class)
                .initializers(initializer)
                .run(this.getParameters().getRaw().toArray(new String[0]));

    }

    @Override
    public void start(final Stage stage) {
        this.registerStageInContext(stage);
        final LayoutFactory layoutFactory = this.context.getBean(LayoutFactory.class);
        final ApplicationRootPane appRootPane = layoutFactory.applicationRootPane();
        final ApplicationWindow rootStackPane = layoutFactory.applicationWindow(appRootPane);
        final Scene scene = this.createScene(rootStackPane);
        this.configureStage(stage, scene, appRootPane);
        // These messages are just to separate executions if a log file gets re-used.
        LOGGER.info("==============================================");
        LOGGER.info("Application started successfully.");
    }

    private void registerStageInContext(final Stage stage) {
        final ApplicationStateContext viewStateContext =
                this.context.getBean(ApplicationStateContext.class);
        viewStateContext.setPrimaryStage(stage);
    }

    /**
     * Creates and returns a Scene, using the given Parent object as a root element.
     */
    private Scene createScene(final Region root) {
        final Scene scene = new Scene(root);
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
    }

    private ApplicationContextInitializer<GenericApplicationContext> initializeBeans() {
        return applicationContext -> {
            // Note - be careful with order here - some beans depend on others being defined.
            applicationContext.registerBean(Application.class, () -> ChessApplication.this);
            applicationContext.registerBean(ApplicationStateContext.class);
            applicationContext.registerBean(StateFactory.class);
            applicationContext.registerBean(MenuFactory.class);
            applicationContext.registerBean(LayoutFactory.class);
        };
    }

}
