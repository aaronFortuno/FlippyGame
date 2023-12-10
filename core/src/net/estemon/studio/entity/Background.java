package net.estemon.studio.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.screens.game.Scrollable;

public class Background extends Scrollable {

    private float x;
    private float y;

    private float width;
    private float height;

    private Stage stage;
    private TextureRegion background;

    // Static constructor
    public Background() {
        super();
    }

    // Scrollable constructor
    public Background(float x, float y, float width, float height, float velocity, AssetManager assetManager) {
        super(x, y, width, height, velocity);
        stage = getStage();
        background = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS).findRegion("background");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.disableBlending();
        batch.draw(background, position.x, position.y, width, height);
        batch.enableBlending();
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
