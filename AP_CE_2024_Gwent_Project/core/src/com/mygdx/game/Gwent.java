package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.view.Screens;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.view.screen.LoginMenuScreen;
import com.mygdx.game.view.screen.PreTournamentScreen;

public class Gwent extends Game implements ApplicationListener {
	private Screens typeScreen;
	private Screen currentScreen;
	private boolean pendingScreenChange;
	public static Gwent singleton;
	SpriteBatch batch;
	public Skin skin;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 980;
	
	@Override
	public void create () {
		singleton = this;
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
		batch = new SpriteBatch();
		currentScreen = new LoginMenuScreen();
		Gwent.singleton.setScreen(currentScreen);
        new Client().start();
    }

	@Override
	public void render () {
		super.render();
		if (pendingScreenChange) {
			currentScreen.dispose(); // Dispose of current screen
			currentScreen = typeScreen.createScreen();
			Gwent.singleton.setScreen(currentScreen);
			pendingScreenChange = false;
			synchronized (Client.getInstance()) {
				Client.getInstance().notify();
			}
		}
	}
	
	@Override
	public void dispose () {}
	public SpriteBatch getBatch() {
		return batch;
	}
	public Skin getSkin() {
		return skin;
	}
	public void changeScreen(Screens screens) {
		typeScreen = screens;
		pendingScreenChange = true;
	}
	public Screen getCurrentScreen() {
		return currentScreen;
	}
}
