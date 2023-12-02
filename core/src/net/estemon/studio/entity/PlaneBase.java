package net.estemon.studio.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import net.estemon.studio.config.GameConfig;

public abstract class PlaneBase {

    private float x;
    private float y;

    private float width = GameConfig.PLANE_SIZE;
    private float height = GameConfig.PLANE_SIZE;

    private final Circle bounds;

    public PlaneBase(float boundsRadius) {
        bounds = new Circle(x, y, boundsRadius);
    }

    public void drawDebug(ShapeRenderer renderer, float rotationAngle) {
        Color oldColor = renderer.getColor();
        renderer.setColor(Color.YELLOW);
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30);
        float radiusLine = bounds.radius;
        double radiusLineX = bounds.x + radiusLine * Math.cos(Math.toRadians(rotationAngle));
        double radiusLineY = bounds.y + radiusLine * Math.sin(Math.toRadians(rotationAngle));
        renderer.line(
                getBounds().x, getBounds().y,
                (float) radiusLineX, (float) radiusLineY
        );
        renderer.setColor(oldColor);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public float getX() { return x; }
    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public float getY() { return y; }
    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void updateBounds() {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        bounds.setPosition(x + halfWidth, y + halfHeight);
    }

    public Circle getBounds() { return bounds; }
}
