package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.PreGameMenuController;
import com.mygdx.game.controller.ScreenManager;
<<<<<<< HEAD
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.AllCards;
=======
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.User;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.CommanderCards;
import com.mygdx.game.model.card.Hero;
>>>>>>> pregameMenu

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
    private Button saveDeckButton;
    private Button downloadDeckButton;
    private Button uploadDeckButton;
    private Button selectLeaderButton;
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

    // Labels
    private Label currentFactionLabel;
    private Label totalCardsInDeck;
    private Label numberOfUnitCards;
    private Label numberOfSpecialCards;
    private Label totalUnitCardStrength;
    private Label heroCards;

    public PreGameMenuScreen() {
        controller = new PreGameMenuController();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        selectedCards = selectedCardsExtractedByCardNames(User.getLoggedInUser().getDeck()==null?new ArrayList<>():User.getLoggedInUser().getDeck());
        unselectedCards = new ArrayList<>();

        // Load background image
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("backgrounds/main_background.jpg"));

        buttonAndFieldInit();
    }

    private void buttonAndFieldInit() {
        dashboardInit();
        mainTable.add(dashboard).center().expand().fill();
    }

    private List<AbstractCard> selectedCardsExtractedByCardNames (ArrayList<String> deck) {
        List<AbstractCard> selectedCards = new ArrayList<>();
        for (String cardName : deck) {
            selectedCards.add(AllCards.getCardByCardName(cardName));
        }
        return selectedCards;
    }

    private void dashboardInit() {
        dashboard = new Table();

        startGameButton = new TextButton("Start Game", Gwent.singleton.skin);
        startGameButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(startGameButton).padBottom(20).center();
        startGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame();
                dispose();
            }
        });
        dashboard.row();

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

        deckButton = new TextButton("Deck", Gwent.singleton.getSkin());
        deckButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(deckButton).width(200).padBottom(20).center().row();
        deckButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                initializeDecks();
                deckWindow.setVisible(true);
            }
        });
        dashboard.row();

        backButton = new TextButton("Back", Gwent.singleton.skin);
        backButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        dashboard.add(backButton).padBottom(20).center();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.gotoMainMenu();
                dispose();
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

    private void saveDeck(){
        User.getLoggedInUser().setDeck(selectedCards);
    }

