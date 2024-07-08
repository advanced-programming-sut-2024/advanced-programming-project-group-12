package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.ChatController;
import com.mygdx.game.controller.local.GameController;
import com.mygdx.game.model.game.card.*;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.card.Action;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.actors.*;

import java.util.*;
import java.util.List;

public class GameScreen implements Screen {
    private final Stage stage;
    private final Texture background;

    //Buttons for veto, pass round, end round, end game
    private TextButton passButton;
    private final GameController controller;
    // info boxes and Actor
    private WeatherBox weatherBox;
    private HandTable hand;
    private ArrayList<RowTable> playerRows;
    private ArrayList<RowTable> enemyRows;

    private Container<Actor> selectedCardPlace;
    private PlayerInfoBox playerInfoBox;
    private PlayerInfoBox oppositionInfoBox;
    private Container playerDiscards;
    private Container oppositionDiscards;
    private Player player;
    private Player opposition;
    //chat parts
    private ChatBox chatBox;
    private TextButton chatButton;

    public GameScreen() {
        controller = new GameController();
        stage = new Stage(new ScreenViewport());
        background = new Texture("bg/board.jpg");
        if (Client.getInstance().getGame().getCurrentPlayer().getUsername().equals(Client.getInstance().getUser().getUsername())) {
            player = Client.getInstance().getGame().getCurrentPlayer();
            opposition = Client.getInstance().getGame().getOpposition();
        } else {
            player = Client.getInstance().getGame().getOpposition();
            opposition = Client.getInstance().getGame().getCurrentPlayer();
        }

        initialStageObjects();

        //TODO : complete this part
        //showCards(, 2);
    }

