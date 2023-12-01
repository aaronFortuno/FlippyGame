package net.estemon.studio.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.estemon.studio.FlippyGame;
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
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }
}
