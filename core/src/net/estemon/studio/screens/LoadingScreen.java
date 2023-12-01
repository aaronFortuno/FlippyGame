package net.estemon.studio.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.utils.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    // Constants
    public static final float PROGRESS_BAR_WIDTH = GameConfig.GAME_WIDTH / 2f; // world units
    public static final float PROGRESS_BAR_HEIGHT = GameConfig.GAME_HEIGHT / 8f; // world units

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.25f; // min loading wait time
    private boolean changeScreen;

    private final FlippyGame game;
    private final AssetManager assetManager;

    // constructor
    public LoadingScreen(FlippyGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // public methods
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, camera);
        renderer = new ShapeRenderer();

        // load required assets
        assetManager.load(AssetDescriptors.UI_FONT_REGULAR);
        assetManager.load(AssetDescriptors.UI_FONT_OUTLINE);
        assetManager.load(AssetDescriptors.GAMEPLAY_ATLAS);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.GAME_MUSIC);
        assetManager.load(AssetDescriptors.SOUND_PROPELLER);
        assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen) {
            game.setScreen(new SplashScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        // IMPORTANT: screens don't dispose automatically
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // private methods
    private void update(float delta) {
        //waitMillis(400); // debug purposes

        // progress is between 0 and 1
        progress = assetManager.getProgress();

        // update returns true when all assets are loaded in memory
        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true; // use this flag to avoid changing screen issues
            }
        }
    }

    private void draw() {
        float progressBarX = (GameConfig.GAME_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.GAME_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }

}
