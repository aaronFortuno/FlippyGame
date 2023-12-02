package net.estemon.studio.config;


public enum DifficultyLevel {

    EASY(
            GameConfig.ENEMY_EASY_X_SPEED,
            GameConfig.ENEMY_EASY_MAX_Y_SPEED,
            GameConfig.ENEMY_EASY_Y_ACCELERATION,
            GameConfig.ENEMY_EASY_SPAWN_TIME
    ),
    MEDIUM(
            GameConfig.ENEMY_MEDIUM_X_SPEED,
            GameConfig.ENEMY_MEDIUM_MAX_Y_SPEED,
            GameConfig.ENEMY_MEDIUM_Y_ACCELERATION,
            GameConfig.ENEMY_MEDIUM_SPAWN_TIME
    ),
    HARD(
            GameConfig.ENEMY_HARD_X_SPEED,
            GameConfig.ENEMY_HARD_MAX_Y_SPEED,
            GameConfig.ENEMY_HARD_Y_ACCELERATION,
            GameConfig.ENEMY_HARD_SPAWN_TIME
    );

    private final float xSpeed;
    private final float maxYSpeed;
    private final float yAcceleration;
    private final float spawnTime;

    DifficultyLevel(float xSpeed, float maxYSpeed, float yAcceleration, float spawnTime) {
        this.xSpeed = xSpeed;
        this.maxYSpeed = maxYSpeed;
        this.yAcceleration = yAcceleration;
        this.spawnTime = spawnTime;
    }


    public float getxSpeed() {
        return xSpeed;
    }

    public float getMaxYSpeed() {
        return maxYSpeed;
    }

    public float getyAcceleration() {
        return yAcceleration;
    }

    public float getSpawnTime() {
        return spawnTime;
    }
}
