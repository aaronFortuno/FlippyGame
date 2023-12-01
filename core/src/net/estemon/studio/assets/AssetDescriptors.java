package net.estemon.studio.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {
    private AssetDescriptors() {} // not instantiable

    public static final AssetDescriptor<BitmapFont> UI_FONT_REGULAR =
            new AssetDescriptor<>(AssetPaths.UI_FONT_REGULAR, BitmapFont.class);
    public static final AssetDescriptor<BitmapFont> UI_FONT_OUTLINE =
            new AssetDescriptor<>(AssetPaths.UI_FONT_OUTLINE, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS =
            new AssetDescriptor<>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);
    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<>(AssetPaths.UI_SKIN, Skin.class);
    public static final AssetDescriptor<Music> GAME_MUSIC =
            new AssetDescriptor<>(AssetPaths.GAME_MUSIC, Music.class);
    public static final AssetDescriptor<Sound> SOUND_PROPELLER =
            new AssetDescriptor<>(AssetPaths.SOUND_PROPELLER, Sound.class);
}
