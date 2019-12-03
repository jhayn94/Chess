package chess.config;

import chess.model.BoardHistory;
import chess.model.ChessBoardModel;
import chess.model.ChessModelUtils;
import chess.model.Color;
import chess.model.piece.Bishop;
import chess.model.piece.ChessPiece;
import chess.model.piece.King;
import chess.model.piece.Knight;
import chess.model.piece.Pawn;
import chess.model.piece.Queen;
import chess.model.piece.Rook;
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

    @Bean
    public BoardHistory boardHistory() {
        return new BoardHistory();
    }

    @Bean
    @Scope("prototype")
    public ChessModelUtils chessModelUtils() {
        return new ChessModelUtils(this);
    }

    public ChessPiece chessPiece(final ChessBoardModel board, final ChessPiece.PieceType type,
                                 final Color color) {
        if (ChessPiece.PieceType.PAWN == type) {
            return new Pawn(color, board);
        } else if (ChessPiece.PieceType.ROOK == type) {
            return new Rook(color, board);
        } else if (ChessPiece.PieceType.KNIGHT == type) {
            return new Knight(color, board);
        } else if (ChessPiece.PieceType.BISHOP == type) {
            return new Bishop(color, board);
        } else if (ChessPiece.PieceType.QUEEN == type) {
            return new Queen(color, board);
        } else if (ChessPiece.PieceType.KING == type) {
            return new King(color, board);
        } else {
            throw new IllegalArgumentException("Type " + type + " not recognized.");
        }
    }
}
