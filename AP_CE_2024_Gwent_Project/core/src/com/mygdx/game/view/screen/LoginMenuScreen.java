package com.mygdx.game.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Gwent;

public class LoginMenuScreen implements Screen {
    private TextButton loginButton;
    private TextButton donNotHaveAnAccountButton;
    public LoginMenuScreen() {
        loginButton = new TextButton("Login", Gwent.singleton.getSkin());
        donNotHaveAnAccountButton = new TextButton("Don't have an account?", Gwent.singleton.getSkin());

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
