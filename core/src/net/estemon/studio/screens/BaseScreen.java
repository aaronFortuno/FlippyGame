package net.estemon.studio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.config.GameConfig;

public abstract class BaseScreen extends ScreenAdapter {

    protected final FlippyGame game;
    protected final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public BaseScreen(FlippyGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    public void show() {
        viewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);
        stage.addActor(createUi());
    }

    protected abstract Actor createUi();

    // TODO base
}
