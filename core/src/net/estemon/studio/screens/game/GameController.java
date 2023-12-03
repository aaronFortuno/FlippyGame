package net.estemon.studio.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.common.GameManager;
import net.estemon.studio.config.DifficultyLevel;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.entity.Background;
import net.estemon.studio.entity.Bonus;
import net.estemon.studio.entity.BonusKind;
import net.estemon.studio.entity.Enemy;
import net.estemon.studio.entity.Player;

public class GameController {

    private final AssetManager assetManager;

    private Background background;
    private DifficultyLevel difficultyLevel;

    // Player
    private Player player;
    private float rotationAngle = GameConfig.PLANE_NORMAL_ANGLE; // initial rotation angle
    private float ySpeed = 0;
    private float inputY1;
    private float inputY2;
    private int lives = GameConfig.PLAYER_START_LIVES;

    // Enemies
    private final Array<Enemy> enemies = new Array<>();
    private float enemyTimer;
    private Pool<Enemy> enemiesPool;
    private boolean isEnemyMovingY = GameConfig.IS_ENEMY_MOVING_Y;


    // Bonus and scoring
    private final Array<Bonus> bonuses = new Array<>();
    private float bonusTimer;
    private Pool<Bonus> bonusesPool;
    private float scoreTimer;
    private Sound bonusSound;

    private int score;
    private int displayScore;

    // Sounds
    private Sound hit;
    private Sound propellerSound;
    private long engine;
    private Sound crashSound;


    public GameController(FlippyGame game) {
        assetManager = game.getAssetManager();
        init();
    }

    private void init() {
        // Create player
        player = new Player();

        // Get difficulty level
        difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();

        // Initial player position
        float startPlayerX = 1 - GameConfig.PLAYER_SIZE / 2;
        float startPlayerY = GameConfig.WORLD_HEIGHT / 2 - (GameConfig.PLAYER_SIZE / 2);
        player.setPosition(startPlayerX, startPlayerY);

        // Init touch position
        inputY1 = player.getBounds().y;
        inputY2 = -1; // not touching

        // Init obstacle pool
        enemiesPool = Pools.get(Enemy.class, GameConfig.ENEMY_MAX_COUNT);

        // Init background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(
                GameConfig.WORLD_WIDTH,
                GameConfig.WORLD_HEIGHT
        );

        // Init bonus pool
        bonusesPool = Pools.get(Bonus.class, GameConfig.BONUS_MAX_COUNT);

        // Set music and sounds
        Music music = assetManager.get(AssetDescriptors.GAME_MUSIC);
        music.setLooping(true);
        music.setVolume(1f);
        music.play(); // uncomment to play music

        propellerSound = assetManager.get(AssetDescriptors.SOUND_PROPELLER);
        engine = propellerSound.loop();
        propellerSound.setVolume(engine, 0.45f);
        propellerSound.setPitch(engine, 1f);

