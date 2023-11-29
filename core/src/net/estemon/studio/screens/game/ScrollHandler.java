package net.estemon.studio.screens.game;

import com.badlogic.gdx.scenes.scene2d.Group;

import net.estemon.studio.entity.Background;

public class ScrollHandler extends Group {

    Background bg, bg_back;

    public ScrollHandler() {

        // Create two backgrounds and scroll them infinitely
        bg = new Background(

        );

        bg_back = new Background(

        );
    }
}
