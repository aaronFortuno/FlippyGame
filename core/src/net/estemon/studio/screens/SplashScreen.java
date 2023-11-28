package net.estemon.studio.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;

import net.estemon.studio.FlippyGame;

public class SplashScreen extends ScreenAdapter {

    private final FlippyGame game;
    private final AssetManager assetManager;

    public SplashScreen(FlippyGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }


}
