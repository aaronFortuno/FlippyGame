package net.estemon.studio.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import net.estemon.studio.config.GameConfig;

public class Enemy extends PlaneBase {

    private float xSpeed = GameConfig.ENEMY_EASY_SPEED;

    public Enemy() {
        super(GameConfig.PLANE_BOUNDS_RADIUS);
        setSize(GameConfig.ENEMY_MIN_SIZE, GameConfig.ENEMY_MIN_SIZE);
    }

    public void update() {
        setX(getX() - xSpeed);
    }

    public void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void drawDebug(ShapeRenderer renderer) {
        super.drawDebug(renderer, -180);
        Color oldColor = renderer.getColor();
        renderer.setColor(Color.BLUE);
        renderer.x(getBounds().x, getBounds().y, 0.1f);
        renderer.setColor(oldColor);
    }
}
