package net.estemon.studio.assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.estemon.studio.screens.game.GameRenderer;

import jdk.tools.jlink.internal.plugins.StripNativeCommandsPlugin;

public class Animations {

    private static TextureAtlas gameplayAtlas;

    public static void playerPlaneAnimation(AssetManager assetManager) {
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        GameRenderer.player = new TextureRegion[4];

        for (int i = 0; i < GameRenderer.player.length; i++) {
            String regionName = "planeGreen" + (i == 3 ? 2 : i + 1);
            GameRenderer.player[i] = gameplayAtlas.findRegion(regionName);
        }

        GameRenderer.playerAnim = new Animation<>(0.03f, GameRenderer.player);
        GameRenderer.playerAnim.setPlayMode(Animation.PlayMode.LOOP);
    }

    public static void enemyPlaneAnimation(AssetManager assetManager) {
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        GameRenderer.enemy = new TextureRegion[4];

        for (int i = 0; i < GameRenderer.enemy.length; i++) {
            String regionName = "planeRed" + (i == 3 ? 2 : i + 1);
            GameRenderer.enemy[i] = gameplayAtlas.findRegion(regionName);
        }

        GameRenderer.enemyAnim = new Animation<>(0.03f, GameRenderer.enemy);
        GameRenderer.enemyAnim.setPlayMode(Animation.PlayMode.LOOP);
    }

    public static void bonusAnimation(AssetManager assetManager) {
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        GameRenderer.bonus = new TextureRegion[8];

        for (int i = 0; i < GameRenderer.bonus.length; i++) {
            String regionName = "coin" + (i + 1);
            GameRenderer.bonus[i] = gameplayAtlas.findRegion(regionName);
        }
        GameRenderer.bonusAnim = new Animation<>(0.03f, GameRenderer.bonus);
        GameRenderer.bonusAnim.setPlayMode(Animation.PlayMode.LOOP);
    }
}
