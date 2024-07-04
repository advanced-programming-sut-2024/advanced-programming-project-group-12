package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.GameRequestController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.model.User;

public class GameRequestScreen implements Screen {
    private final GameRequestController controller;
    private final Stage stage;
    private final Table table;
    private final SpriteBatch batch;
    private final Texture background;
    private final TextField nameField;
    private final Button searchButton;
    private float timer;
    private boolean requestSent;
    private final Label timerLabel;
    private final Label requestSentLabel;
    private final Label errorLabel;
    private final Skin skin;

    public GameRequestScreen() {
        this.controller = new GameRequestController();
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("backgrounds/main_background.jpg"));
        this.skin = Gwent.singleton.getSkin();

        this.nameField = new TextField("", skin);
        this.searchButton = new TextButton("Search", skin);
        this.timerLabel = new Label("", skin);
        this.requestSentLabel = new Label("", skin);
        this.errorLabel = new Label("", skin);

        // Add the text field and button to the table
        table.setFillParent(true);
        table.add(nameField).width(500).pad(10);
        table.row();
        table.add(searchButton).width(200).pad(10);
        table.row();
        table.add(timerLabel).pad(10);
        table.row();
        table.add(requestSentLabel).pad(10);
        table.row();
        table.add(errorLabel).pad(10);
        stage.addActor(table);

        // Add a click listener to the search button
        searchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!requestSent) {
                    String name = nameField.getText();
                    if (controller.userExists(name)) {
                        String sender = User.getLoggedInUser().getUsername();
                        controller.sendGameRequest(name, sender);
                        timer = 120;  // Set timer to 2 minutes
                        requestSent = true;
                        requestSentLabel.setText("Request sent to " + name);
                        errorLabel.setText("");  // Clear any previous error
                        System.out.println("Request sent");
                    } else {
                        errorLabel.setText("User not found.");
                    }
                }
            }
        });

        requestSent = false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (requestSent) {
            timer -= delta;
            if (timer <= 0) {
                timer = 0;
                controller.requestTimedOut();
                requestSent = false;
                requestSentLabel.setText("Request timed out.");
            }
            timerLabel.setText("Request pending: " + (int) timer + " seconds");
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        background.dispose();
        // Do not dispose skin if it is shared across screens
    }
}
