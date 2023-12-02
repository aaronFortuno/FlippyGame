package net.estemon.studio.config;

public class GameConfig {

    public static final float GAME_WIDTH = 960; // pixels
    public static final float GAME_HEIGHT = 480; // pixels

    public static final float UI_WIDTH = 960f;
    public static final float UI_HEIGHT = 480f;

    public static final float WORLD_WIDTH = 12f; // world units
    public static final float WORLD_HEIGHT = 6f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2; // world units

    // common plane values
    public static final float PLANE_BOUNDS_RADIUS = 0.35f; // world units


    // player values
    public static final float PLAYER_SIZE = 0.8f; // world units
    public static final float PLAYER_WORLD_PADDING = 0.1f; // world units

    public static final float PLAYER_MAX_Y_SPEED = 0.1f; // world units
    public static final float PLAYER_ACCELERATION_Y = 0.1f;
    public static final int PLAYER_START_LIVES = 3;

    // enemies values
    public static final boolean IS_ENEMY_MOVING_Y = true;
    public static final float ENEMY_SIZE = 0.8f;
    public static final float ENEMY_EASY_MAX_Y_SPEED = 0.05f;

    public static final float ENEMY_EASY_X_SPEED = 0.05f;
    public static final float ENEMY_ACCELERATION_Y = 0.05f;
    public static final float ENEMY_SPAWN_TIME = 1f;
    public static final int ENEMY_MAX_COUNT = 10;

    // plane rotation
    public static final float PLANE_ROTATION_SPEED = 100f;
    public static final int PLANE_NORMAL_ANGLE = 0;
    public static final int PLANE_MAX_ANGLE = 20;
    public static final int PLANE_MIN_ANGLE = -PLANE_MAX_ANGLE;

    // sound effects values
    public static final float PROPELLER_PITCH_MIN = 0.6f;
    public static final float PROPELLER_PITCH_MAX = 1f;

    public static final int BACKGROUND_SPEED = 1;

    // scoring
    public static final float SCORE_MAX_TIME = 1f;
}
