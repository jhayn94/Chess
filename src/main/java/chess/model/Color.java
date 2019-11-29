package chess.model;

public enum Color {
    BLACK, WHITE, NONE;

    /**
     * Returns true iff the two given colors are any combination of black and white.
     * @param one - first color to check.
     * @param two - second color to check.
     * @return - truee iff the two given colors are any combination of black and white.
     */
    public static boolean areOpposingColors(final Color one, final Color two) {
        return (one == Color.WHITE && two == Color.BLACK) || (one == Color.BLACK && two == Color.WHITE);
    }
}
