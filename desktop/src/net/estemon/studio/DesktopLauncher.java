package net.estemon.studio;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import net.estemon.studio.config.GameConfig;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Flippy");
		config.setWindowedMode((int) GameConfig.GAME_WIDTH, (int) GameConfig.GAME_HEIGHT);
		new Lwjgl3Application(new FlippyGame(), config);
	}
}
