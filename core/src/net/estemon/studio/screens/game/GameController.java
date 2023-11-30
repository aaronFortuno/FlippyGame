package net.estemon.studio.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.entity.Background;
import net.estemon.studio.entity.Player;

public class GameController {

    private Background background;

    private Player player;
    private float rotationAngle = GameConfig.PLANE_NORMAL_ANGLE; // initial rotation angle
    private float ySpeed = 0;

    // TODO obstacles

    private float obstacleTimer;
    private float scoreTimer;

    // TODO lives
    private int score;
    private int displayScore;

    // TODO obstacle pool
    private Sound hit;

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

        // TODO init obstacle pool

        // Init background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(
                GameConfig.WORLD_WIDTH,
                GameConfig.WORLD_HEIGHT
        );

        // TODO init sound
    }

    public void update(float delta) {
        // TODO handle game over

        updatePlayer(delta);
    }

    public Background getBackground() { return background; }
    public Player getPlayer() { return player; }
    public float getRotationAngle() { return rotationAngle; }


    // Private methods
    private void updatePlayer(float delta) {
        float ySpeedCurrent = 0;

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
        if (rotationAngle > 0) {
            goDown(delta);
        } else if (rotationAngle < 0) {
            goUp(delta);
        }
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerY = MathUtils.clamp(player.getY(),
                0 + GameConfig.PLAYER_WORLD_PADDING,
                GameConfig.WORLD_HEIGHT - player.getHeight() - GameConfig.PLAYER_WORLD_PADDING
        );

        player.setPosition(player.getX(), playerY);
    }
}
