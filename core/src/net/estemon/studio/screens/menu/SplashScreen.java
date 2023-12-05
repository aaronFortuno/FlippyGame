package net.estemon.studio.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.assets.RegionNames;
import net.estemon.studio.common.GameMusic;
import net.estemon.studio.screens.common.BaseScreen;
import net.estemon.studio.screens.game.GameScreen;

public class SplashScreen extends BaseScreen {

    private final AssetManager assetManager;

    private GameMusic music;

    public SplashScreen(FlippyGame game) {
        super(game);
        assetManager = game.getAssetManager();
    }

    @Override
    protected Actor createUi() {
        // Setup background music
        music = new GameMusic(assetManager);

        // Setup screen table
        Table table = new Table();
        table.defaults().pad(20);
        // table.debugAll();

        // Setup background
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Setup content table
        Table contentTable = new Table(skin);
        contentTable.defaults().pad(10);
        // contentTable.debugAll();
        contentTable.center();

        // Title label
        TypingLabel titleLabel = new TypingLabel("{WIND=0.5;1;0.5;0.2}flippy!", skin, "title-font");

        // Play button
        TextButton playButton = new TextButton("PLAY", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, music));
            }
        });

        // High score button
        TextButton highScoreButton = new TextButton("HIGH SCORE", skin);
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HighScoreScreen(game));
            }
        });

        // Settings button
        TextButton settingsButton = new TextButton("SETTINGS", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        // Quit button
        TextButton quitButton = new TextButton("QUIT", skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // Add content to content table
        contentTable.add(titleLabel).padBottom(30f).row();
        contentTable.add(playButton).fill().row();
        contentTable.add(highScoreButton).fill().row();
        contentTable.add(settingsButton).fill().row();
        contentTable.add(quitButton).fill().row();

        // Add content table to screen
        table.add(contentTable);

        table.top();
        table.setFillParent(true);
        table.pack();

        return table;
    }
}
