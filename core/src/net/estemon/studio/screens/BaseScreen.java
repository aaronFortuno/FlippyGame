package net.estemon.studio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.entity.Background;
import net.estemon.studio.screens.game.GameController;
import net.estemon.studio.utils.GdxUtils;

public abstract class BaseScreen extends ScreenAdapter {

    protected final FlippyGame game;
    protected final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;

    public BaseScreen(FlippyGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    public void show() {
        viewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        stage = new Stage(viewport, game.getBatch());
        // skin = assetManager.get(AssetPaths.UI_SKIN);

        Background background = new Background(0, 0, 100, 100, 1, assetManager);

        stage.addActor(background);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(createUi()); // UNCOMMENT WHEN READY TO ADD ACTOR
    }

    protected abstract Actor createUi();

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
