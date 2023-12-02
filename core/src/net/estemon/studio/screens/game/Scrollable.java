package net.estemon.studio.screens.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Scrollable extends Actor {

    protected Vector2 position;
    protected float velocity;
    protected float width;
    protected float height;
    protected boolean leftOfScreen;

    protected AssetManager assetManager;

    // Static
    public Scrollable() {}

    // Scrollable
    public Scrollable(float x, float y, float width, float height, float velocity) {
        position = new Vector2(x, y);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        leftOfScreen = false;
    }

    public void act(float delta) {
        super.act(delta);

        // Move object through x axis
        position.x += velocity * delta;

        // If it's out of screen set to true
        if (position.x + width < 0) {
            leftOfScreen = true;
        }
    }
}
