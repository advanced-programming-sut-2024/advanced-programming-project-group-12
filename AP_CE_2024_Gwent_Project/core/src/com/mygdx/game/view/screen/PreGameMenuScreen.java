package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static final float FIELD_WIDTH = 100;
    private static final float FIELD_HEIGHT = 80;
    private static final float CARD_WIDTH = 170; // Adjust the width as needed
    private static final float CARD_HEIGHT = 300;  // Set the card height

    private PreGameMenuController controller;

    private Stage stage;
    private Table mainTable;
    private Table dashboard;
    private Table unselectedCardsTable;
    private Table selectedCardsTable;
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
    private List<AbstractCard> unselectedCards;

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
        unselectedCards = new ArrayList<>();

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
                initializeDecks();
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

        currentFactionLabel = new Label("Current Faction: ", Gwent.singleton.skin);
        factionWindow.add(currentFactionLabel).center().padBottom(20).row();

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

    private void initializeDecks() {
        deckWindow.clear();
        deckWindow.pad(20);

        ScrollPane unselectedScrollPane = new ScrollPane(unselectedCardsTable = new Table());
        ScrollPane selectedScrollPane = new ScrollPane(selectedCardsTable = new Table());

        // Add ScrollPanes to the window
        deckWindow.add(unselectedScrollPane).width(750).height((CARD_HEIGHT * 2)+20).padRight(20);
        deckWindow.add(selectedScrollPane).width(750).height((CARD_HEIGHT*2)+20).padLeft(20);

        // Add cards to unselected cards table
        addFactionAndNeutralCardsToTable(User.getLoggedInUser().getFaction(), unselectedCardsTable);

        // Add cards to selected cards table
        addSelectedCardsToTable();

        stage.addActor(deckWindow);
    }

    private void addFactionAndNeutralCardsToTable(Faction faction, Table table) {
        int columnCounter = 0;
        for (AbstractCard card : AllCards.getFactionCardsByFaction(faction)) {
            if (columnCounter == 3) {
                table.row();
                columnCounter = 0;
            }
            addCardToTable(card, table, false); // Added false to indicate unselected
            unselectedCards.add(card);
            columnCounter++;
        }
        for (AbstractCard card : AllCards.getNeutralCards()) {
            if (columnCounter == 3) {
                table.row();
                columnCounter = 0;
            }
            addCardToTable(card, table, false); // Added false to indicate unselected
            unselectedCards.add(card);
            columnCounter++;
        }
    }



    private void addSelectedCardsToTable() {
        int columnCounter = 0;
        selectedCardsTable.clearChildren();
        for (AbstractCard card : selectedCards) {
            if (columnCounter == 3) {
                selectedCardsTable.row();
                columnCounter = 0;
            }
            unselectedCards.add(card);
            addCardToTable(card, selectedCardsTable, true); // Added true to indicate selected
            columnCounter++;
        }
    }


    private void updateCardPositions() {
        unselectedCardsTable.clearChildren();
        selectedCardsTable.clearChildren();
        for (AbstractCard card : unselectedCards) {
            addCardToTable(card, unselectedCardsTable, false);
        }
        for (AbstractCard card : selectedCards) {
            addCardToTable(card, selectedCardsTable, true);
        }
    }


    private void addCardToTable(AbstractCard card, Table table, boolean isSelected) {
        // Create the ImageButton for the card
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(card.getAssetName()));
        ImageButton cardButton = new ImageButton(drawable);
        cardButton.setTransform(true); // Enable scaling

        float originalWidth = drawable.getMinWidth();
        float originalHeight = drawable.getMinHeight();

        float scale = Math.min(CARD_WIDTH / originalWidth, CARD_HEIGHT / originalHeight);
        cardButton.getImageCell().size(originalWidth * scale, originalHeight * scale);

        // Add a listener to handle clicks
        cardButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (isSelected) {
                    selectedCards.remove(card);
                    unselectedCards.add(card);
                    selectedCardsTable.removeActor(cardButton);
                    addCardToTable(card, unselectedCardsTable, false);
                } else {
                    selectedCards.add(card);
                    unselectedCards.remove(card);
                    unselectedCardsTable.removeActor(cardButton);
                    addCardToTable(card, selectedCardsTable, true);
                }
                updateCardPositions();
            }
        });

        // Check if adding the card exceeds the maximum cards per row
        if (table.getCells().size % 3 == 0) {
            table.row(); // Add a new row if the current row is full
        }

        // Add the card button to the table with padding and size
        table.add(cardButton).pad(10).size(CARD_WIDTH, CARD_HEIGHT);
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
