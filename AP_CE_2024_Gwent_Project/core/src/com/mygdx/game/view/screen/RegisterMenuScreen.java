package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.RegisterMenuController;
import com.mygdx.game.controller.ScreenManager;
import com.mygdx.game.model.SecurityQuestion;

public class RegisterMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;
    //
    private Stage stage;
    private Table table;
    private Window errorWindow;
    // Buttons
    private TextButton registerButton;
    private TextButton randomPasswordButton;
    private TextButton backButton;
    private TextButton showPasswordButton;
    //TextFields
    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;
    private TextField emailField;
    private TextField nicknameField;
    //Labels
    private Label passwordStateLabel;
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
    }
    @Override
    public void show() {
        showPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(passwordField.isPasswordMode()) {
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
                dispose();
                ScreenManager.setLoginScreen();
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
                passwordField.setText(RegisterMenuController.randomPasswordGenerator());
                confirmPasswordField.setText(passwordField.getText());
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0, 0);
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
        showPasswordButton = new TextButton("Show" , Gwent.singleton.getSkin());
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
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        emailField = new TextField("", Gwent.singleton.getSkin());
        emailField.setMessageText("Email");
        passwordStateLabel = new Label("", Gwent.singleton.getSkin());
        //set color
        usernameField.setColor(Color.ROYAL);
        nicknameField.setColor(Color.ROYAL);
        passwordField.setColor(Color.ROYAL);
        confirmPasswordField.setColor(Color.ROYAL);
        emailField.setColor(Color.ROYAL);
        //set size
        backButton.setSize(200, 100);
        registerButton.setSize(400, 100);
        randomPasswordButton.setSize(500, 100);


        //set position
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
        table.add(registerButton);
        table.add(randomPasswordButton);
        backButton.setPosition(0, Gwent.HEIGHT - 100);

        stage.addActor(backButton);
    }
    private void registerHandler(String username, String nickname, String password, String confirmPassword, String email) {
        if(username.isEmpty() || nickname.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || email.isEmpty()) {
            showError("Please fill all fields");
            return;
        } else if(!RegisterMenuController.isUsernameValid(username)) {
            showError("Invalid username");
            return;
        } else if(!RegisterMenuController.isPasswordValid(password, confirmPassword).equals("Valid password")) {
            showError(RegisterMenuController.isPasswordValid(password, confirmPassword));
            return;
        } else if(!RegisterMenuController.isEmailValid(email)) {
            showError("Invalid email");
            return;
        } else if(!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return;
        } else if(RegisterMenuController.isUsernameTaken(username)) {
            showError("Username is already taken");
            return;
        }else {
            showChooseSecurityQuestionWindow();
        }
    }
    private void clearFields() {
        usernameField.setText("");
        nicknameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        emailField.setText("");
    }
    private void showError(String message) {
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
    private void showChooseSecurityQuestionWindow() {
        Window chooseSecurityQuestionWindow = new Window("Choose Security Question", Gwent.singleton.getSkin());
        TextField answerField = new TextField("", Gwent.singleton.getSkin());
        answerField.setMessageText("Answer");
        chooseSecurityQuestionWindow.setMovable(false);
        chooseSecurityQuestionWindow.setSize(600, 400);
        chooseSecurityQuestionWindow.setPosition((float) Gwent.WIDTH / 2 - 300, (float) Gwent.HEIGHT / 2 - 200);
        Label questionLabel = new Label("Choose a security question", Gwent.singleton.getSkin());
        SelectBox<SecurityQuestion> questionSelectBox = new SelectBox<>(Gwent.singleton.getSkin());
        questionSelectBox.setItems(SecurityQuestion.values());
        TextButton okButton = new TextButton("OK", Gwent.singleton.getSkin());
        okButton.setSize(200, 100);
        okButton.setPosition(200, 0);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(answerField.getText().isEmpty()) {
                    showError("Please fill the answer field");
                    return;
                }
                SecurityQuestion question = questionSelectBox.getSelected();
                String answer = answerField.getText();
                RegisterMenuController.register(usernameField.getText(), nicknameField.getText(), passwordField.getText(), emailField.getText(), question, answer);
                clearFields();
                chooseSecurityQuestionWindow.setVisible(false);
                dispose();
                ScreenManager.setLoginScreen();
            }
        });
        chooseSecurityQuestionWindow.add(questionLabel);
        chooseSecurityQuestionWindow.add(questionSelectBox);
        chooseSecurityQuestionWindow.add(okButton);
        stage.addActor(chooseSecurityQuestionWindow);
    }

}
