package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.LoginMenuController;
import com.mygdx.game.controller.ScreenManager;

public class LoginMenuScreen implements Screen {
    //TODO : complete login menu after register menu is done
    private  TextButton loginButton;
    private TextButton forgotPasswordButton;
    private TextButton donNotHaveAnAccountButton;
    private Stage stage;
    public LoginMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createButtons();
    }
    @Override
    public void show() {
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO : implement login
            }
        });
        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO : implement forgot password
            }
        });
        donNotHaveAnAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                LoginMenuController.goToRegisterMenu();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.5f, 0.75f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        Gwent.singleton.getBatch().begin();
        Gwent.singleton.getBatch().end();
        stage.act(delta);
        stage.draw();

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
        stage.dispose();
    }
    public void createButtons() {
        loginButton = new TextButton("Login", Gwent.singleton.getSkin());
        forgotPasswordButton = new TextButton("Forgot Password", Gwent.singleton.getSkin());
        donNotHaveAnAccountButton = new TextButton("Don't have an account?", Gwent.singleton.getSkin());
        loginButton.setPosition((float) Gwent.WIDTH / 2 - loginButton.getWidth() / 2, (float) Gwent.HEIGHT / 2);
        forgotPasswordButton.setPosition((float) Gwent.WIDTH / 2 - forgotPasswordButton.getWidth() / 2, (float) Gwent.HEIGHT / 2 - 50);
        donNotHaveAnAccountButton.setPosition((float) Gwent.WIDTH / 2 - donNotHaveAnAccountButton.getWidth() / 2, (float) Gwent.HEIGHT / 2 - 100);
        stage.addActor(loginButton);
        stage.addActor(forgotPasswordButton);
        stage.addActor(donNotHaveAnAccountButton);

    }
}
