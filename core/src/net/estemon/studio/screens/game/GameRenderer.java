package net.estemon.studio.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.assets.Animations;
import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.assets.RegionNames;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.entity.Background;
import net.estemon.studio.entity.Enemy;
import net.estemon.studio.entity.Player;
import net.estemon.studio.utils.GdxUtils;
import net.estemon.studio.utils.ViewportUtils;
import net.estemon.studio.utils.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private Viewport uiViewport;


    private DebugCameraController debugCameraController;
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private TextureRegion backgroundRegion;
    private float backgroundX;
    private float backgroundX2;
    private boolean drawStatic;

    private int previousY = 0;

    private ParticleEffect particleEffect1;
    private ParticleEffect particleEffect2;

    public static TextureRegion[] player;
    public static Animation playerAnim;


    public static TextureRegion[] enemy;
    public static Animation enemyAnim;
    public static float propellerAnimationTime = 0f;

    // Getter for UiRenderer
    public Viewport getUiViewport() { return uiViewport; }

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

        // UI view
        OrthographicCamera uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(GameConfig.UI_WIDTH, GameConfig.UI_HEIGHT, uiCamera);

        // Create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);

        backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        backgroundX = 0;
        backgroundX2 = 12;
        
        Animations.playerPlaneAnimation(assetManager);
        Animations.enemyPlaneAnimation(assetManager);

        particleEffect1 = new ParticleEffect();
        particleEffect1.load(Gdx.files.internal("particles/particle1.p"), gameplayAtlas);
        particleEffect1.scaleEffect(0.005f);
        particleEffect1.setPosition(0, 0);
        particleEffect1.start();

        particleEffect2 = new ParticleEffect();
        particleEffect2.load(Gdx.files.internal("particles/particle2.p"), gameplayAtlas);
        particleEffect2.scaleEffect(0.02f);
        particleEffect2.setPosition(0, 0);
        particleEffect2.start();
    }

    // Public methods
    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        if (Gdx.input.isTouched() && !controller.isGameOver()) {
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));


        }

        GdxUtils.clearScreen();

        // Render scroll background and gameplay
        updateBackground(delta);
        propellerAnimationTime += delta;
        renderGamePlay(delta);

        // Render debug graphics
        // renderDebug();
    }

    public void updateBackground(float delta) {
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
        uiViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // Private methods

    /************** GAMEPLAY ***************/
    private void renderGamePlay(float delta) {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawBackground(); // uncomment to draw bg textures
        drawPlayer(delta); // uncomment to draw player textures
        drawEnemies(); // uncomment to draw enemy planes textures

        int lives = controller.getLives();
        if (lives < GameConfig.PLAYER_START_LIVES) {
            particleEffect1.draw(batch, delta);
            if (lives <= 1) {
                particleEffect2.draw(batch, delta);
            }
        }

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
                    GameConfig.WORLD_HEIGHT
            );
        }
    }

    private void drawPlayer(float delta) {
        // Get plane rotation angle to draw it accordingly to its ySpeed
        float rotationAngle = controller.getRotationAngle();
        Player player = controller.getPlayer();
        TextureRegion currentFrame = (TextureRegion) playerAnim.getKeyFrame(propellerAnimationTime, true);
        batch.draw(currentFrame,
                player.getX(), player.getY(),
                player.getWidth() / 2, player.getHeight() / 2,
                player.getWidth(), player.getHeight(),
                GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE,
                rotationAngle
        );
        int lives = controller.getLives();
        if (lives < GameConfig.PLAYER_START_LIVES) {
            float particleX = player.getBounds().x;
            float particleY = player.getBounds().y;
            particleEffect1.setPosition(particleX, particleY);
            particleEffect1.update(delta);
            if (lives <= 1) {
                particleEffect2.setPosition(particleX + 0.2f, particleY);
                particleEffect2.update(delta);
            }
        }
    }


    private void drawEnemies() {
        for (Enemy enemy : controller.getEnemies()) {
            TextureRegion currentFrame = (TextureRegion) enemyAnim.getKeyFrame(propellerAnimationTime, true);
            batch.draw(currentFrame,
                    enemy.getX(), enemy.getY(),
                    enemy.getWidth() / 2, enemy.getHeight() / 2,
                    enemy.getWidth(), enemy.getHeight(),
                    GameConfig.ENEMY_SIZE, GameConfig.ENEMY_SIZE, 0);
        }
    }

    /************** DEBUG ***************/
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
        for (Enemy enemy : controller.getEnemies()) {
            enemy.drawDebug(renderer);
        }
    }
}
