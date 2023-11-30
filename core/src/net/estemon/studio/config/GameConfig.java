package net.estemon.studio.config;

public class GameConfig {

    public static final float GAME_WIDTH = 960; // pixels
    public static final float GAME_HEIGHT = 480; // pixels

    public static final float WORLD_WIDTH = 12f; // world units
    public static final float WORLD_HEIGHT = 6f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2; // world units

    // player values
    public static final float PLAYER_BOUNDS_RADIUS = 0.4f; // world units
    public static final float PLAYER_SIZE = 2 * PLAYER_BOUNDS_RADIUS; // world units
    public static final float PLAYER_WORLD_PADDING = 0.1f; // world units

    public static final float MAX_PLAYER_Y_SPEED = 0.1f; // world units
    public static final float PLAYER_ACCELERATION_Y = 0.1f;

    // plane rotation
    public static final float PLANE_ROTATION_SPEED = 100f;
    public static final int PLANE_NORMAL_ANGLE = 0;
    public static final int PLANE_MAX_ANGLE = 20;
    public static final int PLANE_MIN_ANGLE = -PLANE_MAX_ANGLE;


    public static final int BACKGROUND_SPEED = 1;
}
