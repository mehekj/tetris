package tetris;


/**
 * Stores important values used throughout the program.
 */
public class Constants {

    // width of each square
    public static final int SQUARE_WIDTH = 30;
    public static final int BOARD_ROWS = 20;
    public static final int BOARD_COLS = 10;
    // square thickness of the border
    public static final int BORDER = 1;
    public static final int BOTTOM_PANE_HEIGHT = 25;

    public static final double DURATION = 0.5;

    public static final int SCENE_WIDTH = SQUARE_WIDTH * (BOARD_COLS + 2 * BORDER);
    public static final int SCENE_HEIGHT = SQUARE_WIDTH * (BOARD_ROWS + 2 * BORDER) + BOTTOM_PANE_HEIGHT;

    // coordinates for squares in each tetris piece
    public static final int[][] I_PIECE_COORDS = { {0, 0}, {0, 1}, {0, 2}, {0, 3} };
    public static final int[][] T_PIECE_COORDS = { {-1, 0}, {-1, 1}, {-1, 2}, {0, 1} };
    public static final int[][] J_PIECE_COORDS = { {-1, 0}, {-1, 1}, {0, 1}, {1, 1} };
    public static final int[][] L_PIECE_COORDS = { {-1, 1}, {0, 1}, {1, 1}, {1, 0} };
    public static final int[][] O_PIECE_COORDS = { {-1, 0}, {0, 0}, {0, 1}, {-1, 1} };
    public static final int[][] S_PIECE_COORDS = { {-1, 1}, {0, 1}, {0, 0}, {1, 0} };
    public static final int[][] Z_PIECE_COORDS = { {-1, 0}, {0, 0}, {0, 1}, {1, 1} };

    // location of the first square of piece, {0, 0} in the piece coords
    public static final int PIECE_ORIGIN_X = 6;
    public static final int PIECE_ORIGIN_Y = 1;

    public static final int GAME_OVER_X = SCENE_WIDTH / 2 - 50;
    public static final int GAME_OVER_FONT = 24;
}
