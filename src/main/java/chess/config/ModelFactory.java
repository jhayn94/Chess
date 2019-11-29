package chess.config;

import chess.model.ChessBoardModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class ModelFactory {

    @Bean
    @Scope("prototype")
    public ChessBoardModel chessBoardModel(final boolean isP1White) {
        return new ChessBoardModel(isP1White);
    }

//    @Bean
//    @Scope("prototype")
//    public Move move(final ChessPiece piece) {
//        return new Move(piece, this.board());
//    }
}
