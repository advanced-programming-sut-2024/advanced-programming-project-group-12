package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Gwent;
import com.mygdx.game.view.Screens;

public class EmailVerificationScreen implements Screen {
    private Stage stage;
    private Dialog emailDialog;

    public EmailVerificationScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        emailDialog = new Dialog("Verification Email Sent", Gwent.singleton.getSkin());
        emailDialog.setSize(800, 400);
        emailDialog.setPosition((float) Gwent.WIDTH / 2 - 400, (float) Gwent.HEIGHT / 2 - 200);
        Label emailLabel = new Label("A verification email has been sent to your email address. Please check your email to complete the registration.", Gwent.singleton.getSkin());
        emailLabel.setColor(Color.WHITE);
        emailDialog.text(emailLabel);
        emailDialog.button("OK");
        emailDialog.show(stage);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gwent.singleton.changeScreen(Screens.LOGIN);
            }
        }, 5); // Delay in seconds
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
