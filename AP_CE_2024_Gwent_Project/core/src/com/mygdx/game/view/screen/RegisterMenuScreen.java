package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.RegisterMenuController;
import com.mygdx.game.controller.ScreenManager;

public class RegisterMenuScreen implements Screen {
    //
    private Stage stage;
    private Table table;
    // Buttons
    private TextButton registerButton;
    private TextButton randomPasswordButton;
    private TextButton backButton;
    //TextFields
    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;
    private TextField emailField;
    private TextField nicknameField;

    public RegisterMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        buttonAndFieldInit();
    }
    @Override
    public void show() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.setLoginScreen();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        Gwent.singleton.getBatch().begin();
        Gwent.singleton.getBatch().end();
        stage.act();
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
    private void buttonAndFieldInit() {
        registerButton = new TextButton("Register", Gwent.singleton.getSkin());
        randomPasswordButton = new TextButton("Random Password", Gwent.singleton.getSkin());
        backButton = new TextButton("Back", Gwent.singleton.getSkin());
        usernameField = new TextField("", Gwent.singleton.getSkin());
        usernameField.setMessageText("Username");
        nicknameField = new TextField("", Gwent.singleton.getSkin());
        nicknameField.setMessageText("Nickname");
        passwordField = new TextField("", Gwent.singleton.getSkin());
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        confirmPasswordField = new TextField("", Gwent.singleton.getSkin());
        confirmPasswordField.setMessageText("Confirm Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        emailField = new TextField("", Gwent.singleton.getSkin());
        emailField.setMessageText("Email");
        //set color
        usernameField.setColor(Color.BLUE);
        nicknameField.setColor(Color.BLUE);
        passwordField.setColor(Color.BLUE);
        confirmPasswordField.setColor(Color.BLUE);
        emailField.setColor(Color.BLUE);


        table.add(usernameField).width(400).height(50);
        table.row().pad(10);
        table.add(nicknameField).width(400).height(50);
        table.row().pad(10);
        table.add(passwordField).width(400).height(50);
        table.row().pad(10);
        table.add(confirmPasswordField).width(400).height(50);
        table.row().pad(10);
        table.add(emailField).width(400).height(50);
        table.row().pad(10);
        registerButton.setPosition(Gwent.WIDTH / 2 - registerButton.getWidth() / 2 - 150, Gwent.HEIGHT / 2 - 250);
        randomPasswordButton.setPosition(Gwent.WIDTH / 2 - randomPasswordButton.getWidth() / 2 + 100, Gwent.HEIGHT / 2 - 250);
        backButton.setPosition(0, Gwent.HEIGHT - 50);
        stage.addActor(backButton);
        stage.addActor(registerButton);
        stage.addActor(randomPasswordButton);
    }
}
