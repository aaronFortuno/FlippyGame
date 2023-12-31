package net.estemon.studio.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

import net.estemon.studio.common.GameManager;
import net.estemon.studio.config.DifficultyLevel;
import net.estemon.studio.config.GameConfig;

public class Bonus extends GameObjectBase implements Pool.Poolable {

    // TODO PART 3
    private float xSpeed;

    private boolean hit;
    private float timer = 0f;
    private int value;
    private BonusKind kind;
    private boolean collided;
    private float scaleFactor = 1f;
    private float animationTimer = 0f;

    public Bonus() {
        super(GameConfig.BONUS_BOUNDS);
        setSize(GameConfig.BONUS_SIZE, GameConfig.BONUS_SIZE);
        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
    }

    public void setKind(BonusKind kind) {
        this.kind = kind;
        this.value = kind.getValue(); // Get bonus value
    }

    public BonusKind getKind() {
        return kind;
    }

    public int getValue() {
        return value;
    }

    public void update () {
        // Bonuses travel faster than enemies
        setX(getX() - getxSpeed() * 1.2f);
    }

    public void setXSpeed(float xSpeed) {
        float extraSpeed;
        if (kind == BonusKind.GOLD) {
            extraSpeed = 2.5f;
        } else if (kind == BonusKind.SILVER) {
            extraSpeed = 1.7f;
        } else {
            extraSpeed = 1f;
        }
        this.xSpeed = xSpeed * extraSpeed;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void drawDebug(ShapeRenderer renderer) {
        super.drawDebug(renderer, 0f);
        Color oldColor = renderer.getColor();
        if (kind == BonusKind.GOLD) {
            renderer.setColor(Color.GOLD);
        } else if (kind == BonusKind.SILVER) {
            renderer.setColor(Color.LIGHT_GRAY);
        } else {
            renderer.setColor(Color.SALMON);
        }
        renderer.x(getBounds().x, getBounds().y, 0.15f);
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
        // Reset bonus to reuse at pooling
        hit = false;
        timer = 0f;
        collided = false;
        scaleFactor = 1f;
        animationTimer = 0f;
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public float getAnimationTimer() {
        return animationTimer;
    }

    public void setAnimationTimer(float animationTimer) {
        this.animationTimer = animationTimer;
    }
}
