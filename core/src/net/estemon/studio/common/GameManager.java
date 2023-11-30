package net.estemon.studio.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import net.estemon.studio.FlippyGame;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    public static final String HIGH_SCORE_KEY = "highscore";

    private final Preferences PREFS;

    private int highScore;

    // SINGLETON, not instantiable
    private GameManager() {
        PREFS = Gdx.app.getPreferences(FlippyGame.class.getSimpleName());
        highScore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
        // TODO difficulty levels
    }

    public void updateHighScore(int score) {
        if (score < highScore) {
            return;
        }
        highScore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highScore);
        PREFS.flush();
    }

    public String getHighScoreString() { return String.valueOf(highScore); }
}
