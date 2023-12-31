package net.estemon.studio.common;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import net.estemon.studio.assets.AssetDescriptors;

public class GameMusic {

    private final Music music;
    private float volume = 1f;

    public GameMusic(AssetManager assetManager) {
        this.music = assetManager.get(AssetDescriptors.GAME_MUSIC);
        this.music.setLooping(true);
        this.music.setVolume(volume);
        play();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (music != null) {
            music.setVolume(volume);
        }
    }

    public float getVolume() { return volume; }

    public void play() {
        if (music != null) {
            music.play();
        }
    }
}
