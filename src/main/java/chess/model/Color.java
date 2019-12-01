package chess.model;

public enum Color {
    WHITE, BLACK, NONE;

    /**
     * Returns true iff the two given colors are any combination of black and white.
     * @param one - first color to check.
     * @param two - second color to check.
     * @return - true iff the two given colors are any combination of black and white.
     */
    public static boolean areOpposingColors(final Color one, final Color two) {
        return (one == Color.WHITE && two == Color.BLACK) || (one == Color.BLACK && two == Color.WHITE);
    }

    /**
     * Returns the opposing color of the given.
     * @param color - color to invert.
     * @return - the opposing color
     */
    public static Color getOpposingColor(final Color color) {
        if (color == Color.NONE) {
            throw new IllegalArgumentException("Must be Color.BLACK or Color.WHITE");
        }
        return color == Color.BLACK ? Color.WHITE : Color.BLACK;
    }
}
