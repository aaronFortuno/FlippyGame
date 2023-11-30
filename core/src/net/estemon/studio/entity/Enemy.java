package net.estemon.studio.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

import net.estemon.studio.config.GameConfig;

public class Enemy extends PlaneBase implements Pool.Poolable {

    private float xSpeed = GameConfig.ENEMY_EASY_SPEED;
    private boolean hit;

    public Enemy() {
        super(GameConfig.PLANE_BOUNDS_RADIUS);
        setSize(GameConfig.ENEMY_SIZE, GameConfig.ENEMY_SIZE);
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

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();
        // Check if player overlaps enemy bounds
        boolean overlaps = Intersector.overlaps(playerBounds, getBounds());
        hit = overlaps;
        return overlaps;
    }

    public boolean isNotHit() {
        return !hit;
    }

    @Override
    public void reset() {
        // Reset enemy hit to reuse at pooling
        hit = false;
    }
}
