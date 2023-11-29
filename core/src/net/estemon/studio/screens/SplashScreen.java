package net.estemon.studio.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetPaths;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.screens.game.GameScreen;

public class SplashScreen extends BaseScreen {

    public SplashScreen(FlippyGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();
        play();
        return table;
        // TODO implement splash screen
        // return null;
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }
}