//    private void selectLeader(Faction faction) {
//        for (AbstractCard card : AllCards.getFactionCardsByFaction(faction)) {
//            if (card instanceof Hero) {
//                selectedCards.add(card);
//                break;
//            }
//        }
//    }

    private void initializeFactionButtons() {
        Table factionButtonTable = new Table();

        selectLeaderButton = new TextButton("Select Leader", Gwent.singleton.skin);
        selectLeaderButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        factionButtonTable.add(selectLeaderButton).center().padBottom(20).row();

        selectLeaderButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                showSelectLeaderWindow();
            }
        });


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
        factionWindow.row();
        TextButton backToSelectLeaderButton = new TextButton("Back", Gwent.singleton.skin);
        backToSelectLeaderButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                factionWindow.setVisible(false);
            }
        });
        factionWindow.add(backToSelectLeaderButton).padTop(20).center().row();
    }

    private void showSelectLeaderWindow() {
        Window selectLeaderWindow = new Window("Select Leader", Gwent.singleton.skin);
        selectLeaderWindow.setSize(Gwent.WIDTH , Gwent.HEIGHT );
        selectLeaderWindow.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - selectLeaderWindow.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - selectLeaderWindow.getHeight() / 2
        );
        TextureRegionDrawable windowBackground = new TextureRegionDrawable(new TextureRegion(new Texture("backgrounds/faction_window_background.png")));
        selectLeaderWindow.setBackground(windowBackground);

        Table heroTable = new Table();
        heroTable.defaults().pad(10);

        Faction currentFaction = User.getLoggedInUser().getFaction();
        for (AbstractCard card : CommanderCards.getFactionCardsByFaction(currentFaction)) {
            ImageButton cardButton = createCardButton(card);
            heroTable.add(cardButton).padBottom(10);

            // Create a final reference to the card for use in the listener
            final AbstractCard finalCard = card;

            cardButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    User.getLoggedInUser().setLeader(finalCard); // Use finalCard here
                    selectLeaderWindow.setVisible(false);
                    updateCurrentFactionLabel();
                }
            });
        }


        selectLeaderWindow.add(heroTable).center();
        stage.addActor(selectLeaderWindow);

    }

    private ImageButton createCardButton(AbstractCard card) {
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(card.getAssetName()));
        ImageButton cardButton = new ImageButton(drawable);
        cardButton.setTransform(true);

        // Calculate the new size
        float originalWidth = drawable.getMinWidth();
        float originalHeight = drawable.getMinHeight();
        float scale = 0.7f; // Scale factor of 1.5
        float resizedWidth = originalWidth * scale;
        float resizedHeight = originalHeight * scale;

        // Set the size of the ImageButton and the image within it
        cardButton.setSize(resizedWidth, resizedHeight);
        cardButton.getImageCell().size(resizedWidth, resizedHeight);

        return cardButton;
    }





    private void initializeDecks() {
        deckWindow.clear();
        selectedCards = selectedCardsExtractedByCardNames(User.getLoggedInUser().getDeck()==null?new ArrayList<>():User.getLoggedInUser().getDeck());

        // Add the buttons table in a new table that does not affect the main layout
        Table buttonWrapperTable = new Table();
        buttonsTableInit(buttonWrapperTable);

        deckWindow.add(buttonWrapperTable).expandX().padLeft(30).padTop(80).row();

        Table scrollTable = new Table();

        ScrollPane unselectedScrollPane = new ScrollPane(unselectedCardsTable = new Table());
        ScrollPane selectedScrollPane = new ScrollPane(selectedCardsTable = new Table());

        scrollTable.add(unselectedScrollPane).width(750).height(CARD_HEIGHT * 2).padRight(20);
        scrollTable.add(selectedScrollPane).width(750).height(CARD_HEIGHT * 2).padLeft(20);

        // Add ScrollPanes to the window
        deckWindow.add(scrollTable).expand().fill().row();

        // Add cards to unselected cards table
        addFactionCards();

        // Add cards to selected cards table
        addSelectedCardsToTable(selectedCards, selectedCardsTable, true);

        stage.addActor(deckWindow);
    }

    private void buttonsTableInit(Table buttonsTable) {
        buttonsTable.clear();

        saveDeckButton = new TextButton("Save Deck", Gwent.singleton.getSkin());
        saveDeckButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);

        downloadDeckButton = new TextButton("Download Deck", Gwent.singleton.getSkin());
        downloadDeckButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);

        uploadDeckButton = new TextButton("Upload Deck", Gwent.singleton.getSkin());
        uploadDeckButton.setSize(FIELD_WIDTH, FIELD_HEIGHT);

        buttonsTable.add(downloadDeckButton).padRight(20).left();
        buttonsTable.add(saveDeckButton).padRight(20).center();
        buttonsTable.add(uploadDeckButton).right();

        totalCardsInDeck = new Label("Total Cards in Deck: \n" + selectedCards.size(), Gwent.singleton.skin);
        numberOfUnitCards = new Label("Number of Unit Cards: \n" + getNumberOfUnitCards() + "/22", Gwent.singleton.skin);
        numberOfSpecialCards = new Label("Special Cards: \n" + getNumberOfSpecialCards() + "/10", Gwent.singleton.skin);
        totalUnitCardStrength = new Label("Total Unit Card Strength: \n" + getTotalUnitCardStrength(), Gwent.singleton.skin);
        heroCards = new Label("Hero Cards: \n" + getNumberOfHeroCards(), Gwent.singleton.skin);

        Table infoTable = new Table();
        infoTable.add(totalCardsInDeck).center().padTop(80).padRight(20);
        infoTable.add(numberOfUnitCards).center().padTop(80).padRight(20);
        infoTable.add(numberOfSpecialCards).center().padTop(80).padRight(20);
        infoTable.add(totalUnitCardStrength).center().padTop(80).padRight(20);
        infoTable.add(heroCards).center().padTop(80);

        deckWindow.add(infoTable).center().row();

        saveDeckButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                saveDeck();
                ScreenManager.setPreGameMenuScreen();
            }
        });
        downloadDeckButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                saveDeck();
                controller.downloadDeck();
            }
        });
        uploadDeckButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.uploadDeck();
            }
        });
    }

    private void updateTotalCardsInDeckLabel() {
        totalCardsInDeck.setText("Total Cards in Deck: \n" + selectedCards.size());
        numberOfUnitCards.setText("Number of Unit Cards: \n" + getNumberOfUnitCards() + "/22");
        numberOfSpecialCards.setText("Special Cards: \n" + getNumberOfSpecialCards() + "/10");
        totalUnitCardStrength.setText("Total Unit Card Strength: \n" + getTotalUnitCardStrength());
        heroCards.setText("Hero Cards: \n" + getNumberOfHeroCards());
    }


    private int getNumberOfUnitCards() {
        // TODO: implement this
        return 0;
    }
    private int getNumberOfSpecialCards() {
        int specialCards = 0;
        for (AbstractCard card : selectedCards) {
            if (card.getFaction() == Faction.SPECIAL) {
                specialCards++;
            }
        }
        return specialCards;
    }

    private int getTotalUnitCardStrength() {
        // TODO: implement this
        int totalStrength = 0;
//        for (AbstractCard card : selectedCards) {
//            if (card.getFaction() != Faction.SPECIAL) {
//                totalStrength += card.getStrength();
//            }
//        }
        return totalStrength;
    }

    private int getNumberOfHeroCards() {
        int heroCards = 0;
        for (AbstractCard card : selectedCards) {
            if (card instanceof Hero) {
                heroCards++;
            }
        }
        return heroCards;
    }


    private void addFactionCards() {
        List<AbstractCard> factionCards = AllCards.getFactionCardsByFaction(User.getLoggedInUser().getFaction());
        List<AbstractCard> neutralCards = AllCards.getNeutralCards();
        List<AbstractCard> specialCards = AllCards.getSpecialCards();
        List<AbstractCard> weatherCards = AllCards.getWeatherCards();

        // Add faction cards
        for (AbstractCard card : factionCards) {
            if (!selectedCards.contains(card)) {
                unselectedCards.add(card);
            }
        }

        // Add neutral cards
        for (AbstractCard card : neutralCards) {
            if (!selectedCards.contains(card)) {
                unselectedCards.add(card);
            }
        }

        // Add special cards
        for (AbstractCard card : specialCards) {
            if (!selectedCards.contains(card)) {
                unselectedCards.add(card);
            }
        }

        // Add weather cards
        for (AbstractCard card : weatherCards) {
            if (!selectedCards.contains(card)) {
                unselectedCards.add(card);
            }
        }

        // Add cards to table
        addUnselectedCardsToTable(unselectedCards, unselectedCardsTable, false);
    }

    private void addUnselectedCardsToTable(List<AbstractCard> cards, Table table, boolean isSelected) {
        int columnCounter = 0;
        for (AbstractCard card : cards) {
            if (columnCounter == 3) {
                table.row();
                columnCounter = 0;
            }
            addCardToTable(card, table, isSelected);
            columnCounter++;
        }
    }

    private void addSelectedCardsToTable(List<AbstractCard> cards, Table table, boolean isSelected) {
        int columnCounter = 0;
        for (AbstractCard card : cards) {
            if (columnCounter == 3) {
                table.row();
                columnCounter = 0;
            }
            addCardToTable(card, table, isSelected);
            columnCounter++;
        }
    }

    private void addCardToTable(AbstractCard card, Table table, boolean isSelected) {
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(card.getAssetName()));
        ImageButton cardButton = new ImageButton(drawable);
        cardButton.setTransform(true);

        float originalWidth = drawable.getMinWidth();
        float originalHeight = drawable.getMinHeight();

        float scale = Math.min(CARD_WIDTH / originalWidth, CARD_HEIGHT / originalHeight);
        cardButton.getImageCell().size(originalWidth * scale, originalHeight * scale);

        cardButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (isSelected) {
                    selectedCards.remove(card);
                    unselectedCards.add(card);
                } else {
                    selectedCards.add(card);
                    unselectedCards.remove(card);
                }
                updateCardPositions();
                updateTotalCardsInDeckLabel();
            }
        });

        table.add(cardButton).pad(10).size(CARD_WIDTH, CARD_HEIGHT);
    }

    private void updateCardPositions() {
        unselectedCardsTable.clearChildren();
        selectedCardsTable.clearChildren();
        addUnselectedCardsToTable(unselectedCards, unselectedCardsTable, false);
        addSelectedCardsToTable(selectedCards, selectedCardsTable, true);
    }


    private void updateCurrentFactionLabel() {
        currentFactionLabel.setText("Current Faction: " + User.getLoggedInUser().getFaction());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gwent.WIDTH, Gwent.HEIGHT);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
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
