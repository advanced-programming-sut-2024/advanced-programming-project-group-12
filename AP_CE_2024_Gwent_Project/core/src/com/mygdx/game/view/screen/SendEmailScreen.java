package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.model.network.email.Registration;

public class SendEmailScreen implements Screen {
    private Stage stage;
    private TextField emailTextField;
    private TextButton sendButton;
    public SendEmailScreen() {
        stage = new Stage();
        emailTextField = new TextField("", Gwent.singleton.skin);
        emailTextField.setSize(400, 120);
        emailTextField.setPosition(300, 400);
        sendButton = new TextButton("send", Gwent.singleton.skin);
        sendButton.setSize(200, 120);
        sendButton.setPosition(750, 400);
        stage.addActor(emailTextField);
        stage.addActor(sendButton);

        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Registration.registerNewUser(emailTextField.getText());
            }
        });
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1, 1, 1, 0);
        stage.getBatch().begin();
        stage.getBatch().end();
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

    }
}
