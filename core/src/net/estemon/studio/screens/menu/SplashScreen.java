package net.estemon.studio.screens.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.assets.RegionNames;
import net.estemon.studio.screens.common.BaseScreen;
import net.estemon.studio.screens.game.GameScreen;

public class SplashScreen extends BaseScreen {

    private final AssetManager assetManager;


    public SplashScreen(FlippyGame game) {
        super(game);
        assetManager = game.getAssetManager();
    }

    @Override
    protected Actor createUi() {

        // Setup screen table
        Table table = new Table();
        table.debugAll();

        // Setup background
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Title label
        Label titleLabel = new Label("FLIPPY", skin, "outline-font");

        // Setup content table
        Table contentTable = new Table(skin);
        contentTable.defaults().pad(20);
        contentTable.debugAll();

        contentTable.add(titleLabel);

        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        //play();
        return table;
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }
}
