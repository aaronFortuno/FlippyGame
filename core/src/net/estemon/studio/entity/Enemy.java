package net.estemon.studio.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

import net.estemon.studio.common.GameManager;
import net.estemon.studio.config.DifficultyLevel;
import net.estemon.studio.config.GameConfig;

public class Enemy extends PlaneBase implements Pool.Poolable {

    private DifficultyLevel difficultyLevel;

    private float xSpeed;
    private float ySpeed;
    private boolean hit;
    private float timer = 0f;
    private boolean goUp;
    private boolean goDown;


    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public boolean isGoUp() {
        return goUp;
    }

    public void setGoUp() {
        goUp = true;
        goDown = false;
    }

    public boolean isGoDown() {
        return goDown;
    }

    public void setGoDown() {
        goDown = true;
        goUp = false;
    }

    public Enemy() {
        super(GameConfig.PLANE_BOUNDS_RADIUS);
        setSize(GameConfig.ENEMY_SIZE, GameConfig.ENEMY_SIZE);
        difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
    }

    public void update(float delta) {
        setX(getX() - difficultyLevel.getxSpeed());
        setY(getY() + ySpeed);
        timer += delta;
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
        timer = 0f;
        goUp = false;
        goDown = false;
        ySpeed = 0;
    }

    public void goUp(float delta) {
        ySpeed += delta * difficultyLevel.getyAcceleration();

    }

    public void goDown(float delta) {
        ySpeed -= delta * difficultyLevel.getyAcceleration();
    }

    public void goStraight(float delta) {
        // Normalising y speed
        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        float maxYSpeed = difficultyLevel.getMaxYSpeed();
        if (ySpeed > 0) {
            if (ySpeed > maxYSpeed) {
                ySpeed = maxYSpeed;
            } else {
                ySpeed -= delta * difficultyLevel.getyAcceleration();
                ySpeed = Math.max(ySpeed, 0);
            }
        } else if (ySpeed < 0) {
            if (ySpeed < -maxYSpeed) {
                ySpeed = -maxYSpeed;
            } else {
                ySpeed += delta * difficultyLevel.getyAcceleration();
                ySpeed = Math.min(ySpeed, 0);
            }
        }
    }
}
