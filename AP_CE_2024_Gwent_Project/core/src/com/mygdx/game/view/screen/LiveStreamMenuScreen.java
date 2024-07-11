package com.mygdx.game.view.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.LiveStreamMenuController;
import com.mygdx.game.view.Screens;

import java.util.ArrayList;
import java.util.List;

public class LiveStreamMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture background;
    private SpriteBatch batch;
    private BitmapFont font;
    private Table gameListTable;
    private List<String> gameNames;
    private Button backButton;

    public LiveStreamMenuScreen() {
        stage = new Stage();
        skin = Gwent.singleton.skin;
        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Texture("bg/live-stream-menu-background.jpg");
        gameListTable = new Table(skin);
        gameListTable.setFillParent(true);
        stage.addActor(gameListTable);

        gameNames = LiveStreamMenuController.getGames();
        for (String gameName : gameNames) {
            addGameListItem(gameName);
        }
        backButton = new TextButton("back", skin);
        gameListTable.add(backButton).width(200).height(120).center().padLeft(70);

    }

    private void addGameListItem(String gameName) {
        Label gameNameLabel = new Label(gameName, skin);
        gameNameLabel.setFontScale(1.5f);
        gameNameLabel.setColor(Color.MAGENTA);
        Button joinButton = new TextButton("Join", skin);

        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Send a request to join the game
                LiveStreamMenuController.joinGame(gameName);
                // Implement the logic to send a request to join the game
            }
        });

        gameListTable.add(gameNameLabel).width(400).height(100).pad(10);
        gameListTable.add(joinButton).width(300).height(100).pad(10);
        gameListTable.row();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gwent.singleton.changeScreen(Screens.MAIN_MENU);
            }
        });    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
        stage.act(delta);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        font.dispose();
    }
}