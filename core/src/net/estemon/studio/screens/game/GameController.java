package net.estemon.studio.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.config.GameConfig;

public class GameController {

    // TODO player
    // TODO obstacles
    // TODO background

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
        // TODO init player
        // TODO init player position

        // TODO init obstacle pool

        // TODO init background

        // TODO init sound
    }

    public void update(float delta) {
        // TODO handle game over


    }
}
