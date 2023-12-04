package net.estemon.studio.config;

public class GameConfig {

    // World and environment
    public static final float GAME_WIDTH = 960; // pixels
    public static final float GAME_HEIGHT = 480; // pixels

    public static final float UI_WIDTH = 960f;
    public static final float UI_HEIGHT = 480f;

    public static final float WORLD_WIDTH = 12f; // world units
    public static final float WORLD_HEIGHT = 6f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2; // world units

    // Common plane values
    public static final float PLANE_BOUNDS_RADIUS = 0.3f; // world units
    public static final float PLANE_SIZE = 0.8f; // world units


    // Player values
    public static final float PLAYER_SIZE = 0.8f; // world units
    public static final float PLAYER_WORLD_PADDING = 0.1f; // world units
    public static final float PLAYER_MIN_Y_POS = PLAYER_SIZE / 2 + PLAYER_WORLD_PADDING;
    public static final float PLAYER_MAX_Y_POS = WORLD_HEIGHT - PLAYER_SIZE / 2 - PLAYER_WORLD_PADDING;

    public static final float PLAYER_MAX_Y_SPEED = 0.1f; // world units
    public static final float PLAYER_ACCELERATION_Y = 0.1f;
    public static final int PLAYER_START_LIVES = 3;

    // Enemies values
    public static final boolean IS_ENEMY_MOVING_Y = true;
    public static final float ENEMY_SIZE = 0.8f;

    public static final float ENEMY_EASY_X_SPEED = 0.05f;
    public static final float ENEMY_MEDIUM_X_SPEED = 0.07f;
    public static final float ENEMY_HARD_X_SPEED = 0.09f;


    public static final float ENEMY_EASY_MAX_Y_SPEED = 0.02f;
    public static final float ENEMY_MEDIUM_MAX_Y_SPEED = 0.03f;
    public static final float ENEMY_HARD_MAX_Y_SPEED = 0.04f;


    public static final float ENEMY_EASY_Y_ACCELERATION = 0.03f;
    public static final float ENEMY_MEDIUM_Y_ACCELERATION = 0.07f;
    public static final float ENEMY_HARD_Y_ACCELERATION = 0.1f;


    public static final float ENEMY_EASY_SPAWN_TIME = 1f;
    public static final float ENEMY_MEDIUM_SPAWN_TIME = 0.8f;
    public static final float ENEMY_HARD_SPAWN_TIME = 0.6f;

    public static final int ENEMY_MAX_COUNT = 10;

    // Plane rotation
    public static final float PLANE_ROTATION_SPEED = 100f;
    public static final int PLANE_NORMAL_ANGLE = 0;
    public static final int PLANE_MAX_ANGLE = 20;
    public static final int PLANE_MIN_ANGLE = -PLANE_MAX_ANGLE;

    // Bullets
    public static final int BULLET_MAX_COUNT = 3;
    public static final float BULLET_RADIUS = 0.1f;
    public static final float BULLET_INIT_SPEED = 0.1f;
    public static final float BULLET_GRAVITY = -0.098f;
    public static final float BULLET_WIDTH = 0.38f;
    public static final float BULLET_HEIGHT = 0.26f;

    // Bonus values
    public static final int BONUS_MAX_COUNT = 5;
    public static final float BONUS_SIZE = 0.8f;
    public static final float BONUS_BOUNDS = BONUS_SIZE / 3;

    public static final int GOLD_BONUS = 60;
    public static final int SILVER_BONUS = 30;
    public static final int BRONZE_BONUS = 15;

    public static final int BONUS_EASY_MULTIPLIER = 1;
    public static final int BONUS_MEDIUM_MULTIPLIER = 2;
    public static final int BONUS_HARD_MULTIPLIER = 3;

    // Scoring
    public static final float SCORE_MAX_TIME = 1f;

    // Sound effects values
    public static final float PROPELLER_PITCH_MIN = 0.6f;
    public static final float PROPELLER_PITCH_MAX = 1f;

    public static final int BACKGROUND_SPEED = 1;
}
