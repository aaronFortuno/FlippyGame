package net.estemon.studio.screens.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.assets.RegionNames;
import net.estemon.studio.config.GameConfig;

public class UiRenderer extends ScreenAdapter {

    private AssetManager assetManager;
    private GameController controller;
    protected Stage stage;
    private Skin skin;
    private Label scoreLabel;

    private TextureRegion livesTexture;
    private Image[] livesImages;

    public UiRenderer(AssetManager assetManager, Viewport uiViewport, GameController controller) {
        this.assetManager = assetManager;
        this.stage = new Stage(uiViewport);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.controller = controller;
        createUi();
    }

    private void createUi() {
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        livesTexture = gameplayAtlas.findRegion(RegionNames.PLAYER);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label("SCORE: ", skin);

        Table livesTable = new Table();
        int maxLives = GameConfig.PLAYER_START_LIVES;
        livesImages = new Image[maxLives];
        for (int i = 0; i < maxLives; i++)
        {
            livesImages[i] = new Image(livesTexture);
            livesTable.add(livesImages[i]).size(30).pad(5).right();
        }

        table.add(scoreLabel).pad(20).padLeft(150).expandX().left();
        //table.add(levelLabel).pad(20).uniform().expandX();
        table.add(livesTable).pad(20);

        // table.debugAll();
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(2));
        stage.addActor(table);
    }
    @Override
    public void render(float delta) {
        String scoreText = "SCORE: " + controller.getDisplayScore();
        scoreLabel.setText(scoreText);

        updateLives(controller.getLives());

        stage.act();
        stage.draw();
    }

    private void updateLives(int lives) {
        for (int i = 0; i < livesImages.length; i++) {
            livesImages[i].setSize(
                    livesTexture.getRegionWidth() / 2.5f,
                    livesTexture.getRegionHeight() / 2.5f
            );
            if (i + 1 > lives) {
                livesImages[i].setColor(1, 1, 1, 0.4f);
            }
        }
    }

    @Override
    public void dispose() { stage.dispose(); }

}
