package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.LoginMenuController;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.LoginRequest;

public class LoginMenuScreen implements Screen {
    private Stage stage;
    private Table table;
    private Dialog errorDialog;
    private SpriteBatch batch;
    // Buttons
    private TextButton loginButton;
    private TextButton forgotPasswordButton;
    private TextButton donNotHaveAnAccountButton;
    // TextFields
    private TextField usernameField;
    private TextField passwordField;
    // background
    private Texture background;

    public LoginMenuScreen() {
        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("backgrounds/main_background.jpg"));
        Gdx.input.setInputProcessor(stage);
        createFields();
    }

    @Override
    public void show() {
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loginHandler();
            }
        });
        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                forgotPasswordHandler();
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
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
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
        batch.dispose();
        background.dispose();
    }

    public void createFields() {
        table = new Table();
        table.setFillParent(true);
        // Center the fields

        table.align(2);
        table.padTop(100);
        usernameField = new TextField("", Gwent.singleton.getSkin());
        usernameField.setMessageText("Username");
        passwordField = new TextField("", Gwent.singleton.getSkin());
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        loginButton = new TextButton("Login", Gwent.singleton.getSkin());
        forgotPasswordButton = new TextButton("Forgot Password", Gwent.singleton.getSkin());
        donNotHaveAnAccountButton = new TextButton("Don't have an account?", Gwent.singleton.getSkin());

        table.add(usernameField).width(600).height(100).pad(10).row();
        table.add(passwordField).width(600).height(100).pad(10).row();
        table.add(loginButton).width(250).height(120).pad(10).row();
        table.add(forgotPasswordButton).width(500).height(120).row(); // Decrease padding
        table.add(donNotHaveAnAccountButton).width(600).height(120).pad(10).row();

        stage.addActor(table);
    }
    public void showError(String message) {
        errorDialog = new Dialog("Error", Gwent.singleton.getSkin());
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorDialog.hide();
            }
        }, 4); // Delay in seconds
    }
    private void loginHandler() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Client.getInstance().sendMassage(new LoginRequest(username, password));
    }
    private void forgotPasswordHandler() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            showError("Please fill the username field");
            return;
        }
        if(!LoginMenuController.doesThisUserExist(username)) {
            showError("User does not exist");
            return;
        }
        LoginMenuController.setUsernameForForgotPassword(username);
        dispose();
        LoginMenuController.goToForgotPasswordScreen();
    }
}
