package net.estemon.studio.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.assets.RegionNames;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.entity.Background;
import net.estemon.studio.entity.Player;
import net.estemon.studio.utils.GdxUtils;
import net.estemon.studio.utils.ViewportUtils;
import net.estemon.studio.utils.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera uiCamera;
    private Viewport uiViewport;
    private BitmapFont font;

    private final GlyphLayout layout = new GlyphLayout();

    private DebugCameraController debugCameraController;
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private TextureRegion backgroundRegion;
    private float backgroundX;
    private float backgroundX2;
    private boolean drawStatic;

    private TextureRegion playerRegion;

    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController controller) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.controller = controller;
        init();
    }

    public void init() {
        // Game view
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        // TODO ui view

        // Create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);

        backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        backgroundX = 0;
        backgroundX2 = 12;
        
        playerRegion = gameplayAtlas.findRegion(RegionNames.PLAYER);
    }

    // Public methods
    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        GdxUtils.clearScreen();

        // TODO Finish render ui (enemies)
        updateBackground(delta);
        renderGamePlay();

        // TODO Render ui

        // Render debug graphics
        renderDebug();
    }

    private void updateBackground(float delta) {
        backgroundX -= delta * GameConfig.BACKGROUND_SPEED;
        backgroundX2 -= delta * GameConfig.BACKGROUND_SPEED;

        if (backgroundX + GameConfig.WORLD_WIDTH <= 0) {
            backgroundX = GameConfig.WORLD_WIDTH;
        }

        if (backgroundX2 + GameConfig.WORLD_WIDTH <= 0) {
            backgroundX2 = backgroundX + GameConfig.WORLD_WIDTH;
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        // TODO resize ui
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // Private methods
    private void renderGamePlay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawBackground(); // uncomment to draw bg textures
        drawPlayer(); // uncomment to draw plane textures
        drawEnemies();

        batch.end();
    }

    private void drawBackground() {
        if (drawStatic) {
            Background background = controller.getBackground();
            batch.draw(backgroundRegion,
                    GameConfig.WORLD_CENTER_X - background.getWidth() / 2,
                    background.getY(),
                    background.getWidth(), background.getHeight()
            );
        } else {
            batch.draw(backgroundRegion,
                    backgroundX,
                    0,
                    GameConfig.WORLD_WIDTH + 0.1f, // overlap backgrounds to avoid black synchro lines
                    GameConfig.WORLD_HEIGHT
            );
            batch.draw(backgroundRegion,
                    backgroundX2,
                    0,
                    GameConfig.WORLD_WIDTH + 0.1f, // overlap backgrounds to avoid black synchro lines
                    GameConfig.WORLD_HEIGHT)
            ;
        }
    }

    private void drawPlayer() {
        // get plane rotation angle to draw it accordingly to its ySpeed
        float rotationAngle = controller.getRotationAngle();
        Player player = controller.getPlayer();
        batch.draw(playerRegion,
                player.getX(), player.getY(),
                player.getWidth() / 2, player.getHeight() / 2,
                player.getWidth(), player.getHeight(),
                GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE,
                rotationAngle
        );
    }

    private void drawEnemies() {
        // TODO render enemies
    }

    private void renderDebug() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug() {
        float rotationAngle = controller.getRotationAngle();
        controller.getPlayer().drawDebug(renderer, rotationAngle);
    }
}