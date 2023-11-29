package net.estemon.studio.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

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

        player.setY(player.getY());
    }
}
