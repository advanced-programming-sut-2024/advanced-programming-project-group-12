package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.MainMenuController;

public class MainMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;

    private MainMenuController controller;
    private Stage stage;
    private Table table;
    private SpriteBatch batch;
    private Texture background;
    // Buttons
    private TextButton startGameButton;
    private TextButton showProfileButton;
    private TextButton exitButton;
    private TextButton leaderBoardButton;

    public MainMenuScreen() {
        controller = new MainMenuController();
        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("backgrounds/main_background.jpg"));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        buttonAndFieldInit();
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame();
            }
        });
        showProfileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showProfile();
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showLeaderBoard();
            }
        });
    }

    private void buttonAndFieldInit() {
        startGameButton = new TextButton("Start Game", Gwent.singleton.skin);
        startGameButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        table.add(startGameButton).padBottom(20);
        table.row();
        showProfileButton = new TextButton("Show Profile", Gwent.singleton.skin);
        showProfileButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        table.add(showProfileButton).padBottom(20);
        table.row();
        leaderBoardButton = new TextButton("Leaderboard", Gwent.singleton.skin);
        leaderBoardButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        table.add(leaderBoardButton).padBottom(20);
        table.row();
        exitButton = new TextButton("Exit", Gwent.singleton.skin);
        exitButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        table.add(exitButton).padBottom(20);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
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
}
