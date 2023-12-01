package net.estemon.studio.screens.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.utils.GdxUtils;

public class UiRenderer extends ScreenAdapter {

    private GameController controller;
    private Stage stage;
    private Skin skin;
    private Label scoreLabel;

    public UiRenderer(AssetManager assetManager, Viewport uiViewport, GameController controller) {
        this.stage = new Stage(uiViewport);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.controller = controller;
        createUi();
    }

    private void createUi() {
        Table table = new Table();
        table.top().left();
        table.setFillParent(true);

        scoreLabel = new Label("SCORE: ", skin);

        table.add(scoreLabel).pad(20).padLeft(150f);
        table.debugAll();

        stage.addActor(table);
    }
    @Override
    public void render(float delta) {
        String scoreText = "SCORE: " + controller.getDisplayScore();
        scoreLabel.setText(scoreText);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() { stage.dispose(); }

}
