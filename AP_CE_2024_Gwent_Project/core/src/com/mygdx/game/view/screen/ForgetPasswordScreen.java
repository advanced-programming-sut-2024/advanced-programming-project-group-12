package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.LoginMenuController;
import com.mygdx.game.controller.RegisterMenuController;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.view.Screens;

public class ForgetPasswordScreen implements Screen {
    private Stage stage;
    private Table table;
    // TextFields
    private TextField answerField;
    private TextField newPasswordField;
    private TextField confirmPasswordField;
    //TextButtons
    private TextButton submitButton;
    private TextButton backButton;
    private TextButton showPasswordButton;

    private Dialog errorDialog;
    private Dialog finalMessageDialog;
    private SelectBox<String> questionSelectBox;

    public ForgetPasswordScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Initialize fields and button
        answerField = new TextField("", Gwent.singleton.getSkin());
        answerField.setMessageText("Answer");
        newPasswordField = new TextField("", Gwent.singleton.getSkin());
        newPasswordField.setMessageText("New Password");
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setPasswordMode(true);
        confirmPasswordField = new TextField("", Gwent.singleton.getSkin());
        confirmPasswordField.setMessageText("Confirm Password");
        confirmPasswordField.setPasswordCharacter('*');
        confirmPasswordField.setPasswordMode(true);
        submitButton = new TextButton("Submit", Gwent.singleton.getSkin());
        backButton = new TextButton("Back", Gwent.singleton.getSkin());
        showPasswordButton = new TextButton("Show", Gwent.singleton.getSkin());
        questionSelectBox = new SelectBox<>(Gwent.singleton.getSkin());


        setPositions();
        // Display security questions
        String[] securityQuestions = SecurityQuestion.getAllQuestions();
        questionSelectBox.setItems(securityQuestions);
        questionSelectBox.setSelected(securityQuestions[0]);



    }

    @Override
    public void show() {
        // Add listener to submit button
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleAnswerSubmission();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LoginMenuController.removeUsernameForForgotPassword();
                Gwent.singleton.changeScreen(Screens.LOGIN);
            }
        });
        showPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(newPasswordField.isPasswordMode()) {
                    newPasswordField.setPasswordMode(false);
                    confirmPasswordField.setPasswordMode(false);
                    showPasswordButton.setText("Hide");
                } else {
                    newPasswordField.setPasswordMode(true);
                    confirmPasswordField.setPasswordMode(true);
                    showPasswordButton.setText("Show");
                }
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
        table.clear();
        stage.clear();
    }
    private void setPositions() {
        table = new Table();
        table.setFillParent(true);
        table = table.center();
        table.add(new Label("Security Question", Gwent.singleton.getSkin())).pad(10);
        table.add(questionSelectBox).pad(10).row();
        table.add(new Label("Answer", Gwent.singleton.getSkin())).pad(10);
        table.add(answerField).width(500).height(100).pad(10).row();
        table.add().pad(30).row();
        table.add(new Label("New Password", Gwent.singleton.getSkin())).pad(10);
        table.add(newPasswordField).width(500).height(100).pad(10).row();
        table.add(new Label("Confirm Password", Gwent.singleton.getSkin())).pad(10);
        table.add(confirmPasswordField).width(500).height(100).pad(10).row();
        table.add(backButton).pad(10);
        table.add(showPasswordButton).pad(10);
        table.add(submitButton).row();
        table.pack();
        stage.addActor(table);
    }
    private void handleAnswerSubmission() {
        String selectedQuestion = questionSelectBox.getSelected();
        String answer = answerField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if(answer.isEmpty() || newPassword.isEmpty() || confirmPasswordField.getText().isEmpty()) {
            showError("Please fill all fields");
        } else if (!LoginMenuController.isAnswerCorrect(selectedQuestion, answer)) {
            showError("Incorrect answer");
        } else if(!RegisterMenuController.isPasswordValid(newPassword, confirmPassword).equals("Valid password")) {
            showError(RegisterMenuController.isPasswordValid(newPassword, confirmPassword));
        }
        else {
            showChangePasswordDialog(newPassword);
            LoginMenuController.changePassword(newPassword);
        }
    }

    private void showChangePasswordDialog(String newPassword) {
        finalMessageDialog = new Dialog("Password Changed", Gwent.singleton.getSkin());
        finalMessageDialog.text("Your password has been changed to " + newPassword);
        finalMessageDialog.button("OK");
        finalMessageDialog.show(stage);
    }

    private void showError(String message) {
        errorDialog = new Dialog("Error", Gwent.singleton.getSkin());
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);
    }
}
