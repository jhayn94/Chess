package chess.model;

import java.util.Collections;
import java.util.List;

public class ChessAI {

    private final int MAX_SEARCH_TIME = 15;

    private final int MAX_SEARCH_DEPTH = 1;

    private final ChessBoardModel board;

    private final Color color;

    private final ChessModelUtils utils;

    public ChessAI(final ChessBoardModel board, final Color color, final ChessModelUtils utils) {
        this.board = board;
        this.color = color;
        this.utils = utils;
    }

    public MoveWithSource findMove() {
        final List<MoveWithSource> allMoves = this.utils.getAllMoves(this.board, this.color);
        Collections.shuffle(allMoves);
        return allMoves.stream().findAny().orElseThrow(NullPointerException::new);
    }
}
