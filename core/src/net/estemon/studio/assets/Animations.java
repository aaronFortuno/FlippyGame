package net.estemon.studio.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.estemon.studio.screens.game.GameRenderer;

public class Animations {

    public static void planeAnimation(AssetManager assetManager) {
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        GameRenderer.player = new TextureRegion[4];

        for (int i = 0; i < GameRenderer.player.length; i++) {
            String regionName = "planeGreen" + (i == 3 ? 2 : i + 1);
            GameRenderer.player[i] = gameplayAtlas.findRegion(regionName);
        }

        /*GameRenderer.player[0] = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS).findRegion("planeGreen1");
        GameRenderer.player[1] = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS).findRegion("planeGreen2");
        GameRenderer.player[2] = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS).findRegion("planeGreen3");
        GameRenderer.player[3] = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS).findRegion("planeGreen2");
*/
        GameRenderer.playerAnim = new Animation(0.03f, GameRenderer.player);
        GameRenderer.playerAnim.setPlayMode(Animation.PlayMode.LOOP);
    }
}
