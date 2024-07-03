package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.controller.ScreenManager;
import com.mygdx.game.model.network.Client;

import java.io.IOException;

public class Gwent extends Game {
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
		ScreenManager.setLoginScreen();
        try {
            new Client().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {}
	public SpriteBatch getBatch() {
		return batch;
	}
	public Skin getSkin() {
		return skin;
	}
}
