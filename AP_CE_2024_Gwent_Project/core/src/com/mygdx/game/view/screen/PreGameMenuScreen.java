package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.PreGameMenuController;
import com.mygdx.game.model.Faction;

public class PreGameMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;

    private PreGameMenuController controller;

    private Stage stage;
    private Table mainTable;
    private Table dashboard;

    // Buttons
    private Button backButton;
    private Button changeFactionButton;
    private Button startGameButton;

    // Faction buttons
    private Button northernRealmsButton;
    private Button nilfgaardianButton;
    private Button scoiataelButton;
    private Button monstersButton;
    private Button skelligeButton;
    private Button neutralButton;

    // Window
    private Window factionWindow;

    public PreGameMenuScreen() {
        controller = new PreGameMenuController();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        buttonAndFieldInit();
    }

    private void buttonAndFieldInit() {
        dashboardInit();
        mainTable.add(dashboard).center().expand().fill();
    }

    private void dashboardInit() {
        dashboard = new Table();

        changeFactionButton = new TextButton("Change Faction", Gwent.singleton.skin);
        changeFactionButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(changeFactionButton).padBottom(20).center();
        changeFactionButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                factionWindow.setVisible(true);
            }
        });
        dashboard.row();

        startGameButton = new TextButton("Start Game", Gwent.singleton.skin);
        startGameButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(startGameButton).padBottom(20).center();
        startGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                controller.startGame();
            }
        });
        dashboard.row();

        backButton = new TextButton("Back", Gwent.singleton.skin);
        backButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(backButton).padBottom(20).center();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                controller.gotoMainMenu();
            }
        });

        dashboard.setFillParent(true);

        factionWindow = new Window("Choose Your Faction", Gwent.singleton.skin);
        factionWindow.setSize(900, 510);
        factionWindow.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - factionWindow.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - factionWindow.getHeight() / 2
        );
        factionWindow.setVisible(false);
        stage.addActor(factionWindow);

        // Initialize faction buttons
        initializeFactionButtons();

        factionWindow.padBottom(20);
    }

    private void initializeFactionButtons() {
        northernRealmsButton = new TextButton("Northern Realms", Gwent.singleton.skin);
        factionWindow.add(northernRealmsButton).padBottom(20).center().padTop(100);
        northernRealmsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.NORTHERN_REALMS.getName());
                factionWindow.setVisible(false);
            }
        });

        nilfgaardianButton = new TextButton("Nilfgaardian", Gwent.singleton.skin);
        factionWindow.add(nilfgaardianButton).padBottom(20).center().padTop(100);
        nilfgaardianButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.NILFGAARD.getName());
                factionWindow.setVisible(false);
            }
        });
        factionWindow.row();

        scoiataelButton = new TextButton("Scoiatael", Gwent.singleton.skin);
        factionWindow.add(scoiataelButton).padBottom(20).center();
        scoiataelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.SCOIATAEL.getName());
                factionWindow.setVisible(false);
            }
        });

        monstersButton = new TextButton("Monsters", Gwent.singleton.skin);
        factionWindow.add(monstersButton).padBottom(20).center();
        monstersButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.MONSTERS.getName());
                factionWindow.setVisible(false);
            }
        });
        factionWindow.row();

        skelligeButton = new TextButton("Skellige", Gwent.singleton.skin);
        factionWindow.add(skelligeButton).padBottom(20).center();
        skelligeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.SKELLIGE.getName());
                factionWindow.setVisible(false);
            }
        });

        neutralButton = new TextButton("Neutral", Gwent.singleton.skin);
        factionWindow.add(neutralButton).padBottom(20).center();
        neutralButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.NEUTRAL.getName());
                factionWindow.setVisible(false);
            }
        });
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
