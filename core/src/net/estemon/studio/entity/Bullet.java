package net.estemon.studio.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pool;

import net.estemon.studio.config.GameConfig;

public class Bullet extends GameObjectBase implements Pool.Poolable {

    // TODO PART 2
    private float xSpeed = GameConfig.BULLET_INIT_SPEED;
    private float ySpeed;

    private float angle;

    private float timer;

    public Bullet() {
        super(GameConfig.BULLET_RADIUS);
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void update(float delta) {
        setX(getX() + xSpeed);
        ySpeed += GameConfig.GRAVITY * delta;
        setY(getY() + ySpeed);

        angle = (float) Math.toDegrees(Math.atan2(ySpeed, xSpeed));

        timer += delta;
    }

    public void drawDebug(ShapeRenderer renderer) {
        super.drawDebug(renderer, 0f);
        Color oldColor = renderer.getColor();
        renderer.setColor(Color.RED);
        renderer.x(getBounds().x, getBounds().y, 0.1f);
        renderer.setColor(oldColor);
    }

    @Override
    public void reset() {
        timer = 0f;
        ySpeed = 0f;
    }
}
