package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.RegisterMenuController;
import com.mygdx.game.controller.ScreenManager;
import com.mygdx.game.model.SecurityQuestion;

public class ChooseSecurityQuestionScreen implements Screen {
    //
    private Stage stage;
    private Dialog errorDialog;
    private Dialog welcomeDialog;
    // Buttons
    private TextButton backButton;
    private TextButton submitButton;
    private TextField answerField;
    private SelectBox<String> securityQuestionSelectBox;

    public ChooseSecurityQuestionScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        backButton = new TextButton("Back", Gwent.singleton.getSkin());
        submitButton = new TextButton("Submit", Gwent.singleton.getSkin());
        answerField = new TextField("", Gwent.singleton.getSkin());
        answerField.setMessageText("Write Your Answer");
        securityQuestionSelectBox = new SelectBox<>(Gwent.singleton.getSkin());
        securityQuestionSelectBox.setItems(SecurityQuestion.getAllQuestions());
        securityQuestionSelectBox.setSelected(SecurityQuestion.getAllQuestions()[0]);

        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("Choose Security Question", Gwent.singleton.getSkin())).colspan(2);
        table.row().pad(10);
        table.add(securityQuestionSelectBox).width(800).height(80).colspan(2);
        table.row().pad(30);
        table.add(answerField).width(400).height(80).colspan(2);
        table.row().pad(30);
        table.add(backButton).width(200).height(80);
        table.add(submitButton).width(200).height(80);
        stage.addActor(table);
    }

    @Override
    public void show() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RegisterMenuController.removeUser();
                dispose();
                ScreenManager.setRegisterScreen();
            }
        });
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (answerField.getText().isEmpty()) {
                    showError("please enter your answer");
                    return;
                }
                RegisterMenuController.setQuestionAndAnswerForUser(SecurityQuestion.getQuestionByString(securityQuestionSelectBox.getSelected()), answerField.getText());
                showWelcomeMessage();
                dispose();
                ScreenManager.setLoginScreen();
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
        stage.dispose();
    }

    private void showWelcomeMessage() {
        // Show submit message
        welcomeDialog = new Dialog("Welcome", Gwent.singleton.getSkin());
        welcomeDialog.setSize(800, 400);
        welcomeDialog.setPosition((float) Gwent.WIDTH / 2 - 300, (float) Gwent.HEIGHT / 2 - 200);
        Label welcomeLabel = new Label("Welcome to Gwent World!", Gwent.singleton.getSkin());
        welcomeDialog.show(stage);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                welcomeDialog.hide();
                dispose();
                ScreenManager.setLoginScreen();
            }
        }, 3); // Delay in seconds
    }

    private void showError(String message) {
        // Show error message
        errorDialog = new Dialog("Error", Gwent.singleton.getSkin());
        errorDialog.setSize(800, 400);
        errorDialog.setPosition((float) Gwent.WIDTH / 2 - 300, (float) Gwent.HEIGHT / 2 - 200);
        Label errorLabel = new Label(message, Gwent.singleton.getSkin());
        errorLabel.setColor(Color.RED);
        errorDialog.text(errorLabel);
        errorDialog.button("OK");
        errorDialog.show(stage);
    }

}