    public void showChooseStarter() {
        Window window = new Window("", Gwent.singleton.skin);
        window.setSize(900, 700);
        window.setPosition((float) Gwent.WIDTH / 2 - (float) Gwent.WIDTH / 4, (float) Gwent.HEIGHT / 2 - (float) Gwent.HEIGHT / 4);
        Label label = new Label("choose which player start game :", Gwent.singleton.skin);
        label.setColor(Color.SKY);
        window.add(label).padBottom(200).align(Align.center);
        window.row();
        String playerUsername = Client.getInstance().getGame().getCurrentPlayer().getUsername();
        TextButton playerButton = new TextButton(playerUsername, Gwent.singleton.skin);
        playerButton.setSize(200, 100);
        String oppositionUsername = Client.getInstance().getGame().getOpposition().getUsername();
        TextButton oppositionButton = new TextButton(oppositionUsername, Gwent.singleton.skin);
        oppositionButton.setSize(200, 100);
        window.setMovable(false);
        window.add(playerButton);
        window.padRight(20);
        window.add(oppositionButton);
        playerButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               controller.chooseWhichPlayerStartFirst(playerUsername);
               if (Client.getInstance().getGame().getCurrentPlayer().getUsername().equals(Client.getInstance().getUser().getUsername())) {
                   player = Client.getInstance().getGame().getCurrentPlayer();
                   opposition = Client.getInstance().getGame().getOpposition();
               } else {
                   player = Client.getInstance().getGame().getOpposition();
                   opposition = Client.getInstance().getGame().getCurrentPlayer();
               }
               updateStage();
               window.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeOut(0.6f)));
               window.remove();
           }
        });
        oppositionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.chooseWhichPlayerStartFirst(oppositionUsername);
                if (Client.getInstance().getGame().getCurrentPlayer().getUsername().equals(Client.getInstance().getUser().getUsername())) {
                    player = Client.getInstance().getGame().getCurrentPlayer();
                    opposition = Client.getInstance().getGame().getOpposition();
                } else {
                    player = Client.getInstance().getGame().getOpposition();
                    opposition = Client.getInstance().getGame().getCurrentPlayer();
                }
                updateStage();
                window.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeOut(0.6f)));
                window.remove();
            }
        });
        stage.addActor(window);
        window.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.6f)));
    }

    public void clearStage() {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof TextButton) continue;
            actor.clear();
            actor.remove();
        }

    }

    private void initialStageObjects() {
        //Buttons
        passButton = new TextButton("Pass", Gwent.singleton.skin);
        passButton.setPosition(220, 120);
        passButton.setSize(150, 80);
        passButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.passRound();
                updateStage();
            }
        });
        chatButton = new TextButton("Chat", Gwent.singleton.skin);
        chatButton.setPosition(1440, 60); // set the position of the chat button
        chatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (chatBox.isVisible()) {
                    chatBox.hide();
                } else {
                    chatBox.show();
                }
            }
        });
        stage.addActor(chatButton);

        // select card area
        selectedCardPlace = new Container<>();
        stage.addActor(selectedCardPlace);

        //weather box
        weatherBox = new WeatherBox();
        displayWeatherBox();
        stage.addActor(weatherBox);

        //chat box
        chatBox = new ChatBox(Gwent.singleton.skin);
        chatBox.setPosition(400, 500); // set the position of the chat box
        chatBox.getSendButton().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String message = chatBox.getInputText();
                if (message.isEmpty()) return true;
                ChatController.sendMessage(Client.getInstance().getUser().getUsername(), message);
                chatBox.clearInput();
                return true;
            }
        });
        stage.addActor(chatBox);

        //discards
        playerDiscards = new Container<Actor>();
        oppositionDiscards = new Container<Actor>();
        playerDiscards.setTouchable(Touchable.enabled);
        oppositionDiscards.setTouchable(Touchable.enabled);
        playerDiscards.setPosition(1290, 97);
        oppositionDiscards.setPosition(1290, 785);
        playerDiscards.setSize(85, 130);
        oppositionDiscards.setSize(85, 130);
        stage.addActor(playerDiscards);
        stage.addActor(oppositionDiscards);
        stage.addActor(passButton);

        //display rows
        displayRows();

        //display infos
        displayInfo();

        //display leader cards
        displayLeaderCard();

        //display hand
        displayHand();

        //display deck stack
        displayPlayerDeckStack(player, 97);
        displayPlayerDeckStack(opposition, 785);

        //display strength
        displayStrengths();
    }

    private void displayRows() {
        playerRows = new ArrayList<>();
        enemyRows = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<PlayableCard> cards = Client.getInstance().getGame().getGameBoard().getRowCards(player, i);
            //TODO : complete path to
            String path = Client.getInstance().getGame().getGameBoard().getWeatherAssetForRow(i);
            RowTable playerRow = new RowTable(i, true, cards, path);
            playerRows.add(playerRow);
            stage.addActor(playerRow);
            playerRow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (controller.getSelectedCard() != null) {
                        if (controller.isAllowedToPlay(controller.getSelectedCard(), playerRow.getSide(), playerRow.getRowNumber())) {
                            playCard(controller.getSelectedCard(), playerRow);
                        }
                    }
                }
            });
        }
        for (int i = 0; i < 3; i++) {
            ArrayList<PlayableCard> cards = Client.getInstance().getGame().getGameBoard().getRowCards(opposition, i);
            String path = Client.getInstance().getGame().getGameBoard().getWeatherAssetForRow(i);
            RowTable enemyRow = new RowTable(i, false, cards, path);
            enemyRows.add(enemyRow);
            stage.addActor(enemyRow);
            enemyRow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (controller.getSelectedCard() != null) {
                        if (controller.isAllowedToPlay(controller.getSelectedCard(), enemyRow.getSide(), enemyRow.getRowNumber())) {
                            playCard(controller.getSelectedCard(), enemyRow);
                        }
                    }
                }
            });
        }
    }

    private void displayWeatherBox() {
        HashSet<SpellCard> cards = Client.getInstance().getGame().getGameBoard().getWeatherCards();
        for(SpellCard card : cards) {
            addCardToWeatherBox(new CardActor(card));
        }
        weatherBox.setTouchable(Touchable.enabled);
        weatherBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AbstractCard selectedCard = controller.getSelectedCard();
                if (selectedCard != null && selectedCard.getFaction().equals(Faction.WEATHER)) {
                    controller.playCard(selectedCard, 3);
                    selectedCardPlace.clear();
                    controller.setSelectedCard(null);
                    resetBackgroundColors();
                    updateStage();
                }
            }
        });
    }

    private void displayInfo() {
        playerInfoBox = new PlayerInfoBox(player.getHandAsCards().size(), player.getUsername(),
                player.getFaction().toString(), player.getHealth());
        stage.addActor(playerInfoBox.getInfoTable());
        oppositionInfoBox = new PlayerInfoBox(opposition.getHandAsCards().size(), opposition.getUsername(),
                opposition.getFaction().toString(), player.getHealth());
        stage.addActor(oppositionInfoBox.getInfoTable());
        playerInfoBox.setPosition(50, 260);
        oppositionInfoBox.setPosition(50, 610);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
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

    }

    public GameController getController() {
        return controller;
    }

    public void displayLeaderCard() {
        InfoCardActor leaderCard = new InfoCardActor(player.getLeader());
        leaderCard.getImage().setWidth((float) (leaderCard.getWidth() * 1.15));
        leaderCard.getImage().setPosition(115, 100);
        stage.addActor(leaderCard.getImage());
        InfoCardActor oppositeLeaderCard = new InfoCardActor(opposition.getLeader());
        oppositeLeaderCard.getImage().setWidth((float) (oppositeLeaderCard.getWidth() * 1.15));
        oppositeLeaderCard.getImage().setPosition(115, 780);
        stage.addActor(oppositeLeaderCard.getImage());
        leaderCard.getImage().setTouchable(Touchable.enabled);
        leaderCard.getImage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLeaderCard(leaderCard.getCard(), true);
            }
        });

        oppositeLeaderCard.getImage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLeaderCard(oppositeLeaderCard.getCard(), false);
            }
        });
    }

    private void showLeaderCard(AbstractCard card, boolean isPlayerLeader) {
        // Create the blur effect
        Image blurEffect = new Image(new Texture("bg/Blur-Effect.png")); // Replace with the path to your blur effect
        blurEffect.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blurEffect.setColor(new Color(0, 0, 0, 0)); // Start with fully transparent black

        // Create the enlarged card
        InfoCardActor enlargedCard = new InfoCardActor(card);
        enlargedCard.getImage().setSize(0, 0); // Start with size 0
        enlargedCard.getImage().setPosition(700, 450);

        TextButton playButton = new TextButton("play", Gwent.singleton.skin);
        // Create the close button
        TextButton closeButton = new TextButton("X", Gwent.singleton.skin);
        closeButton.setSize(75, 75); // Adjust the size as needed
        closeButton.setPosition(enlargedCard.getImage().getX() + 300 - closeButton.getWidth(),
                enlargedCard.getImage().getY() + 450 - closeButton.getHeight());
        // Disable all other actors on the stage
        for (Actor actor : stage.getActors()) {
            actor.setTouchable(Touchable.disabled);
        }

        // Add the blur effect, the enlarged card, and the close button to the stage
        stage.addActor(blurEffect);
        stage.addActor(enlargedCard.getImage());
        stage.addActor(closeButton);

        // Enable the blur effect, the enlarged card, and the close button
        blurEffect.setTouchable(Touchable.enabled);
        enlargedCard.getImage().setTouchable(Touchable.enabled);
        closeButton.setTouchable(Touchable.enabled);
        // Add a ClickListener to the close button
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Add fade-out animations
                blurEffect.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        // Remove the blur effect from the stage after the fade-out animation completes
                        blurEffect.remove();
                    }
                })));
                enlargedCard.getImage().addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        // Remove the enlarged card from the stage after the fade-out animation completes
                        enlargedCard.getImage().remove();
                    }
                })));

                // Remove the close button from the stage immediately
                closeButton.remove();
                if (controller.getPermission())
                    playButton.remove();
                // Re-enable all other actors on the stage
                for (Actor actor : stage.getActors()) {
                    actor.setTouchable(Touchable.enabled);
                }
            }
        });

        //play button
        if (controller.getPermission() && isPlayerLeader) {
            //create play button for leader card
            playButton.setSize(200, 100);
            playButton.setPosition(enlargedCard.getImage().getX() + 60, enlargedCard.getImage().getY() - playButton.getHeight());
            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(((CommanderCard)card).HasPlayedAction()) {
                        //TODO : don't let him play and show error
                        return;
                    }
                    controller.playCard(player.getLeader(), -1);
                    // Add fade-out animations
                    blurEffect.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            // Remove the blur effect from the stage after the fade-out animation completes
                            blurEffect.remove();
                        }
                    })));
                    enlargedCard.getImage().addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            // Remove the enlarged card from the stage after the fade-out animation completes
                            enlargedCard.getImage().remove();
                        }
                    })));

                    // Remove the close button from the stage immediately
                    closeButton.remove();
                    playButton.remove();
                    // Re-enable all other actors on the stage
                    for (Actor actor : stage.getActors()) {
                        actor.setTouchable(Touchable.enabled);
                    }
                }
            });
            stage.addActor(playButton);
            playButton.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.7f)));
        }

        // Add animations
        closeButton.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.7f)));
        blurEffect.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f))); // Fade in over 0.5 seconds
        enlargedCard.getImage().addAction(Actions.sizeTo(300, 450, 0.5f)); // Grow to size 300x450 over 0.5 seconds
    }


    public void displayHand() {

        LinkedList<AbstractCard> handCards = player.getHandAsCards();
        hand = new HandTable(handCards);
        hand.addToStageAndAddListener(stage);
        for (CardActor cardActor : hand.getCards()) {
            AbstractCard card = cardActor.getCard();
            cardActor.getCardTable().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AbstractCard selectedCard = controller.getSelectedCard();
                    if (selectedCard != null && selectedCard.equals(card)) {
                        // If the clicked card is already the selected card, unselect it
                        controller.setSelectedCard(null);
                        selectedCardPlace.setActor(null);
                        resetBackgroundColors();
                    } else {
                        // Otherwise, select the clicked card
                        controller.setSelectedCard(card);
                        selectedCardPlace.setActor(null);
                        InfoCardActor showCard = new InfoCardActor(card);
                        selectedCardPlace.setActor(showCard);
                        showCard.setSize(200, 300);
                        showCard.setPosition(1300, 400);
                        selectedCardPlace.setPosition(1300, 400);

                        resetBackgroundColors();
                        highlightAllowablePlaces(card);
                    }
                }
            });
        }
    }


    private void playCard(AbstractCard card, RowTable row) {
        // Get the card from the CardActor

        controller.playCard(card, row.getRowNumber());

        // Unselect the card
        resetBackgroundColors();
        updateStage();
    }

    private void addCardToWeatherBox(CardActor card) {
        weatherBox.add(card.getCardTable()).size(80, 110).expand().fill();
    }

    private void highlightAllowablePlaces(AbstractCard card) {
        if (card.getAllowableRows() == null) return;
        List<Integer> allowableRows = card.getAllowableRows();

        if (allowableRows.contains(3)) {
            weatherBox.highlight();
        }
        // Check if the card is a horn card
        if (controller.isHorn(card)) {
            // If it is, highlight the horn areas of the allowable rows
            for (RowTable row : playerRows) {
                if (allowableRows.contains(row.getRowNumber())) {
                    row.getHornArea().highlight();
                }
            }
        } else if (card.getAction().equals(Action.SPY)) {
            for (RowTable row : enemyRows) {
                if (allowableRows.contains(row.getRowNumber())) {
                    row.highlight();
                }
            }
        } else {
            for (RowTable row : playerRows) {
                if (allowableRows.contains(row.getRowNumber())) {
                    row.highlight();
                }
            }
        }
    }

    private void resetBackgroundColors() {
        for (RowTable row : playerRows) {
            row.unhighlight();
        }

        for (RowTable row : enemyRows) {
            row.unhighlight();
        }
        weatherBox.unhighlight();

    }

    private void displayPlayerDeckStack(Player player, float y) {
        final int MAX_VISIBLE_CARDS = 5; // Maximum number of cards to display in the stack
        final float CARD_OFFSET = 3f; // Offset for each card in the stack

        Texture cardBackTexture = new Texture(player.getFaction().getAssetFileName());
        int deckSize = player.getDeck().size();
        int cardsToShow = Math.min(deckSize, MAX_VISIBLE_CARDS);

        for (int i = 0; i < cardsToShow; i++) {
            Image cardImage = new Image(cardBackTexture);
            cardImage.setSize(90, 130);
            // Adjust the position for each card to create a stack effect
            cardImage.setPosition(1440 - (i * CARD_OFFSET), y + (i * CARD_OFFSET));
            stage.addActor(cardImage);
        }

        // Display the number of cards left in the deck
        Label numberOfCards = new Label("Cards: " + deckSize, Gwent.singleton.skin);
        numberOfCards.setColor(Color.ORANGE);
        // Position the label above or below the stack
        numberOfCards.setPosition(1440 + 45 - numberOfCards.getWidth() / 2, y - 60);
        stage.addActor(numberOfCards);
    }

    public void addCardToDiscard(boolean side, AbstractCard card) {
        CardActor cardActor = new CardActor(card);
        if (side) {
            playerDiscards.clear();
            playerDiscards.setActor(cardActor);
            playerDiscards.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showCardsToSelect(Client.getInstance().getGame().getGameBoard().getDiscardCards(player), -1);
                }
            });
            cardActor.setPosition(1290, 97);
        } else {
            oppositionDiscards.clear();
            oppositionDiscards.setActor(cardActor);

            oppositionDiscards.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showCardsToSelect(Client.getInstance().getGame().getGameBoard().getDiscardCards(opposition), -1);
                }
            });
            cardActor.setPosition(1290, 785);
        }

    }

    public void showCardsToSelect(List<? extends AbstractCard> cards, int numberOfCards) {
        ArrayList<AbstractCard> selectedCards = new ArrayList<>();
        Image bgImage = new Image(new Texture("bg/black.jpg"));
        bgImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(bgImage);
        ArrayList<Image> cardImages = new ArrayList<>();

        for (Actor actor : stage.getActors()) {
            actor.setTouchable(Touchable.disabled);
        }
        TextButton closeButton = new TextButton("X", Gwent.singleton.skin);
        closeButton.setSize(80, 80);
        closeButton.setPosition(Gdx.graphics.getWidth() - closeButton.getWidth(), Gdx.graphics.getHeight() - closeButton.getHeight());
        stage.addActor(closeButton);
        float x = 200;
        float y = 700;
        for (int i = 0; i < cards.size(); i++) {
            AbstractCard card = cards.get(i);
            Texture texture = new Texture(card.getAssetName());
            Image cardImage = new Image(texture);
            cardImage.setSize(160, 240);
            cardImage.setPosition(x, y);
            stage.addActor(cardImage);
            cardImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
            cardImages.add(cardImage);
            x += 180;
            if (i > 0 && i % 6 == 0) {
                y -= 280;
                x = 200;
            }
            if (numberOfCards > 0) {
                cardImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (selectedCards.contains(card)) {
                            cardImage.addAction(Actions.scaleTo(1.0f, 1.0f, 0.2f));
                            selectedCards.remove(card);
                            System.out.println(selectedCards.size());
                        } else {
                            cardImage.addAction(Actions.scaleBy(0.1f, 0.1f, 0.2f));
                            selectedCards.add(card);
                        }
                        if (numberOfCards == selectedCards.size()) {
                            for (Image cardImage : cardImages) {
                                cardImage.remove();
                            }
                            for (Actor actor : stage.getActors()) {
                                actor.setTouchable(Touchable.enabled);
                            }
                            bgImage.remove();
                            closeButton.remove();
                            controller.chooseCardInSelectCardMode(selectedCards);
                        }
                    }
                });
            }
        }
        // Add a ClickListener to the close button
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Image cardImage : cardImages) {
                    cardImage.remove();
                }
                for (Actor actor : stage.getActors()) {
                    actor.setTouchable(Touchable.enabled);
                }
                bgImage.remove();
                closeButton.remove();
                controller.chooseCardInSelectCardMode(selectedCards);
            }
        });
    }

    private void updateStage() {
        stage.clear();
        initialStageObjects();
    }
    public void endGame(int state) {
        stage.clear();
        Image endGameBackground = new Image(new Texture("bg/end-game.jpg"));
        endGameBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(endGameBackground);

        /*
        win : 1
        draw : 0
        lose : -1
         */
        Image status;
                switch (state) {
            case 1 :
                status = new Image(new Texture("icons/end_win.png"));
            break;
            case 0 :
                status = new Image(new Texture("icons/end_draw.png"));
            break;
            case -1 :
                status = new Image(new Texture("icons/end_lose.png"));
            break;
            default :
                status = new Image(new Texture("icons/"));
        };
        status.setSize(800, 600);
        status.setPosition(400, 400);
        stage.addActor(status);
        status.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.8f)));
        Table endGameInfo = new Table();
        endGameInfo.add(new Label("players", Gwent.singleton.skin)).align(Align.center).padLeft(40);

        for(int i = 1; i <= 3; i++) {
            endGameInfo.add(new Label("Round " + i, Gwent.singleton.skin)).align(Align.center).padLeft(40);
        }
        endGameInfo.row().padTop(50);
        endGameInfo.add(new Label(player.getUsername(), Gwent.singleton.skin)).align(Align.center).padLeft(40);
        for(int i = 1; i <= 3; i++) {
            Label label = new Label("hichi", Gwent.singleton.skin);
            label.setColor(Color.GOLD);
            endGameInfo.add(label).align(Align.center).padLeft(40);
        }
        endGameInfo.row().padTop(40);
        endGameInfo.add(new Label(opposition.getUsername(), Gwent.singleton.skin)).align(Align.center).padLeft(40);
        for(int i = 1; i <= 3; i++) {
            Label label = new Label("hichi", Gwent.singleton.skin);
            label.setColor(Color.LIGHT_GRAY);
            endGameInfo.add(label).align(Align.center).padLeft(40);
        }
        endGameInfo.row().padBottom(40);
        endGameInfo.setBounds(600, 150, 400, 300);
        stage.addActor(endGameInfo);
        TextButton exitButton = new TextButton("exit", Gwent.singleton.skin);
        exitButton.setSize(300, 120);
        exitButton.setPosition(650, 60);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });
        stage.addActor(exitButton);
    }
    public void showNotification(String message, String pathToAsset) {
        Image image = new Image(new Texture(pathToAsset));
        Label label = new Label(message, Gwent.singleton.skin);
        image.setSize(200, 200);
        image.setPosition(580, 500);
        label.setPosition(750, 550);
        label.setScale(3);

        // Fade in for image
        image.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.6f)));
        // Fade out for image
        image.addAction(Actions.sequence(Actions.delay(1), Actions.fadeOut(0.6f), Actions.removeActor()));

        label.setColor(Color.GOLD);

        // Fade in for label
        label.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.6f)));
        // Fade out for label
        label.addAction(Actions.sequence(Actions.delay(1), Actions.fadeOut(0.6f), Actions.removeActor()));

        stage.addActor(image);
        stage.addActor(label);
    }
    public void displayStrengths() {
        int playerTotalStrength = Client.getInstance().getGame().getGameBoard().getPlayerStrength(player);
        Label playerToatalStrengthLabel = new Label(Integer.toString(playerTotalStrength), Gwent.singleton.skin);
        int oppositionTotalStrength = Client.getInstance().getGame().getGameBoard().getPlayerStrength(opposition);
        Label oppositionToatalStrengthLabel = new Label(Integer.toString(oppositionTotalStrength), Gwent.singleton.skin);
        ArrayList<Label> playerRowsStrengthLabels = new ArrayList<>();
        ArrayList<Label> oppositionRowsStrengthLabels = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            int playerRowStrength = Client.getInstance().getGame().getGameBoard().getRowStrength(player, i);
            int oppositionRowStrength = Client.getInstance().getGame().getGameBoard().getRowStrength(opposition, i);
            playerRowsStrengthLabels.add(new Label(Integer.toString(playerRowStrength), Gwent.singleton.skin));
            oppositionRowsStrengthLabels.add(new Label(Integer.toString(oppositionRowStrength), Gwent.singleton.skin));
        }
        float rowX = 440;
        float midY = (float) Gwent.HEIGHT / 2 + 95;
        for (int i = 0; i < 3; i++) {
            playerRowsStrengthLabels.get(i).setPosition(rowX, midY - (i * 125 + 65));
            playerRowsStrengthLabels.get(i).setScale(2);
            oppositionRowsStrengthLabels.get(i).setPosition(rowX, midY + (i * 125 + 65));
            stage.addActor(playerRowsStrengthLabels.get(i));
            stage.addActor(oppositionRowsStrengthLabels.get(i));
        }
        Image highScoreImage = new Image(new Texture("icons/icon_high_score.png"));
        highScoreImage.setSize(70,70);
        if(playerTotalStrength < oppositionTotalStrength) {
            highScoreImage.setPosition(345, 635);
        } else {
            highScoreImage.setPosition(345, 270);
        }
        stage.addActor(highScoreImage);
        playerToatalStrengthLabel.setScale(3.5f);
        playerToatalStrengthLabel.setPosition(375, 295);
        stage.addActor(playerToatalStrengthLabel);
        oppositionToatalStrengthLabel.setScale(4f);
        oppositionToatalStrengthLabel.setPosition(375, 660);
        stage.addActor(oppositionToatalStrengthLabel);
        
        
    }

    public void endRound() {
        //TODO : show notif
        showNotification("end round", "notif_draw_round");
    }
}
