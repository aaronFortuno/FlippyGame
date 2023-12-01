package net.estemon.studio;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = false;
    private static final String RAW_ASSETS_PATH = "assets/new"; // origin
    private static final String ASSETS_PATH = "assets"; // destination

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
        settings.pot = false;
        settings.maxWidth = 1024;
        settings.maxHeight = 732;

        TexturePacker.process(
                settings,
                RAW_ASSETS_PATH,
                ASSETS_PATH,
                "gameplay" // atlas name
        );
    }
}
