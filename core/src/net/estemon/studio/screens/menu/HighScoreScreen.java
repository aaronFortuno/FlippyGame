package net.estemon.studio.screens.menu;

import com.badlogic.gdx.assets.AssetManager;
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
import net.estemon.studio.common.GameManager;
import net.estemon.studio.screens.common.BaseScreen;

public class HighScoreScreen extends BaseScreen {

    private final AssetManager assetManager;

    public HighScoreScreen(FlippyGame game) {
        super(game);
        assetManager = game.getAssetManager();
    }

    @Override
    protected Actor createUi() {
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
        TypingLabel titleLabel = new TypingLabel("high score", skin, "title-font");

        // High score label
        String highScoreString = GameManager.INSTANCE.getHighScoreString();
        final TypingLabel highScoreLabel = new TypingLabel("{SLIDE}{HANG=1;0.5}" + highScoreString, skin, "title-font");
        highScoreLabel.setFontScale(0.5f);

        // Reset button
        TextButton resetButton = new TextButton("RESET", skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.INSTANCE.resetHighScore();
                highScoreLabel.setText("{SLIDE}{HANG=1;0.5}" + GameManager.INSTANCE.getHighScoreString());
            }
        });

        // Back button
        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SplashScreen(game));
            }
        });

        // Add content to content table
        contentTable.add(titleLabel).padBottom(30f).row();
        contentTable.add(highScoreLabel).row();
        contentTable.add(resetButton).row();
        contentTable.add(backButton);

        // Add content table to screen
        table.add(contentTable);

        table.top();
        table.setFillParent(true);
        table.pack();
        return table;
    }
}