        bonusSound = assetManager.get(AssetDescriptors.SOUND_BONUS);
        crashSound = assetManager.get(AssetDescriptors.SOUND_CRASH);
    }

    public void update(float delta) {
        // TODO handle game over
        if (isGameOver()) {
            return;
        }
        updatePlayer(delta);
        updateBonus(delta);

        Bonus collidedBonus = isPlayerCollidingWithBonus();
        if (collidedBonus != null) {
            updateScore(collidedBonus.getValue());
        }

        updateEnemies(delta);
        updateScore(delta);
        updateDisplayScore(delta);
        if (isPlayerCollidingWithEnemy()) {
            lives--;
            if (isGameOver()) {
                GameManager.INSTANCE.updateHighScore(score);
            }
        }
    }

    // Getters
    public Background getBackground() { return background; }
    public Player getPlayer() { return player; }
    public Array<Enemy> getEnemies() { return enemies; }
    public Array<Bonus> getBonuses() { return bonuses; }
    public float getRotationAngle() { return rotationAngle; }
    public int getLives() { return lives; }
    public int getDisplayScore() { return displayScore; }

    public void stopPropellerSound() {
        propellerSound.stop();
    }

    public boolean isGameOver() {
        return lives <= 0;
    }


    // Private methods

    /************** PLAYER ***************/
    private boolean isPlayerCollidingWithEnemy() {
        for (Enemy enemy : enemies) {
            if (enemy.isNotHit() && enemy.isPlayerColliding(player)) {
                System.out.println("[HIT]");
                crashSound.play(0.7f);
                return true;
            }
        }
        return false;
    }

    private Bonus isPlayerCollidingWithBonus() {
        for (Bonus bonus : bonuses) {
            if (bonus.isNotHit() && bonus.isPlayerColliding(player)) {
                System.out.println("[BONUS]");
                bonus.setCollided(true);
                bonusSound.play(0.7f);
                return bonus;
            }
        }
        return null;
    }

    private void updatePlayer(float delta) {

        // Handle going up/down
        float screenHeight = Gdx.graphics.getHeight();
        inputY1 = screenHeight - Gdx.input.getY(); // Inverse Y axis system
        boolean isTouched = Gdx.input.isTouched();

        if (isTouched) {

            // Check if is first touching
            if (inputY2 == -1) {
                inputY2 = inputY1;
            }

            // Relative change at Y
            float deltaY = inputY1 - inputY2;

            // Check if there's significative change
            if (Math.abs(deltaY) > 2) {
                if (deltaY > 0) {
                    if (checkIfCanGoUp()) {
                        goUp(delta);
                    } else {
                        goStraight(delta);
                    }
                } else {
                    if (checkIfCanGoDown()) {
                        goDown(delta);
                    } else {
                        goStraight(delta);
                    }
                }
            }

            // Update inputY2 at end of touching
            inputY2 = inputY1;
        } else {

            // Reset inputY2 if there's no touch
            inputY2 = -1;

            // Handle keystrokes only if there's no touching screen or dragging player
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (checkIfCanGoUp()) {
                    goUp(delta);
                } else {
                    goStraight(delta);
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (checkIfCanGoDown()) {
                    goDown(delta);
                } else {
                    goStraight(delta);
                }
            } else {
                goStraight(delta);
            }
        }

        player.setY(player.getY() + ySpeed);
        blockPlayerFromLeavingTheWorld();

        float pitchValue = mapYSpeedToPitch(ySpeed);
        propellerSound.setPitch(engine, pitchValue);
    }

    private boolean checkIfCanGoUp() {
        return player.getBounds().y < GameConfig.PLAYER_MAX_Y_POS;
    }

    private boolean checkIfCanGoDown() {
        return player.getBounds().y > GameConfig.PLAYER_MIN_Y_POS;
    }

    private float mapYSpeedToPitch(float ySpeed) {
        float normalizedSpeed = (ySpeed + GameConfig.PLAYER_MAX_Y_SPEED) / GameConfig.PLAYER_MAX_Y_SPEED;
        float pitch = GameConfig.PROPELLER_PITCH_MIN / 2 + (GameConfig.PROPELLER_PITCH_MAX - GameConfig.PROPELLER_PITCH_MIN) * normalizedSpeed;
        if (pitch < GameConfig.PROPELLER_PITCH_MIN) {
            pitch = GameConfig.PROPELLER_PITCH_MIN;
        } else if (pitch > GameConfig.PROPELLER_PITCH_MAX) {
            pitch = GameConfig.PROPELLER_PITCH_MAX;
        }
        return pitch;
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerY = MathUtils.clamp(player.getY(),
                0 + GameConfig.PLAYER_WORLD_PADDING,
                GameConfig.WORLD_HEIGHT - player.getHeight() - GameConfig.PLAYER_WORLD_PADDING
        );

        player.setPosition(player.getX(), playerY);
    }

    /************** PLANE COMMON ***************/

    public void goUp(float delta) {
        ySpeed += delta * GameConfig.PLAYER_ACCELERATION_Y;
        rotationAngle += delta * GameConfig.PLANE_ROTATION_SPEED;
        rotationAngle = Math.min(rotationAngle, GameConfig.PLANE_MAX_ANGLE);
    }

    public void goDown(float delta) {
        ySpeed -= delta * GameConfig.PLAYER_ACCELERATION_Y;
        rotationAngle -= delta * GameConfig.PLANE_ROTATION_SPEED;
        rotationAngle = Math.max(rotationAngle, GameConfig.PLANE_MIN_ANGLE);
    }


    public void goStraight(float delta) {
        // Normalising rotation angle
        if (rotationAngle > 0) {
            rotationAngle -= delta * GameConfig.PLANE_ROTATION_SPEED;
            rotationAngle = Math.max(rotationAngle, 0);
        } else if (rotationAngle < 0) {
            rotationAngle += delta * GameConfig.PLANE_ROTATION_SPEED;
            rotationAngle = Math.min(rotationAngle, 0);
        }

        // Normalising y speed
        if (ySpeed > 0) {
            if (ySpeed > GameConfig.PLAYER_MAX_Y_SPEED) {
                ySpeed = GameConfig.PLAYER_MAX_Y_SPEED;
            } else {
                ySpeed -= delta * GameConfig.PLAYER_ACCELERATION_Y;
                ySpeed = Math.max(ySpeed, 0);
            }
        } else if (ySpeed < 0) {
            if (ySpeed < -GameConfig.PLAYER_MAX_Y_SPEED) {
                ySpeed = -GameConfig.PLAYER_MAX_Y_SPEED;
            } else {
                ySpeed += delta * GameConfig.PLAYER_ACCELERATION_Y;
                ySpeed = Math.min(ySpeed, 0);
            }
        }
    }

    /*************** BONUS ****************/
    private void updateBonus(float delta) {
        for (Bonus bonus : bonuses) {
            bonus.update(delta);
        }
        createNewBonus(delta);
        removePassedBonuses();
    }

    private void createNewBonus(float delta) {
        bonusTimer += delta;
        if (bonusTimer >= difficultyLevel.getBonusSpawnTime()) {
            System.out.println("[bonus spawnTime] " + difficultyLevel.getBonusSpawnTime());
            float min = 0f + GameConfig.BONUS_SIZE / 2;
            float max = GameConfig.WORLD_HEIGHT - GameConfig.BONUS_SIZE / 2;
            float bonusX = GameConfig.WORLD_WIDTH;
            float bonusY = MathUtils.random(min, max);;
            Bonus bonus = bonusesPool.obtain();

            int bonusType = MathUtils.random(0, 10);
            if (bonusType <= 1) {
                bonus.setKind(BonusKind.GOLD);
            } else if (bonusType <= 4) {
                bonus.setKind(BonusKind.SILVER);
            } else {
                bonus.setKind(BonusKind.BRONZE);
            }
            bonus.setXSpeed(difficultyLevel.getxSpeed());
            System.out.println("[bonus xSpeed] " + bonus.getxSpeed());
            bonus.setPosition(bonusX, bonusY);

            bonuses.add(bonus);
            bonusTimer = 0f;
        }
    }

    private void removePassedBonuses() {
        if (bonuses.size > 0 ) {
            Bonus first = bonuses.first();

            float minBonusX = -GameConfig.BONUS_SIZE;

            if (first.getX() < minBonusX) {
                bonuses.removeValue(first, true);
                bonusesPool.free(first);
            }
        }
    }

    /************** ENEMIES ***************/
    private void updateEnemies(float delta) {
        for (Enemy enemy : enemies) {

            // Move Y axis enemies depending on configuration
            if (isEnemyMovingY) {
                if (enemy.isGoUp()) {
                    enemy.goUp(delta);
                } else if (enemy.isGoDown()) {
                    enemy.goDown(delta);
                } else {
                    enemy.goStraight(delta);
                }

                if (enemy.getBounds().y > GameConfig.WORLD_HEIGHT - 1) {
                    enemy.setGoDown();
                    enemy.goDown(delta);
                } if (enemy.getBounds().y < 1) {
                    enemy.setGoUp();
                    enemy.goUp(delta);
                }

                decideIfGoUpDownStraight(delta, enemy);
            }

            enemy.update(delta);
        }

        createNewEnemy(delta);
        removePassedEnemies();
    }

    private static void decideIfGoUpDownStraight(float delta, Enemy enemy) {
        if (enemy.getTimer() > 1f) {
            if (MathUtils.randomBoolean()) {
                if (MathUtils.randomBoolean()) {
                    enemy.setGoUp();
                    enemy.goUp(delta);
                } else {
                    enemy.setGoDown();
                    enemy.goDown(delta);
                }
            } else {
                enemy.goStraight(delta);
            }
            enemy.setTimer(0f);
        }
    }

    private void createNewEnemy(float delta) {
        enemyTimer += delta;
        if (enemyTimer >= difficultyLevel.getEnemySpawnTime()) {
            float min = 0f + GameConfig.ENEMY_SIZE / 2;
            float max = GameConfig.WORLD_HEIGHT - GameConfig.ENEMY_SIZE / 2;
            float enemyX = GameConfig.WORLD_WIDTH;
            float enemyY = MathUtils.random(min, max);

            Enemy enemy = enemiesPool.obtain();

            enemy.setXSpeed(difficultyLevel.getxSpeed());
            enemy.setPosition(enemyX, enemyY);

            enemies.add(enemy);
            enemyTimer = 0f;
        }
    }

    private void removePassedEnemies() {
        if (enemies.size > 0) {
            Enemy first = enemies.first();

            float minEnemyX = -GameConfig.ENEMY_SIZE;

            if (first.getX() < minEnemyX) {
                enemies.removeValue(first, true);
                enemiesPool.free(first);
            }
        }
    }

    /************** SCORING ***************/
    private void updateScore(float delta) {
        scoreTimer += delta;
        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0f;
        }
    }

    private void updateScore(int bonusPoints) {
        int totalPoints = bonusPoints * difficultyLevel.getBonusMultiplier();
        System.out.println("[bonus points] " + totalPoints);
        score += totalPoints;
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score,
                    displayScore + (int) (60 * delta)
            );
        }
    }
}
