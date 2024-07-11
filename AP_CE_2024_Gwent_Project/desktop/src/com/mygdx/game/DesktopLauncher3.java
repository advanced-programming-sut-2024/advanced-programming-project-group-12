package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.Server;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher3 {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(120);
        config.setResizable(false);


        config.setTitle("Gwent");
        config.setWindowedMode(Gwent.WIDTH, Gwent.HEIGHT);
        new Lwjgl3Application(new Gwent(), config);
    }
}
