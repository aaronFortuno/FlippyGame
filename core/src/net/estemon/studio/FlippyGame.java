package net.estemon.studio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.estemon.studio.screens.LoadingScreen;

public class FlippyGame extends Game {

	private AssetManager assetManager;
	private SpriteBatch batch;

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
	@Override
	public void create () {
		assetManager = new AssetManager();
		batch = new SpriteBatch();

		setScreen(new LoadingScreen(this));
	}
	
	@Override
	public void dispose () {

	}
}
