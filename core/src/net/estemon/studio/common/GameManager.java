package net.estemon.studio.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.config.DifficultyLevel;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    public static final String HIGH_SCORE_KEY = "highscore";
    public static final String DIFFICULTY_KEY = "difficulty";

    private final Preferences PREFS;

    private int highScore;
    private DifficultyLevel difficultyLevel;

    // SINGLETON, not instantiable
    private GameManager() {
        PREFS = Gdx.app.getPreferences(FlippyGame.class.getSimpleName());
        highScore = PREFS.getInteger(HIGH_SCORE_KEY, 0);

        String difficultyName = PREFS.getString(DIFFICULTY_KEY, DifficultyLevel.EASY.name());
        difficultyLevel = DifficultyLevel.valueOf(difficultyName);
    }

    /********* HIGH SCORE *********/
    public void updateHighScore(int score) {
        if (score < highScore) {
            return;
        }
        highScore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highScore);
        PREFS.flush();
    }

    public void resetHighScore() {
        highScore = 0;
        PREFS.putInteger(HIGH_SCORE_KEY, highScore);
        PREFS.flush();
    }

    public String getHighScoreString() { return String.valueOf(highScore); }

    /********* DIFFICULTY LEVEL *********/
    public void updateDifficulty(DifficultyLevel newDifficultyLevel) {
        if (difficultyLevel == newDifficultyLevel) {
            return;
        }

        difficultyLevel = newDifficultyLevel;
        PREFS.putString(DIFFICULTY_KEY, difficultyLevel.name());
        PREFS.flush();
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }
}
