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

    public UiRenderer(AssetManager assetManager, Viewport uiViewport, GameController controller) {
        this.stage = new Stage(uiViewport);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.controller = controller;
    }

    @Override
    public void render(float delta) {
        drawTable();
        stage.act();
        stage.draw();
    }

    private void drawTable() {
        Table table = new Table();
        String scoreText = "SCORE: " + controller.getDisplayScore();
        Label score = new Label(scoreText, skin);

        Table contentTable = new Table();
        contentTable.debugAll().defaults().pad(20);
        contentTable.add(score);
        contentTable.left();

        table.add(contentTable);
        table.left();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
    }

    @Override
    public void dispose() {  }

}
