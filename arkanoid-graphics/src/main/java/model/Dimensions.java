package model;

public final class Dimensions {

    // zmienne skalujące wymiary elementow konsoli do  wersji grafiki
    public static final int SCALE_X = 9;
    public static final int SCALE_Y = 20;

    public static final int WIDTH = 100 * SCALE_X;
    public static final int HEIGHT = 30 * SCALE_Y;
    public static final int BRICK_HEIGHT = SCALE_Y;
    public static final int BRICK_WIDTH = 5 * SCALE_X;
    public static final int MARGIN = 4 * SCALE_X;
    private static final int BRICK_SECTION_HEIGHT = HEIGHT / 2;
    private static final int BRICK_SECTION_WIDTH = WIDTH;
    public static final int BRICKS_IN_ROW = BRICK_SECTION_WIDTH / (BRICK_WIDTH + MARGIN);
    public static final int BRICKS_IN_COLUMN = BRICK_SECTION_HEIGHT / (BRICK_HEIGHT + 50);
    public static final int MAX_BRICKS = BRICKS_IN_COLUMN * BRICKS_IN_ROW;
    public static final int PLATFORM_LENGTH_NORMAL = 8 * SCALE_X;
    public static final int PLATFORM_LENGTH_SHORT = 4 * SCALE_X;
    public static final int PLATFORM_LENGTH_LONG = 12 * SCALE_X;
    public static final int PLATFORM_INITIAL_POSITION_HORIZONTAL = (Dimensions.WIDTH / 2) - (Dimensions.PLATFORM_LENGTH_NORMAL / 2);
    public static final int PLATFORM_MAX_MOVEMENT = 4 * SCALE_X;
    public static final int PLATFORM_INITIAL_POSITION_VERTICAL = HEIGHT - PLATFORM_MAX_MOVEMENT;
    public static final int BALL_INITIAL_POSITION_HORIZONTAL = (Dimensions.WIDTH / 2);
    public static final int BALL_INITIAL_POSITION_VERTICAL = PLATFORM_INITIAL_POSITION_VERTICAL - 10;


}
