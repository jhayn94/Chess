package chess.config;

import chess.model.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChessBeanConfig {

    @Bean
    public TestBean testBean() {
        return new TestBean();
    }
}
