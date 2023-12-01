package net.estemon.studio.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.screens.BaseScreen;

public class GameScreen extends BaseScreen {

    // private final FlippyGame game;
    private final AssetManager assetManager;
    private GameController controller;
    private GameRenderer renderer;
    private UiRenderer uiRenderer;

    public GameScreen(FlippyGame game) {
        super(game);
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        controller = new GameController(game);
        renderer = new GameRenderer(game.getBatch(), assetManager, controller);
        uiRenderer = new UiRenderer(assetManager, renderer.getUiViewport(), controller);
    }

    @Override
    protected Actor createUi() {
        return null;
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
        uiRenderer.render(delta);

        // TODO handle game over
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
