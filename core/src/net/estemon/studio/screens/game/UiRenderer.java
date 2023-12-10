package net.estemon.studio.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.assets.RegionNames;
import net.estemon.studio.common.GameManager;
import net.estemon.studio.config.GameConfig;
import net.estemon.studio.screens.menu.SplashScreen;

public class UiRenderer extends ScreenAdapter {

    private final FlippyGame game;
    private final AssetManager assetManager;
    private final GameController controller;
    protected Stage stage;
    private final Skin skin;
    private Label scoreLabel;

    private TextureRegion livesTexture;
    private Image[] livesImages;

    private Image pauseButtonImage;

    private Image upButtonImage;

    private Image downButtonImage;

    private Image shootButtonImage;

    private boolean shownEnd;

    public UiRenderer(FlippyGame game, AssetManager assetManager, Viewport uiViewport, GameController controller) {
        this.game = game;
        this.assetManager = assetManager;
        this.stage = new Stage(uiViewport);
        this.skin = assetManager.get(AssetDescriptors.UI_SKIN);
        this.controller = controller;
        createUi();
    }

    private void createUi() {
        Gdx.input.setInputProcessor(stage);
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        livesTexture = gameplayAtlas.findRegion(RegionNames.PLAYER);

        // Screen table
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label("SCORE: ", skin);

        Table livesTable = new Table();
        int maxLives = GameConfig.PLAYER_START_LIVES;
        livesImages = new Image[maxLives];
        for (int i = 0; i < maxLives; i++)
        {
            livesImages[i] = new Image(livesTexture);
            livesTable.add(livesImages[i]).size(30).pad(5).right();
        }

        // Pause button
        TextureRegion pauseButtonTexture = gameplayAtlas.findRegion(RegionNames.PAUSE_BUTTON);
        pauseButtonImage = new Image(pauseButtonTexture);
        pauseButtonImage.setColor(1, 1, 1, 0.5f);

        pauseButtonImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setPaused(!controller.isPaused());
            }
        });

        // UI Buttons
        boolean showUiButtons = GameManager.INSTANCE.getShowButtons();
        if (showUiButtons) {
            TextureRegion upButtonTexture = gameplayAtlas.findRegion(RegionNames.UP_BUTTON);
            TextureRegion downButtonTexture = gameplayAtlas.findRegion(RegionNames.DOWN_BUTTON);

            upButtonImage = new Image(upButtonTexture);
            upButtonImage.setColor(1, 1, 1, 0.5f);
            downButtonImage = new Image(downButtonTexture);
            downButtonImage.setColor(1, 1, 1, 0.5f);

            upButtonImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {  }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    controller.setShouldGoUp(true);
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    controller.setShouldGoStraight(true);
                }
            });

            downButtonImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {  }
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    controller.setShouldGoDown(true);
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    controller.setShouldGoStraight(true);
                }
            });

            TextureRegion shootButtonTexture = gameplayAtlas.findRegion(RegionNames.SHOOT_BUTTON);
            shootButtonImage = new Image(shootButtonTexture);

            shootButtonImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.createNewBullet();
                }
            });
        }

        // Buttons table
        Table buttonsTable = new Table();
        // buttonsTable.debugAll();
        buttonsTable.add().expandY();
        buttonsTable.add();
        buttonsTable.add(pauseButtonImage).expandY().size(80, 80).right().top().row();
        buttonsTable.bottom();
        buttonsTable.add(upButtonImage).left();
        buttonsTable.add(shootButtonImage).left().pad(20).expand();
        buttonsTable.add(downButtonImage).right().expand();

        // Adding to screen table
        table.add(scoreLabel).pad(20).padLeft(150).expandX().left();
        table.add(livesTable).pad(20).row();
        table.add(buttonsTable).pad(20).colspan(2).expand().bottom().fillX().fillY();
        // table.debugAll();

        // Add to stage
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(1));
        stage.addActor(table);
    }
    @Override
    public void render(float delta) {
        String scoreText = "SCORE: " + controller.getDisplayScore();
        if (!controller.isPaused()) {
            scoreLabel.setText(scoreText);
        } else {
            scoreLabel.setText("");
        }

        updateLives(controller.getLives());
        updatePause();

        if (controller.isGameOver()) {
            showFinalScore();
        }

        stage.act();
        stage.draw();
    }

    private void updatePause() {
        pauseButtonImage.setVisible(!controller.isPaused());
    }

    private void updateLives(int lives) {
        if (!controller.isPaused()) {
            for (int i = 0; i < livesImages.length; i++) {
                livesImages[i].setColor(1, 1, 1, 1);
                livesImages[i].setSize(
                        livesTexture.getRegionWidth() / 2.5f,
                        livesTexture.getRegionHeight() / 2.5f
                );
                if (i + 1 > lives) {
                    livesImages[i].setColor(1, 1, 1, 0.4f);
                }
            }
        } else {
            for (Image image : livesImages) {
                image.setColor(1, 1, 1, 0.1f);
            }
        }
    }

    private void showFinalScore() {
        if (!shownEnd) {
            Table table = new Table();
            table.setFillParent(true);
            // table.debugAll();
            table.defaults().center();

            int score = controller.getScore();

            TypingLabel scoreLabel = new TypingLabel("{SLIDE}{HANG=1;0.5}" + score + " points!", skin, "title-font");

            String message;
            switch (controller.checkFinalScore()) {
                case 0:
                    message = "that was... it's hard to play it worse!";
                    break;
                case 1:
                    message = "that's great! well done!";
                    break;
                case 2:
                    message = "wow! unbelievable! hard to play better!";
                    break;
                default:
                    message = "";
            }

            Label messageLabel = new Label(message, skin);

            TextButton menuScreenButton = new TextButton("MENU", skin);
            menuScreenButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new SplashScreen(game));
                }
            });

            table.add(scoreLabel).row();
            table.add(messageLabel).row();
            table.add(menuScreenButton).pad(10);

            stage.addActor(table);

            shownEnd = true;

            // Hide other screen elements, if there are in the UI
            if (upButtonImage != null) {
                pauseButtonImage.setVisible(false);
                upButtonImage.setVisible(false);
                downButtonImage.setVisible(false);
                shootButtonImage.setVisible(false);
            }
        }
    }

    @Override
    public void dispose() { stage.dispose(); }

}
