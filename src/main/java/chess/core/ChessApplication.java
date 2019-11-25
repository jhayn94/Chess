package chess.core;

import chess.config.ChessBeanConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChessApplication extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChessApplication.class, args);
	}

	// TODO - why can't this be in ChessConfig?
	@Bean
	public ApplicationRunner applicationRunner(ApplicationContext ctx) {
		return args -> {
			ChessApplication.launch();
		};
	}

	@Bean
	public ChessBeanConfig chessBeanConfig() {
		return new ChessBeanConfig();
	}

	@Override
	public void start(Stage stage) {
		String javaVersion = System.getProperty("java.version");
		String javafxVersion = System.getProperty("javafx.version");
		Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
		Scene scene = new Scene(new StackPane(l), 640, 480);
		stage.setScene(scene);
		stage.show();
	}
}
