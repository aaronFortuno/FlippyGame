package net.estemon.studio.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetDescriptors;
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
    private Music music;
    private Sound propellerSound;
    private long engine1;
    private long engine2;

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

        // TODO init music
        music = assetManager.get(AssetDescriptors.GAME_MUSIC);
        music.setLooping(true);
        music.setVolume(1f);
        music.play();

        // TODO init sounds
        propellerSound = assetManager.get(AssetDescriptors.SOUND_PROPELLER);
        //engine1 = propellerSound.loop();
        //propellerSound.setPitch(engine1, 1f);

        engine2 = propellerSound.loop();
        propellerSound.setVolume(engine2, 0.5f);
        propellerSound.setPitch(engine2, 1f);

        // propellerSound.play();
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
        propellerSound.setPitch(engine2, pitchValue);
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
}
