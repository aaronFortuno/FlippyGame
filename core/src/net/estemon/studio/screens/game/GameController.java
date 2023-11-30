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
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.entity.Background;
import net.estemon.studio.entity.Enemy;
import net.estemon.studio.entity.Player;

public class GameController {

    private Background background;

    private Player player;
    private float rotationAngle = GameConfig.PLANE_NORMAL_ANGLE; // initial rotation angle
    private float ySpeed = 0;

    private final Array<Enemy> enemies = new Array<>();
    private float enemyTimer;
    private Pool<Enemy> enemiesPool;


    private float scoreTimer;

    private int lives = GameConfig.PLAYER_START_LIVES;
    private int score;
    private int displayScore;


    private Sound hit;
    private Music music;
    private Sound propellerSound;
    private long engine;

    private final AssetManager assetManager;

    public GameController(FlippyGame game) {
        assetManager = game.getAssetManager();
        init();
    }

    private void init() {
        // Create player
        player = new Player();

        // Initial player position
        float startPlayerX = 1 - GameConfig.PLAYER_SIZE / 2;
        float startPlayerY = GameConfig.WORLD_HEIGHT / 2 - (GameConfig.PLAYER_SIZE / 2);
        player.setPosition(startPlayerX, startPlayerY);

        // Init obstacle pool
        enemiesPool = Pools.get(Enemy.class, GameConfig.ENEMY_MAX_COUNT);

        // Init background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(
                GameConfig.WORLD_WIDTH,
                GameConfig.WORLD_HEIGHT
        );


        music = assetManager.get(AssetDescriptors.GAME_MUSIC);
        music.setLooping(true);
        music.setVolume(1f);
        music.play();


        propellerSound = assetManager.get(AssetDescriptors.SOUND_PROPELLER);
        engine = propellerSound.loop();
        propellerSound.setVolume(engine, 0.4f);
        propellerSound.setPitch(engine, 1f);
    }

    public void update(float delta) {
        // TODO handle game over
        updatePlayer(delta);
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
    public float getRotationAngle() { return rotationAngle; }
    public int getLives() { return lives; }
    public int getDisplayScore() { return displayScore; }

    public boolean isGameOver() {
        return lives <= 0;
    }


    // Private methods

    /************** PLAYER ***************/
    private boolean isPlayerCollidingWithEnemy() {
        for (Enemy enemy : enemies) {
            if (enemy.isNotHit() && enemy.isPlayerColliding(player)) {
                System.out.println("HIT!");
                return true;
            }
        }
        return false;
    }

    private void updatePlayer(float delta) {
        // Handle going up/down
        boolean isGoingUp = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean isGoingDown = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (isGoingUp) {
            goUp(delta);
        } else if (isGoingDown) {
            goDown(delta);
        } else {
            goStraight(delta);
        }

        player.setY(player.getY() + ySpeed);
        blockPlayerFromLeavingTheWorld();

        float pitchValue = mapYSpeedToPitch(ySpeed);
        propellerSound.setPitch(engine, pitchValue);
    }

    private float mapYSpeedToPitch(float ySpeed) {
        float normalizedSpeed = (ySpeed + GameConfig.MAX_PLAYER_Y_SPEED) / GameConfig.MAX_PLAYER_Y_SPEED;
        float pitch = GameConfig.PROPELLER_PITCH_MIN / 2 + (GameConfig.PROPELLER_PITCH_MAX - GameConfig.PROPELLER_PITCH_MIN) * normalizedSpeed;
        if (pitch < GameConfig.PROPELLER_PITCH_MIN) {
            pitch = GameConfig.PROPELLER_PITCH_MIN;
        } else if (pitch > GameConfig.PROPELLER_PITCH_MAX) {
            pitch = GameConfig.PROPELLER_PITCH_MAX;
        }
        return pitch;
    }

    private void goUp(float delta) {
        ySpeed += delta * GameConfig.PLAYER_ACCELERATION_Y;
        rotationAngle += delta * GameConfig.PLANE_ROTATION_SPEED;
        rotationAngle = Math.min(rotationAngle, GameConfig.PLANE_MAX_ANGLE);
    }

    private void goDown(float delta) {
        ySpeed -= delta * GameConfig.PLAYER_ACCELERATION_Y;
        rotationAngle -= delta * GameConfig.PLANE_ROTATION_SPEED;
        rotationAngle = Math.max(rotationAngle, GameConfig.PLANE_MIN_ANGLE);
    }

    private void goStraight(float delta) {
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
            if (ySpeed > GameConfig.MAX_PLAYER_Y_SPEED) {
                ySpeed = GameConfig.MAX_PLAYER_Y_SPEED;
            } else {
                ySpeed -= delta * GameConfig.PLAYER_ACCELERATION_Y;
                ySpeed = Math.max(ySpeed, 0);
            }
        } else if (ySpeed < 0) {
            if (ySpeed < -GameConfig.MAX_PLAYER_Y_SPEED) {
                ySpeed = -GameConfig.MAX_PLAYER_Y_SPEED;
            } else {
                ySpeed += delta * GameConfig.PLAYER_ACCELERATION_Y;
                ySpeed = Math.min(ySpeed, 0);
            }
        }
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerY = MathUtils.clamp(player.getY(),
                0 + GameConfig.PLAYER_WORLD_PADDING,
                GameConfig.WORLD_HEIGHT - player.getHeight() - GameConfig.PLAYER_WORLD_PADDING
        );

        player.setPosition(player.getX(), playerY);
    }

    /************** ENEMIES ***************/
    private void updateEnemies(float delta) {
        for (Enemy enemy : enemies) {
            enemy.update();
        }

        createNewEnemy(delta);
        removePassedEnemies();
    }

    private void createNewEnemy(float delta) {
        enemyTimer += delta;
        if (enemyTimer >= GameConfig.ENEMY_SPAWN_TIME) {
            float min = 0f + GameConfig.ENEMY_SIZE / 2;
            float max = GameConfig.WORLD_HEIGHT - GameConfig.ENEMY_SIZE / 2;
            float enemyX = GameConfig.WORLD_WIDTH;
            float enemyY = MathUtils.random(min, max);

            Enemy enemy = enemiesPool.obtain();

            enemy.setXSpeed(GameConfig.ENEMY_EASY_SPEED);
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
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score,
                    displayScore + (int) (60 * delta)
            );
        }
    }
}
