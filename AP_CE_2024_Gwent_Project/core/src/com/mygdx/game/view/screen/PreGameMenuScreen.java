package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.PreGameMenuController;

public class PreGameMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;

    PreGameMenuController controller;

    private Stage stage;
    private Table mainTable;
    private Table allCards;
    private Table dashboard;
    private Table deck;

    Button backButton;
    Button changeFactionButton;
    Button startGameButton;

    public PreGameMenuScreen() {
        controller = new PreGameMenuController();
        stage = new Stage();
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        buttonAndFieldInit();
    }

    private void buttonAndFieldInit() {
        deck = new Table();
        mainTable.add(deck).expand().fill();

        dashboard = new Table();
        changeFactionButton = new TextButton("Change Faction", Gwent.singleton.skin);
        changeFactionButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(changeFactionButton).padBottom(20);
        dashboard.row();
        startGameButton = new TextButton("Start Game", Gwent.singleton.skin);
        startGameButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(startGameButton).padBottom(20);
        dashboard.row();
        backButton = new TextButton("Back", Gwent.singleton.skin);
        backButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(backButton).padBottom(20);
        mainTable.add(dashboard).expand().fill();

        deck = new Table();
        mainTable.add(deck).expand().fill();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
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
}
