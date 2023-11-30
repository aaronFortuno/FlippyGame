package net.estemon.studio.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {
    private AssetDescriptors() {} // not instantiable

    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS =
            new AssetDescriptor<>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);
    public static final AssetDescriptor<Music> GAME_MUSIC =
            new AssetDescriptor<>(AssetPaths.GAME_MUSIC, Music.class);
    public static final AssetDescriptor<Sound> SOUND_PROPELLER =
            new AssetDescriptor<>(AssetPaths.SOUND_PROPELLER, Sound.class);

}
