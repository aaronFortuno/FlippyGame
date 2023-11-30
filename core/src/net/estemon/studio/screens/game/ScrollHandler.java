package net.estemon.studio.screens.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Group;

import net.estemon.studio.config.GameConfig;
import net.estemon.studio.entity.Background;

public class ScrollHandler extends Group {

    private AssetManager assetManager;
    Background bg, bg_back;

    public ScrollHandler(AssetManager assetManager) {

        // Create two backgrounds and scroll them infinitely
        bg = new Background(0, 0,
                GameConfig.WORLD_WIDTH * 2, GameConfig.WORLD_HEIGHT,
                GameConfig.BACKGROUND_SPEED,
                assetManager
        );

        bg_back = new Background(

        );

        System.out.println("[ScrollHandler]");
        addActor(bg);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
