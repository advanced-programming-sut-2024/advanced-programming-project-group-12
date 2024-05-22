package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

public class GwentMain extends ApplicationAdapter {
	SpriteBatch batch;
	public Skin skin;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
    }

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
