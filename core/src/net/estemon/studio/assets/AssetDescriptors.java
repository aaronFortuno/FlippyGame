package net.estemon.studio.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {
    private AssetDescriptors() {} // not instantiable

    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS =
            new AssetDescriptor<>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);
}
