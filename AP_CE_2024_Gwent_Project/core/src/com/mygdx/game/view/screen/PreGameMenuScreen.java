package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.PreGameMenuController;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.User;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;

import java.util.ArrayList;
import java.util.List;

public class PreGameMenuScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;
    private static final float CARD_WIDTH = 150; // Set the card width
    private static final float CARD_HEIGHT = 200; // Set the card height

    private PreGameMenuController controller;

    private Stage stage;
    private Table mainTable;
    private Table dashboard;
    private SpriteBatch batch;
    private Texture background;

    // Buttons
    private Button backButton;
    private Button changeFactionButton;
    private Button startGameButton;
    private Button deckButton;

    // Faction buttons
    private ImageButton northernRealmsButton;
    private ImageButton nilfgaardianButton;
    private ImageButton scoiataelButton;
    private ImageButton monstersButton;
    private ImageButton skelligeButton;

    // Window
    private Window factionWindow;
    private Window deckWindow;

    // List of selected cards
    private List<AbstractCard> selectedCards;

    // Label for current faction
    private Label currentFactionLabel;

    public PreGameMenuScreen() {
        controller = new PreGameMenuController();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        selectedCards = new ArrayList<>();

        // Load background image
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("backgrounds/main_background.png"));

        buttonAndFieldInit();
    }

    private void buttonAndFieldInit() {
        dashboardInit();
        mainTable.add(dashboard).center().expand().fill();
    }

    private void dashboardInit() {
        dashboard = new Table();

        deckButton = new TextButton("Deck", Gwent.singleton.getSkin());
        deckButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(deckButton).padBottom(20).center().row();
        deckButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                initializeDeckButtons();
                deckWindow.setVisible(true);
            }
        });

        changeFactionButton = new TextButton("Change Faction", Gwent.singleton.skin);
        changeFactionButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(changeFactionButton).padBottom(20).center();
        changeFactionButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                updateCurrentFactionLabel();
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

        deckWindow = new Window("Select your deck!", Gwent.singleton.getSkin());
        deckWindow.background(new TextureRegionDrawable(new Texture("backgrounds/faction_window_background.png")));
        setWindowSize(deckWindow);

        factionWindow = new Window("", Gwent.singleton.skin);
        factionWindow.background(new TextureRegionDrawable(new Texture("backgrounds/faction_window_background.png")));
        setWindowSize(factionWindow);

        // Add the current faction label to the factionWindow
        currentFactionLabel = new Label("Current Faction: ", Gwent.singleton.skin);
        factionWindow.add(currentFactionLabel).center().padBottom(20).row();

        // Initialize faction buttons
        initializeFactionButtons();

        factionWindow.padBottom(20);
    }

    private void setWindowSize(Window window) {
        window.setSize(Gwent.WIDTH, Gwent.HEIGHT);
        window.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
        );
        window.setVisible(false);
        stage.addActor(window);
    }

    private void initializeFactionButtons() {
        Table factionButtonTable = new Table();

        northernRealmsButton = new ImageButton(new TextureRegionDrawable(new Texture(Faction.NORTHERN_REALMS.getAssetFileName())));
        factionButtonTable.add(northernRealmsButton).padBottom(20).center().padRight(20);
        northernRealmsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.NORTHERN_REALMS.getName());
                updateCurrentFactionLabel();
                factionWindow.setVisible(false);
            }
        });

        nilfgaardianButton = new ImageButton(new TextureRegionDrawable(new Texture(Faction.NILFGAARD.getAssetFileName())));
        factionButtonTable.add(nilfgaardianButton).padBottom(20).center().padRight(20);
        nilfgaardianButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.NILFGAARD.getName());
                updateCurrentFactionLabel();
                factionWindow.setVisible(false);
            }
        });

        scoiataelButton = new ImageButton(new TextureRegionDrawable(new Texture(Faction.SCOIATAEL.getAssetFileName())));
        factionButtonTable.add(scoiataelButton).padBottom(20).center().padRight(20);
        scoiataelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.SCOIATAEL.getName());
                updateCurrentFactionLabel();
                factionWindow.setVisible(false);
            }
        });

        monstersButton = new ImageButton(new TextureRegionDrawable(new Texture(Faction.MONSTERS.getAssetFileName())));
        factionButtonTable.add(monstersButton).padBottom(20).center().padRight(20);
        monstersButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.MONSTERS.getName());
                updateCurrentFactionLabel();
                factionWindow.setVisible(false);
            }
        });

        skelligeButton = new ImageButton(new TextureRegionDrawable(new Texture(Faction.SKELLIGE.getAssetFileName())));
        factionButtonTable.add(skelligeButton).padBottom(20).center();
        skelligeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.setFaction(Faction.SKELLIGE.getName());
                updateCurrentFactionLabel();
                factionWindow.setVisible(false);
            }
        });

        factionWindow.add(factionButtonTable).center();
    }

    private void initializeDeckButtons() {
        deckWindow.clear();

        Table unselectedCardsTable = new Table();
        Table selectedCardsTable = new Table();

        unselectedCardsTable.setSize((Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() - 100);
        selectedCardsTable.setSize((Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() - 100);

        Faction currentFaction = User.getLoggedInUser().getFaction();
        addFactionCardsToTable(currentFaction, unselectedCardsTable, selectedCardsTable);
        Faction neutralFaction = Faction.getFactionByName("neutral");
        addFactionCardsToTable(neutralFaction, unselectedCardsTable, selectedCardsTable);

        ScrollPane unselectedScrollPane = new ScrollPane(unselectedCardsTable);
        ScrollPane selectedScrollPane = new ScrollPane(selectedCardsTable);

        // Set scrolling to the X direction
        unselectedScrollPane.setScrollingDisabled(true, false);
        selectedScrollPane.setScrollingDisabled(true, false);

        // Add the ScrollPanes side by side
        deckWindow.add(selectedScrollPane).right();
        deckWindow.add(unselectedScrollPane).left();
    }

    private void addFactionCardsToTable(Faction faction, Table unselectedCardsTable, Table selectedCardsTable) {
        int columnCounter = 0;

        for (AbstractCard card : AllCards.getFactionCardsByFaction(faction)) {
            ImageButton button = new ImageButton(new TextureRegionDrawable(new Texture(card.getAssetName())));
            button.setSize(CARD_WIDTH, CARD_HEIGHT);

            if (selectedCards.contains(card)) {
                selectedCardsTable.add(button).size(CARD_WIDTH, CARD_HEIGHT).padBottom(20).center().padRight(20);
            } else {
                unselectedCardsTable.add(button).size(CARD_WIDTH, CARD_HEIGHT).padBottom(20).center().padRight(20);
            }

            // Increment column counter and wrap to next row if needed
            if (columnCounter == 2) {
                columnCounter = 0;
                // Set the maximum table size (optional, adjust as needed)
                unselectedCardsTable.row().maxWidth(3 * CARD_WIDTH + 2 * 20); // Account for padding
                selectedCardsTable.row().maxWidth(3 * CARD_WIDTH + 2 * 20);
            }
            columnCounter++;


            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedCards.contains(card)) {
                        selectedCards.remove(card);
                        unselectedCardsTable.add(button).size(CARD_WIDTH, CARD_HEIGHT).padBottom(20).center().padRight(20);
                        selectedCardsTable.removeActor(button);

                        // Reposition remaining buttons in the selected cards table
                        float xPos = 20; // Starting x-position for buttons
                        float yPos = selectedCardsTable.getHeight() - CARD_HEIGHT - 20; // Starting y-position (from bottom)
                        int count = 0;
                        for (Actor actor : selectedCardsTable.getChildren()) {
                            actor.setPosition(xPos, yPos);
                            count++;
                            if (count % 3 == 0) { // Move to next row after 3 buttons
                                yPos -= CARD_HEIGHT + 20;
                                xPos = 20;
                            } else {
                                xPos += CARD_WIDTH + 20;
                            }
                        }
                    } else {
                        selectedCards.add(card);
                        selectedCardsTable.add(button).size(CARD_WIDTH, CARD_HEIGHT).padBottom(20).center().padRight(20);
                        unselectedCardsTable.removeActor(button);
                    }
                    unselectedCardsTable.pack();
                    selectedCardsTable.pack();
                }
            });

            columnCounter++;
            if (columnCounter == 3) {
                columnCounter = 0;
                selectedCardsTable.row();
            }
        }
    }

    private void updateCurrentFactionLabel() {
        currentFactionLabel.setText("Current Faction: " + User.getLoggedInUser().getFaction().getName());
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
        batch.dispose();
        background.dispose();
        stage.dispose();
    }
}
