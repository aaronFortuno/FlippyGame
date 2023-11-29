package net.estemon.studio.entity;

import net.estemon.studio.screens.game.Scrollable;

public class Background extends Scrollable {

    private float x;
    private float y;

    private float width;
    private float height;

    // Static constructor
    public Background() {
        super();
    }

    // Scrollable constructor
    public Background(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
