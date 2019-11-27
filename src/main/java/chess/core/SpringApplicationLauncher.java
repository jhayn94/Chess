package chess.core;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApplicationLauncher {

	public static void main(final String... args) {
		Application.launch(ChessApplication.class, args);
	}


}
