package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher2 {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(120);
		config.setResizable(false);
// 		full screen mode : (its recommended to use windows/mac fullscreen mode instead of setting it here)
//		{
//			config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
//		}

		config.setTitle("Gwent");
		config.setWindowedMode(Gwent.WIDTH, Gwent.HEIGHT);
		new Lwjgl3Application(new Gwent(), config);
	}
}
