package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.ProfileMenuController;
import com.mygdx.game.view.Screens;

import java.util.ArrayList;

public class ProfileMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 500;
    private static final float FIELD_HEIGHT = 80;

    private final Stage stage;
    private final Table table;
    private Window errorWindow;
    private Window successWindow;
    private TextureRegion backgroundTexture; // Texture for background image
    // Labels
    private Label currentUsernameLabel;
    private Label currentEmailLabel;
    private Label currentNicknameLabel;
    private Label factionLabel;
    // Buttons
    private TextButton changePasswordButton;
    private TextButton changeUsernameButton;
    private TextButton changeNicknameButton;
    private TextButton changeEmailButton;
    private TextButton backButton;
    private TextButton logoutButton;
    private TextButton friendsButton;
    // Text Fields
    private TextField oldPasswordField;
    private TextField newPasswordField;
    private TextField confirmPasswordField;
    private TextField newUsernameField;
    private TextField newNicknameField;
    private TextField newEmailField;

    private final ProfileMenuController controller;

    public ProfileMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        controller = new ProfileMenuController();

        // Load background image
        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/profile_menu_background.jpg")));

        buttonAndFieldInit();

        // Listeners for buttons
        setupListeners();
    }

    private void buttonAndFieldInit() {
        ArrayList<String> profileInfo = controller.showProfile();
        currentUsernameLabel = new Label("Username: " + profileInfo.get(0), Gwent.singleton.getSkin(), "subtitle");
        currentEmailLabel = new Label("Email: " + profileInfo.get(2), Gwent.singleton.getSkin(), "subtitle");
        currentNicknameLabel = new Label("Nickname: " + profileInfo.get(1), Gwent.singleton.getSkin(), "subtitle");
        factionLabel = new Label("Faction: " + profileInfo.get(3), Gwent.singleton.getSkin(), "subtitle");

        changePasswordButton = new TextButton("Change Password", Gwent.singleton.getSkin());
        changeUsernameButton = new TextButton("Change Username", Gwent.singleton.getSkin());
        changeNicknameButton = new TextButton("Change Nickname", Gwent.singleton.getSkin());
        changeEmailButton = new TextButton("Change Email", Gwent.singleton.getSkin());
        backButton = new TextButton("Back", Gwent.singleton.getSkin());
        logoutButton = new TextButton("Logout", Gwent.singleton.getSkin());
        friendsButton = new TextButton("Friends", Gwent.singleton.getSkin());

        oldPasswordField = new TextField("", Gwent.singleton.getSkin());
        oldPasswordField.setMessageText("Old Password");
        oldPasswordField.setPasswordMode(true);
        oldPasswordField.setPasswordCharacter('*');

        newPasswordField = new TextField("", Gwent.singleton.getSkin());
        newPasswordField.setMessageText("New Password");
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');

        confirmPasswordField = new TextField("", Gwent.singleton.getSkin());
        confirmPasswordField.setMessageText("Confirm New Password");
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');

        newUsernameField = new TextField("", Gwent.singleton.getSkin());
        newUsernameField.setMessageText("New Username");

        newNicknameField = new TextField("", Gwent.singleton.getSkin());
        newNicknameField.setMessageText("New Nickname");

        newEmailField = new TextField("", Gwent.singleton.getSkin());
        newEmailField.setMessageText("New Email");

        // Add components to table
        table.add(currentUsernameLabel);
        table.add(currentEmailLabel);
        table.row().pad(10);
        table.add(currentNicknameLabel);
        table.add(factionLabel);
        table.row().pad(10);

        table.add(oldPasswordField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.add(newPasswordField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(confirmPasswordField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.add(changePasswordButton).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(newUsernameField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.add(changeUsernameButton).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(newNicknameField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.add(changeNicknameButton).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(10);
        table.add(newEmailField).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.add(changeEmailButton).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row().pad(20);
        table.add(friendsButton).width(FIELD_WIDTH/2).height(FIELD_HEIGHT).center();
        table.add(backButton).width(FIELD_WIDTH/2).height(FIELD_HEIGHT);
        table.row();
        table.add(logoutButton).width(FIELD_WIDTH/2).height(FIELD_HEIGHT);
    }

    private void setupListeners() {
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controller.changePassword(oldPasswordField.getText(), newPasswordField.getText(), confirmPasswordField.getText())) {
                    showSuccess("Password changed successfully");
                } else {
                    showError("Passwords should match and be valid! ");
                }
                updateLabels();
            }
        });

        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controller.changeUsername(newUsernameField.getText())) {
                    showSuccess("Username changed successfully");
                } else {
                    showError("Username is either invalid or already taken");
                }
                updateLabels();
            }
        });

        changeNicknameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controller.changeNickname(newNicknameField.getText())) {
                    showSuccess("Nickname changed successfully");
                }
                updateLabels();
            }
        });

        changeEmailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controller.changeEmail(newEmailField.getText())) {
                    showSuccess("Email changed successfully");
                } else {
                    showError("Email is invalid");
                }
                updateLabels();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gwent.singleton.changeScreen(Screens.MAIN_MENU);
            }
        });

        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.logout();
                Gwent.singleton.changeScreen(Screens.LOGIN);
            }
        });

        friendsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gwent.singleton.changeScreen(Screens.FRIENDS);
            }
        });
    }

    private void updateLabels() {
        ArrayList<String> profileInfo = controller.showProfile();
        currentUsernameLabel.setText("Username: " + profileInfo.get(0));
        currentEmailLabel.setText("Email: " + profileInfo.get(2));
        currentNicknameLabel.setText("Nickname: " + profileInfo.get(1));
        factionLabel.setText("Faction: " + profileInfo.get(3));
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

    private void showSuccess(String message) {
        successWindow = new Window("Success", Gwent.singleton.getSkin());
        successWindow.setMovable(false);
        successWindow.setColor(Color.GREEN);
        successWindow.setSize(800, 400);
        successWindow.setPosition((float) Gwent.WIDTH / 2 - 300, (float) Gwent.HEIGHT / 2 - 200);
        Label successLabel = new Label(message, Gwent.singleton.getSkin());
        TextButton okButton = new TextButton("OK", Gwent.singleton.getSkin());
        okButton.setSize(200, 100);
        okButton.setPosition(successWindow.getWidth() - 50, 0);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                successWindow.setVisible(false);
            }
        });
        successWindow.add(successLabel);
        successWindow.add(okButton);
        stage.addActor(successWindow);
    }

    @Override
    public void show() {
        updateLabels();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Draw background
        SpriteBatch batch = Gwent.singleton.getBatch();
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

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
