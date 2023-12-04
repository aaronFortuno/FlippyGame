package net.estemon.studio.screens.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import net.estemon.studio.FlippyGame;
import net.estemon.studio.assets.AssetDescriptors;
import net.estemon.studio.assets.RegionNames;
import net.estemon.studio.common.GameManager;
import net.estemon.studio.config.DifficultyLevel;
import net.estemon.studio.screens.common.BaseScreen;


public class SettingsScreen extends BaseScreen {

    private AssetManager assetManager;

    private ButtonGroup<CheckBox> diffCheckBoxGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public SettingsScreen(FlippyGame game) {
        super(game);
        assetManager = game.getAssetManager();
    }

    @Override
    protected Actor createUi() {
        // Setup screen table
        Table table = new Table();
        table.defaults().pad(20);
        // table.debugAll();

        // Setup background
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Setup content table
        Table contentTable = new Table(skin);
        contentTable.defaults().pad(10);
        // contentTable.debugAll();
        contentTable.center();

        // Title label
        TypingLabel titleLabel = new TypingLabel("settings", skin, "title-font");

        Label label = new Label("DIFFICULTY", skin);

        // Difficulty checkboxes
        easy = checkBox(DifficultyLevel.EASY.name(), skin);
        medium = checkBox(DifficultyLevel.MEDIUM.name(), skin);
        hard = checkBox(DifficultyLevel.HARD.name(), skin);

        diffCheckBoxGroup = new ButtonGroup<>(easy, medium, hard);
        final DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        diffCheckBoxGroup.setChecked(difficultyLevel.name());
        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };

        easy.addListener(listener);
        medium.addListener(listener);
        hard.addListener(listener);

        // Show buttons checkbox
        final CheckBox showButtons = checkBox("UI buttons", skin);
        showButtons.setChecked(GameManager.INSTANCE.getShowButtons());

        showButtons.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.INSTANCE.updateShowButtons(showButtons.isChecked());
            }
        });

        // Back button
        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SplashScreen(game));
            }
        });

        // Add content to content table
        contentTable.add(titleLabel).padBottom(30f).colspan(3).row();
        contentTable.add(easy);
        contentTable.add(medium);
        contentTable.add(hard).row();
        contentTable.add();
        contentTable.add(showButtons).row();
        contentTable.add();
        contentTable.add(backButton);

        // Add content table to screen
        table.add(contentTable);

        table.top();
        table.setFillParent(true);
        table.pack();
        return table;
    }

    private void difficultyChanged() {
        CheckBox checked = diffCheckBoxGroup.getChecked();

        if (checked == easy) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
        } else if (checked == medium) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
        } else if (checked == hard) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
        }
    }

    private static CheckBox checkBox(String text, Skin skin) {
        CheckBox checkBox = new CheckBox(text, skin);
        checkBox.left().padLeft(4).padTop(4).padBottom(8);
        checkBox.getLabelCell().padLeft(8);
        return checkBox;
    }

}
