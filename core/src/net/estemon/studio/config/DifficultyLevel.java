package net.estemon.studio.config;


import com.badlogic.gdx.math.MathUtils;

public enum DifficultyLevel {

    EASY(
            GameConfig.ENEMY_EASY_X_SPEED,
            GameConfig.ENEMY_EASY_MAX_Y_SPEED,
            GameConfig.ENEMY_EASY_Y_ACCELERATION,
            GameConfig.ENEMY_EASY_SPAWN_TIME,
            GameConfig.BONUS_EASY_MULTIPLIER
    ),
    MEDIUM(
            GameConfig.ENEMY_MEDIUM_X_SPEED,
            GameConfig.ENEMY_MEDIUM_MAX_Y_SPEED,
            GameConfig.ENEMY_MEDIUM_Y_ACCELERATION,
            GameConfig.ENEMY_MEDIUM_SPAWN_TIME,
            GameConfig.BONUS_MEDIUM_MULTIPLIER
    ),
    HARD(
            GameConfig.ENEMY_HARD_X_SPEED,
            GameConfig.ENEMY_HARD_MAX_Y_SPEED,
            GameConfig.ENEMY_HARD_Y_ACCELERATION,
            GameConfig.ENEMY_HARD_SPAWN_TIME,
            GameConfig.BONUS_HARD_MULTIPLIER
    );

    private final float xSpeed;
    private final float maxYSpeed;
    private final float yAcceleration;
    private final float spawnTime;
    private final int bonusMultiplier;

    DifficultyLevel(float xSpeed, float maxYSpeed, float yAcceleration, float spawnTime, int bonusMultiplier) {
        this.xSpeed = xSpeed;
        this.maxYSpeed = maxYSpeed;
        this.yAcceleration = yAcceleration;
        this.spawnTime = spawnTime;
        this.bonusMultiplier = bonusMultiplier;
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

    public float getEnemySpawnTime() {
        return spawnTime;
    }

    public float getBonusSpawnTime() {
        return spawnTime * MathUtils.random(2.5f, 5f);
    }

    public int getBonusMultiplier() {
        return bonusMultiplier;
    }
}
