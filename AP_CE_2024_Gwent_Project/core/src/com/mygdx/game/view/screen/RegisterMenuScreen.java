package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.RegisterMenuController;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.email.Sender;
import com.mygdx.game.model.network.email.UserVerificationStore;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;

public class RegisterMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;

    private Stage stage;
    private SpriteBatch batch;
    private Table table;
    private Window errorWindow;

    // Buttons
    private TextButton registerButton;
    private TextButton randomPasswordButton;
    private TextButton backButton;
    private TextButton showPasswordButton;
    private TextButton sendVerificationCodeButton;
    private TextButton confirmRegistrationButton;

    // TextFields
    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;
    private TextField emailField;
    private TextField nicknameField;
    private TextField verificationCodeField;

    // Labels
    private Label passwordStateLabel;
    private Label verificationCodeLabel;

    // Other UI elements
    private Texture background;

    public RegisterMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        buttonAndFieldInit();

        passwordField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                RegisterMenuController.updatePasswordStrength(passwordField.getText(), passwordStateLabel);
                return super.keyTyped(event, character);
            }
        });

        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("backgrounds/main_background.jpg"));
    }

    @Override
    public void show() {
        showPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (passwordField.isPasswordMode()) {
                    passwordField.setPasswordMode(false);
                    confirmPasswordField.setPasswordMode(false);
                    showPasswordButton.setText("Hide");
                } else {
                    passwordField.setPasswordMode(true);
                    confirmPasswordField.setPasswordMode(true);
                    showPasswordButton.setText("Show");
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gwent.singleton.changeScreen(Screens.LOGIN);
            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String nickname = nicknameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                String email = emailField.getText();
                registerHandler(username, nickname, password, confirmPassword, email);
            }
        });

        randomPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String generatedPassword = RegisterMenuController.randomPasswordGenerator();
                passwordField.setText(generatedPassword);
                confirmPasswordField.setText(generatedPassword);
                RegisterMenuController.updatePasswordStrength(passwordField.getText(), passwordStateLabel);

            }
        });

        sendVerificationCodeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String email = emailField.getText();
                if (RegisterMenuController.isEmailValid(email)) {
                    String verificationCode = RegisterMenuController.generateVerificationCode();
                    RegisterMenuController.storeTempVerificationCode(verificationCode);
                    Sender.sendVerificationCode(email, verificationCode);
                    verificationCodeLabel.setText("Verification code sent to your email.");
                } else {
                    showError("Invalid email format", null);
                }
            }
        });

        confirmRegistrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String enteredVerificationCode = verificationCodeField.getText();
                if (RegisterMenuController.verifyVerificationCode(enteredVerificationCode)) {
                    String username = usernameField.getText();
                    String nickname = nicknameField.getText();
                    String password = passwordField.getText();
                    String email = emailField.getText();
                    User.setToBeSignedUp(new User (username, nickname, password, email));
                    clearFields();
                    Gwent.singleton.changeScreen(Screens.CHOOSE_SECURITY_QUESTION);
                } else {
                    showError("Invalid verification code", null);
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0, 0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gwent.WIDTH, Gwent.HEIGHT);
        batch.end();
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
        stage.dispose();
    }

    private void buttonAndFieldInit() {
        showPasswordButton = new TextButton("Show", Gwent.singleton.getSkin());
        registerButton = new TextButton("Register", Gwent.singleton.getSkin());
        randomPasswordButton = new TextButton("Random Password", Gwent.singleton.getSkin());
        backButton = new TextButton("Back", Gwent.singleton.getSkin());
        sendVerificationCodeButton = new TextButton("Send Verification Code", Gwent.singleton.getSkin());
        confirmRegistrationButton = new TextButton("Confirm Registration", Gwent.singleton.getSkin());

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
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        emailField = new TextField("", Gwent.singleton.getSkin());
        emailField.setMessageText("Email");
        verificationCodeField = new TextField("", Gwent.singleton.getSkin());
        verificationCodeField.setMessageText("Verification Code");

        passwordStateLabel = new Label("", Gwent.singleton.getSkin());
        verificationCodeLabel = new Label("", Gwent.singleton.getSkin());

        // Set color
        usernameField.setColor(Color.ROYAL);
        nicknameField.setColor(Color.ROYAL);
        passwordField.setColor(Color.ROYAL);
        confirmPasswordField.setColor(Color.ROYAL);
        emailField.setColor(Color.ROYAL);

        // Set size
        backButton.setSize(200, 100);
        registerButton.setSize(400, 100);
        randomPasswordButton.setSize(500, 100);

        // Set position
        table.add(usernameField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.add(nicknameField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(passwordField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.add(confirmPasswordField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(showPasswordButton).width(150).height(100);
        table.add(passwordStateLabel);
        table.row().pad(10);
        table.add(emailField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(sendVerificationCodeButton);
        table.add(verificationCodeField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(confirmRegistrationButton);
        table.add(randomPasswordButton);
        table.row().pad(10);

        backButton.setPosition(0, Gwent.HEIGHT - 100);
        stage.addActor(backButton);
    }

    private void registerHandler(String username, String nickname, String password, String confirmPassword, String email) {
        if (!RegisterMenuController.isUsernameValid(username)) {
            showError("Invalid username", username);
            return;
        }
        if (!RegisterMenuController.isPasswordValid(password,confirmPassword).equals("Valid password")) {
            showError("Invalid password", null);
            return;
        }
        if (!RegisterMenuController.isEmailValid(email)) {
            showError("Invalid email format", null);
            return;
        }
        if (RegisterMenuController.isUsernameTaken(username)) {
            showError("Username is already in use", username);
            return;
        }

        // Create a new User object
        User user = new User(username, nickname, password, email);
        User.setToBeSignedUp(user); // Store the user for later use

        // Transition to the next screen
        Gwent.singleton.changeScreen(Screens.CHOOSE_SECURITY_QUESTION);
    }


    private void clearFields() {
        usernameField.setText("");
        nicknameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        emailField.setText("");
        verificationCodeField.setText("");
    }

    public void showError(String message, String oldUsername) {
        if (oldUsername != null) {
            String newUsername = RegisterMenuController.generateNewUsername(oldUsername);
            usernameField.setText(newUsername);
            message = "This username is already taken. You can choose " + newUsername + " instead.";
        }

        errorWindow = new Window("Error", Gwent.singleton.getSkin());
        errorWindow.setMovable(false);
        errorWindow.setColor(Color.RED);
        errorWindow.setSize(800, 400);
        errorWindow.setPosition((float) Gwent.WIDTH / 2 - 300, (float) Gwent.HEIGHT / 2 - 200);

        Label errorLabel = new Label(message, Gwent.singleton.getSkin());
        TextButton okButton = new TextButton("OK", Gwent.singleton.getSkin());
        okButton.setSize(200, 100);
        okButton.setPosition(errorWindow.getWidth() - 50, 0);

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                errorWindow.setVisible(false);
            }
        });

        errorWindow.add(errorLabel);
        errorWindow.add(okButton);
        stage.addActor(errorWindow);
    }
}
