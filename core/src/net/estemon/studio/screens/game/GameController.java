package net.estemon.studio.screens.game;

import com.badlogic.gdx.Game;
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

        updatePlayer();
    }

    public Background getBackground() { return background; }
    public Player getPlayer() { return player; }


    // Private methods
    private void updatePlayer() {
        float ySpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ySpeed = GameConfig.MAX_PLAYER_Y_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ySpeed = -GameConfig.MAX_PLAYER_Y_SPEED;
        }


        player.setY(player.getY() + ySpeed);
        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerY = MathUtils.clamp(player.getY(),
                0 + GameConfig.PLAYER_WORLD_PADDING,
                GameConfig.WORLD_HEIGHT - player.getHeight() - GameConfig.PLAYER_WORLD_PADDING
        );

        player.setPosition(player.getX(), playerY);
    }
}
