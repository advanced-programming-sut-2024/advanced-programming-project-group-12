package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.LeaderBoardMenuController;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;

import java.util.List;

public class LeaderBoardMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;
    private final Stage stage;
    private final Table table;
    private final LeaderBoardMenuController controller;
    private TextureRegion backgroundTexture;
    private SpriteBatch batch;
    private Button backButton;

    public LeaderBoardMenuScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();

        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/leaderboard_background.jpg")));

        table = new Table(Gwent.singleton.getSkin());
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(backgroundTexture));
        stage.addActor(table);

        controller = new LeaderBoardMenuController();

        populateTable();

        backButton = new TextButton("Back", Gwent.singleton.getSkin());
        backButton.setSize(FIELD_WIDTH/2, FIELD_HEIGHT);
        backButton.setPosition(Gwent.WIDTH / 2 - 100 , 150);
        stage.addActor(backButton);

        setupListeners();
    }

    private void setupListeners() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gwent.singleton.changeScreen(Screens.MAIN_MENU);
            }
        });
    }

    private void populateTable() {
        List<User> sortedUsers = controller.sortedUsers();

        // Header
        Label nameHeader = new Label("Name", Gwent.singleton.getSkin(), "title");
        Label winCountHeader = new Label("Wins", Gwent.singleton.getSkin(), "title");
        table.add(nameHeader).pad(10).expandX().padBottom(5);
        table.add(winCountHeader).pad(10).expandX().padBottom(5);
        table.row();

        int rank = 1;
        // Rows
        for (User user : sortedUsers) {
            Label nameLabel = new Label(rank + "." + user.getUsername(), Gwent.singleton.getSkin(), "subtitle");
            Label winCountLabel = new Label(String.valueOf(controller.getUserWinCount(user)), Gwent.singleton.getSkin(), "subtitle");
            table.add(nameLabel).pad(10).expandX().padBottom(5);
            table.add(winCountLabel).pad(10).expandX().padBottom(5);
            table.row().padTop(5);
            rank++;
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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
    }
}
