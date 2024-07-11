package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.ChatController;
import com.mygdx.game.controller.local.GameController;
import com.mygdx.game.model.game.Row;
import com.mygdx.game.model.game.card.*;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.card.Action;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.actors.*;

import java.util.*;
import java.util.List;

public class GameScreen implements Screen {
    //
    private final Stage stage;
    private final Texture background;


    //literally toffffff
    private boolean update = false;
    private boolean showTurn = false;
    private boolean isRoundEnd = false;
    private int endRoundState = -2;
    private boolean opponentPassedRound = false;
    private boolean isGameEnd = false;
    private boolean showReactionWindow = false;
    private int endGameState = -2;
    private Emoji reactedEmoji = null;
    private String reactedMessage = null;
    private boolean oppositionDisconnect = false;
    private boolean oppositionReconnect = false;


    //Notification Queue
    private Queue<Notification> notificationsQueue = new LinkedList<>();
    private boolean isShowingNotification = false;
    private boolean notificationShown = false;

    //Buttons for pass round, end round, end game
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
    private TextButton chatButton;

    public GameScreen() {
        controller = new GameController();
        stage = new Stage(new ScreenViewport());
        new ChatUI(stage, Gwent.singleton.skin, false);
        ChatUI.getInstance().hide();
        background = new Texture("bg/board.jpg");
        if (Client.getInstance().getGame().getCurrentPlayer().getUsername().equals(Client.getInstance().getUser().getUsername())) {
            player = Client.getInstance().getGame().getCurrentPlayer();
            opposition = Client.getInstance().getGame().getOpposition();
        } else {
            player = Client.getInstance().getGame().getOpposition();
            opposition = Client.getInstance().getGame().getCurrentPlayer();
        }

        initialStageObjects();
    }

    public void showChooseStarter() {
        Window window = new Window("", Gwent.singleton.skin);
        window.setSize(800, 500);
        window.setPosition((float) Gwent.WIDTH / 2 - (float) Gwent.WIDTH / 4, (float) Gwent.HEIGHT / 2 - (float) Gwent.HEIGHT / 4);
        Label label = new Label("choose which player start game :", Gwent.singleton.skin);
        label.setColor(Color.SKY);
        window.add(label).padBottom(200).align(Align.center);
        window.row();
        String playerUsername = Client.getInstance().getGame().getCurrentPlayer().getUsername();
        TextButton playerButton = new TextButton(playerUsername, Gwent.singleton.skin);
        playerButton.setSize(400, 200);
        String oppositionUsername = Client.getInstance().getGame().getOpposition().getUsername();
        TextButton oppositionButton = new TextButton(oppositionUsername, Gwent.singleton.skin);
        oppositionButton.setSize(400, 200);
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

                window.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeOut(0.6f)));
                window.remove();
            }
        });
        stage.addActor(window);
        window.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.6f)));
    }

    private void initialStageObjects() {
        //Buttons
        if (controller.getPermission()) {
            passButton = new TextButton("Pass", Gwent.singleton.skin);
            passButton.setPosition(220, 120);
            passButton.setSize(150, 80);
            passButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    notificationsQueue.add(new Notification("Round Passed!", "icons/notif_round_passed.png"));
                    controller.passRound();

                }
            });
            stage.addActor(passButton);
        }
        chatButton = new TextButton("Chat", Gwent.singleton.skin);
        chatButton.setPosition(1440, 300); // set the position of the chat button
        chatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ChatUI.getInstance().isVisible()) {
                    ChatUI.getInstance().hide();
                } else {
                    ChatUI.getInstance().show();
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


        //discards
        playerDiscards = new Container<Actor>();
        oppositionDiscards = new Container<Actor>();
        playerDiscards.setTouchable(Touchable.enabled);
        oppositionDiscards.setTouchable(Touchable.enabled);
        playerDiscards.setPosition(1290, 97);
        oppositionDiscards.setPosition(1290, 785);
        playerDiscards.setSize(85, 130);
        oppositionDiscards.setSize(85, 130);



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
        if (!Client.getInstance().getGame().getGameBoard().getDiscardCards(player).isEmpty()) {

            AbstractCard playerDiscard = Client.getInstance().getGame().getGameBoard().getDiscardCards(player).getLast();
            displayDiscard(true, playerDiscard);
            stage.addActor(playerDiscards);
        }
        if (!Client.getInstance().getGame().getGameBoard().getDiscardCards(opposition).isEmpty()) {
            AbstractCard oppositionDiscard = Client.getInstance().getGame().getGameBoard().getDiscardCards(opposition).getLast();
            displayDiscard(false, oppositionDiscard);
            stage.addActor(oppositionDiscards);
        }

    }

    private void displayRows() {
        playerRows = new ArrayList<>();
        enemyRows = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Row row = Client.getInstance().getGame().getGameBoard().getRowForPlayer(i, player);
            RowTable playerRow = new RowTable(row,true, i);
            playerRows.add(playerRow);
            stage.addActor(playerRow);
            int finalI = i;
            playerRow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (controller.getSelectedCard() != null && controller.getPermission()) {
                        if(controller.getSelectedCard().equals(AllCards.DECOY.getAbstractCard())) {
                            if(Client.getInstance().getGame().getGameBoard().getRowCards(player, finalI).isEmpty()) return;
                            showCardsToSelect(Client.getInstance().getGame().getGameBoard().getRowCards(player, finalI), finalI, false);
                        }else if (controller.isAllowedToPlay(controller.getSelectedCard(), playerRow.getSide(), playerRow.getRowNumber())) {
                            playCard(controller.getSelectedCard(), playerRow);
                            controller.setSelectedCard(null);
                            resetBackgroundColors();
                        }
                    }
                }
            });
        }
        for (int i = 0; i < 3; i++) {
            Row row = Client.getInstance().getGame().getGameBoard().getRowForPlayer(i, opposition);
            RowTable oppositionRow = new RowTable(row,false, i);
            enemyRows.add(oppositionRow);
            stage.addActor(oppositionRow);
            oppositionRow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (controller.getSelectedCard() != null && controller.getPermission()) {
                        if (controller.isAllowedToPlay(controller.getSelectedCard(), oppositionRow.getSide(), oppositionRow.getRowNumber())) {
                            playCard(controller.getSelectedCard(), oppositionRow);
                            selectedCardPlace.clear();
                            controller.setSelectedCard(null);
                            resetBackgroundColors();
                        }
                    }
                }
            });
        }
    }

    private void displayWeatherBox() {
        HashSet<SpellCard> cards = Client.getInstance().getGame().getGameBoard().getWeatherCards();
        for (SpellCard card : cards) {
            weatherBox.addCard(card);
        }
        weatherBox.setTouchable(Touchable.enabled);
        weatherBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AbstractCard selectedCard = controller.getSelectedCard();
                if (selectedCard != null && selectedCard.getFaction().equals(Faction.WEATHER) && controller.getPermission()) {
                    controller.playCard(selectedCard, 3);
                    selectedCardPlace.clear();
                    controller.setSelectedCard(null);
                    resetBackgroundColors();
                }
            }
        });
    }

    private void displayInfo() {
        playerInfoBox = new PlayerInfoBox(player.getHandAsCards().size(), player.getUsername(),
                player.getFaction().toString(), player.getHealth());
        stage.addActor(playerInfoBox.getInfoTable());
        oppositionInfoBox = new PlayerInfoBox(opposition.getHandAsCards().size(), opposition.getUsername(),
                opposition.getFaction().toString(), opposition.getHealth());
        stage.addActor(oppositionInfoBox.getInfoTable());
        playerInfoBox.setPosition(0, 260);
        oppositionInfoBox.setPosition(0, 600);
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
        if(isGameEnd) {
            endGame(endGameState);
        }
        if(controller.isShowSelectCardCalled()) {
            showCardsToSelect(controller.getCardsToShow(), controller.getNumberOfCardsToChoose(), controller.isCanChooseLess());
            controller.setOffShowCardToSelect();
        }
        if(update) {
            this.updateStage();
            update = false;
        }
        if(opponentPassedRound) {
            notificationsQueue.add(new Notification("Opponent Passed Round", "icons/notif_round_passed.png"));
            opponentPassedRound = false;
        }
        if(isRoundEnd) {
            notificationsQueue.add(new Notification("end round", "icons/notif_draw_round.png"));
            showEndRound();
            notificationsQueue.add(new Notification("New Round Started!" , "icons/notif_round_start.png"));
            isRoundEnd = false;
        }
        if(showTurn) {
            if(controller.getPermission()) {
                notificationsQueue.add(new Notification("Your Turn!", "icons/notif_me_turn.png"));
            } else {
                notificationsQueue.add(new Notification("Opponent Turn!", "icons/notif_op_turn.png"));
            }
            showTurn = false;
        }
        if (!notificationsQueue.isEmpty()) {
            if (!isShowingNotification) {
                Notification currentNotification = notificationsQueue.poll();
                showNotification(currentNotification.message, currentNotification.pathToAsset);
                isShowingNotification = true;
            } else {
                // Wait for the current notification to finish showing
                if (notificationShown) {
                    isShowingNotification = false;
                    notificationShown = false;
                }
            }
        }
        if(reactedEmoji != null) {
            showReactionEmoji(reactedEmoji);
            reactedEmoji = null;
        }
        if(reactedMessage != null) {
            showReactionMessage(reactedMessage);
            reactedMessage = null;
        }
        if(showReactionWindow) {
            showReactionWindow();
            showReactionWindow = false;
        }
        if(ChatUI.getInstance().getNewMessage() != null) {
            ChatUI.getInstance().putNewMessage();
        }
        if(oppositionDisconnect) {
            showOpponentDisconnectedMessage();
            oppositionDisconnect = false;
        }
        if(oppositionReconnect) {
            showOpponentReconnectedMessage();
            oppositionReconnect = false;
        }
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
        Image blurEffect = new Image(new Texture("bg/Blur-Effect.png"));// Replace with the path to your blur effect
        blurEffect.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blurEffect.setColor(new Color(1, 1, 1, 0.1f)); // Start with fully transparent black

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
                    if (((CommanderCard) card).HasPlayedAction()) {
                        showError("you already played your commander card");
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
        closeButton.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.7f)));
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

    public void displayDiscard(boolean side, AbstractCard card) {
        if (card == null) return;
        CardActor cardActor = new CardActor(card);
        if (side) {
            playerDiscards.clear();
            playerDiscards.setActor(cardActor);
            playerDiscards.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showCardsToSelect(Client.getInstance().getGame().getGameBoard().getDiscardCards(player), -1, true);
                }
            });
            cardActor.setPosition(1290, 97);
        } else {
            oppositionDiscards.clear();
            oppositionDiscards.setActor(cardActor);

            oppositionDiscards.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showCardsToSelect(Client.getInstance().getGame().getGameBoard().getDiscardCards(opposition), -1, true);
                }
            });
            cardActor.setPosition(1290, 785);
        }

    }

    public void showCardsToSelect(List<? extends AbstractCard> cards, int numberOfCards, boolean canChooseLess) {
        int rowNumber = numberOfCards;
        if(controller.getSelectedCard() != null && controller.getSelectedCard().equals(AllCards.DECOY.getAbstractCard())) {
            System.out.println("i am here");
            numberOfCards = 1;
        }
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
        if(canChooseLess) stage.addActor(closeButton);

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
                int finalNumberOfCards = numberOfCards;
                cardImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (selectedCards.contains(card)) {
                            cardImage.addAction(Actions.scaleTo(1.0f, 1.0f, 0.2f));
                            selectedCards.remove(card);
                        } else {cardImage.addAction(Actions.scaleBy(0.1f, 0.1f, 0.2f));
                            selectedCards.add(card);
                        }
                        if (finalNumberOfCards == selectedCards.size()) {
                            for (Image cardImage : cardImages) {
                                cardImage.remove();
                            }
                            for (Actor actor : stage.getActors()) {
                                actor.setTouchable(Touchable.enabled);
                            }
                            bgImage.remove();
                            closeButton.remove();
                            if(controller.getSelectedCard() != null && controller.getSelectedCard().equals(AllCards.DECOY.getAbstractCard())) {
                                controller.playDecoy(selectedCards.getFirst(), rowNumber);
                            } else controller.chooseCardInSelectCardMode(selectedCards, canChooseLess);
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
                controller.chooseCardInSelectCardMode(selectedCards, canChooseLess);
            }
        });
    }

    private void updateStage() {
        if(controller.getPermission()) {
            System.out.println("player");
            player = Client.getInstance().getGame().getCurrentPlayer();
            opposition = Client.getInstance().getGame().getOpposition();
        } else {
            System.out.println("opposition");
            player = Client.getInstance().getGame().getOpposition();
            opposition = Client.getInstance().getGame().getCurrentPlayer();
        }
        showTurn = true;
        stage.clear();
        initialStageObjects();
        ChatUI.getInstance().returnToStage(stage);
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
        System.out.println(state);
        switch (state) {
            case 1:
                status = new Image(new Texture("icons/end_win.png"));
                break;
            case 0:
                status = new Image(new Texture("icons/end_draw.png"));
                break;
            case -1:
                status = new Image(new Texture("icons/end_lose.png"));
                break;
            default:
                status = new Image(new Texture("icons/end_draw.png"));
        }
        ;
        status.setSize(800, 600);
        status.setPosition(400, 400);
        status.setVisible(true);
        stage.addActor(status);
        status.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.8f)));
        Table endGameInfo = new Table();
        endGameInfo.add(new Label("players", Gwent.singleton.skin)).align(Align.center).padLeft(40);

        for (int i = 0; i < Client.getInstance().getGame().getRounds().size(); i++) {
            endGameInfo.add(new Label("Round " + (i+1), Gwent.singleton.skin)).align(Align.center).padLeft(40);
        }
        endGameInfo.row().padTop(50);
        endGameInfo.add(new Label(player.getUsername(), Gwent.singleton.skin)).align(Align.center).padLeft(40);
        for (int i = 0; i < Client.getInstance().getGame().getRounds().size(); i++) {
            int score = Client.getInstance().getGame().getRounds().get(i).getScoreByUsername(player.getUsername());
            Label label = new Label(Integer.toString(score), Gwent.singleton.skin);
            if(Client.getInstance().getGame().getRounds().get(i).isWinner(player.getUsername())) {
                label.setColor(Color.GOLD);
            } else {
                label.setColor(Color.LIGHT_GRAY);
            }
            endGameInfo.add(label).align(Align.center).padLeft(40);
        }
        endGameInfo.row().padTop(40);
        endGameInfo.add(new Label(opposition.getUsername(), Gwent.singleton.skin)).align(Align.center).padLeft(40);
        for (int i = 0; i < Client.getInstance().getGame().getRounds().size(); i++) {
            int score = Client.getInstance().getGame().getRounds().get(i).getScoreByUsername(opposition.getUsername());
            Label label = new Label(Integer.toString(score), Gwent.singleton.skin);
            if(Client.getInstance().getGame().getRounds().get(i).isWinner(opposition.getUsername())) {
                label.setColor(Color.GOLD);
            } else {
                label.setColor(Color.LIGHT_GRAY);
            }
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
        // Create a Table to hold the notification content
        Table notificationTable = new Table();
        notificationTable.setSize(Gdx.graphics.getWidth(), 200);
        notificationTable.setPosition(0, 450);

        // Create an Image as the background for the notification box
        Image background = new Image(new Texture("bg/Blur-Effect.png"));
        background.setColor(1, 1, 1, 1);
        background.setFillParent(true); // Fill the entire table with the background image
        notificationTable.setBackground(background.getDrawable()); // Set the background image
        //Create Image for image
        Image image = new Image(new Texture(pathToAsset));
        image.setSize(200, 200);

        // Create a Label for the message
        Label label = new Label(message, Gwent.singleton.skin);
        label.setFontScale(3f);
        label.setAlignment(Align.center);
        label.setColor(Color.GOLD);

        // Add the Label to the Table
        notificationTable.add(image).left().expand();
        notificationTable.add(label).center().expand();

        // Add the Table to the Stage
        stage.addActor(notificationTable);

        // Fade in for the notification box
        notificationTable.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(0.6f),
                Actions.delay(1),
                Actions.fadeOut(0.6f),
                Actions.removeActor()
        ));

        notificationTable.addAction(Actions.sequence(Actions.fadeIn(0.6f), Actions.delay(1), Actions.fadeOut(0.6f), Actions.run(() -> notificationShown = true)));

    }

    public void displayStrengths() {
        int playerTotalStrength = Client.getInstance().getGame().getGameBoard().getPlayerStrength(player);
        Label playerToatalStrengthLabel = new Label(Integer.toString(playerTotalStrength), Gwent.singleton.skin);
        int oppositionTotalStrength = Client.getInstance().getGame().getGameBoard().getPlayerStrength(opposition);
        Label oppositionToatalStrengthLabel = new Label(Integer.toString(oppositionTotalStrength), Gwent.singleton.skin);
        ArrayList<Label> playerRowsStrengthLabels = new ArrayList<>();
        ArrayList<Label> oppositionRowsStrengthLabels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
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
        highScoreImage.setSize(70, 70);
        if (playerTotalStrength < oppositionTotalStrength) {
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

    public void endRound(String winner) {
        if(player.getUsername().equals(winner)) {
            endRoundState = 1;
        } else if(opposition.getUsername().equals(winner)) {
            endRoundState = -1;
        } else {
            endRoundState = 0;
        }
        isRoundEnd = true;
    }
    public void showEndRound() {
        if(endRoundState == 1) {
            notificationsQueue.add(new Notification("You Won Round!", "icons/notif_win_round.png"));
        } else if(endRoundState == 0) {
            notificationsQueue.add(new Notification("Round Draw!", "icons/notif_draw_round.png"));
        } else if(endRoundState == -1) {
            notificationsQueue.add(new Notification("You Lost Round!", "icons/notif_lose_round.png"));
        }
    }
    public void setUpdate() {
        this.update = true;
    }
    private void showError(String message) {
        Dialog errorDialog = new Dialog("Error", Gwent.singleton.getSkin());
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);
    }

    public void showOpponentPassedRound() {
        opponentPassedRound = true;
    }
    public void setGameEnd(int state) {
        endGameState = state;
        isGameEnd = true;
    }

    public Stage getStage() {
        return stage;
    }
    public void showReactionEmoji(Emoji emoji) {
        Image reactionImage = new Image(emoji.getTexture());
        reactionImage.setColor(Color.WHITE);
        reactionImage.setSize(200, 200);
        reactionImage.setPosition((float) Gwent.WIDTH /2 - reactionImage.getWidth() / 2,
                (float) Gwent.HEIGHT / 2 - reactionImage.getHeight() / 2);
        reactionImage.addAction(Actions.sequence(
                Actions.fadeIn(0.5f),
                Actions.delay(5f), // wait for 5 seconds
                Actions.fadeOut(0.5f)
        ));
        stage.addActor(reactionImage);
    }
    public void showReactionMessage(String message) {
        showError(message);
    }

    public void showReactionWindow() {
        Window reactionWindow = new Window("Show your reaction!", Gwent.singleton.skin);
        reactionWindow.setBackground(new NinePatchDrawable(new NinePatch(new Texture("bg/Designer.jpeg"))));
        reactionWindow.setSize(500, 400);
        reactionWindow.setPosition((float) Gwent.WIDTH - reactionWindow.getWidth(),
                (float) Gwent.HEIGHT - reactionWindow.getHeight());


        Table table = new Table();
        reactionWindow.add(table).fill().expand();

        // Emoji buttons
        Table emojiTable = new Table();
        for (Emoji emoji : Emoji.values()) {
            ImageButton emojiButton = new ImageButton(new Image(emoji.getTexture()).getDrawable());
            emojiButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ChatController.sendEmojiReaction(emoji);
                    reactionWindow.addAction(Actions.sequence(
                            Actions.fadeOut(0.5f), // fade out the window over 0.5 seconds
                            Actions.removeActor() // remove the window from the stage after fade out
                    ));
                }
            });
            emojiTable.add(emojiButton).size(40, 40).padRight(10);
        }
        table.add(emojiTable).fillX().uniformX().center().row();

        // Message buttons
        Table messageTable = new Table();
        TextButton niceButton = new TextButton("Nice play!", Gwent.singleton.skin);
        niceButton.setScale(0.4f);
        niceButton.getLabel().setFontScale(0.7f);
        niceButton.setColor(Color.GREEN); // lime green color
        niceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChatController.sendMessageReaction("Nice play!");
                reactionWindow.addAction(Actions.sequence(
                        Actions.removeActor() // remove the window from the stage after fade out
                ));
            }
        });
        messageTable.add(niceButton).size(250, 80).padRight(20);

        TextButton tooBadButton = new TextButton("Too bad play!", Gwent.singleton.skin);
        tooBadButton.setScale(0.4f);
        tooBadButton.getLabel().setFontScale(0.7f);
        tooBadButton.setColor(Color.RED); // hot pink color
        tooBadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChatController.sendMessageReaction("Too bad play!");
                reactionWindow.addAction(Actions.sequence(
                        Actions.removeActor() // remove the window from the stage after fade out
                ));
            }
        });
        messageTable.add(tooBadButton).size(250, 80).padRight(20);

        table.add(messageTable).fillX().uniformX().center().row();

        // Short message input
        TextField shortMessage = new TextField("", Gwent.singleton.skin);
        shortMessage.setMessageText("Send your short message:");
        shortMessage.setColor(Color.DARK_GRAY); // white color
        table.add(shortMessage).size(250, 60).padRight(20).center().row();

        // Send button
        TextButton sendReactionButton = new TextButton("Send", Gwent.singleton.skin);
        sendReactionButton.setColor(Color.LIGHT_GRAY);
        sendReactionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChatController.sendMessageReaction(shortMessage.getText());
                reactionWindow.addAction(Actions.sequence(
                        Actions.removeActor() // remove the window from the stage after fade out
                ));
            }
        });
        table.add(sendReactionButton).size(130, 60).center();

        // Add a fade effect to the window
        reactionWindow.addAction(Actions.sequence(
                Actions.delay(4f), // wait for 5 seconds
                Actions.removeActor() // remove the window from the stage after fade out
        ));

        // Show the reaction window
        reactionWindow.setVisible(true);
        stage.addActor(reactionWindow);
    }

    public void setReactedEmoji(Emoji emoji) {
        System.out.println("gere");
        reactedEmoji = emoji;
    }

    public void setReactedMessage(String message) {
        reactedMessage = message;
    }
    public void setShowReactionWindow() {
        showReactionWindow = true;
    }
    public void showOpponentDisconnectedMessage() {
        // Create a label to display the message
        Label messageLabel = new Label("Opposition lost connection!", Gwent.singleton.skin);
        messageLabel.setFontScale(2f);
        messageLabel.setColor(Color.RED);

        // Create a window to display the message
        Window messageWindow = new Window("", Gwent.singleton.skin);
        messageWindow.add(messageLabel);
        messageWindow.pack();
        messageWindow.setPosition((Gdx.graphics.getWidth() - messageWindow.getWidth()) / 2, (Gdx.graphics.getHeight() - messageWindow.getHeight()) / 2);
        messageWindow.setVisible(true);
        stage.addActor(messageWindow);

        // Disable touchability of all actors in the stage
        for (Actor actor : stage.getActors()) {
            actor.setTouchable(Touchable.disabled);
        }

        // Remove the message window after 5 seconds
        messageWindow.addAction(Actions.sequence(
                Actions.delay(5f),
                Actions.removeActor()
        ));
    }
    public void showOpponentReconnectedMessage() {
        // Create a label to display the message
        Label messageLabel = new Label("Opposition is back in the game!", Gwent.singleton.skin);
        messageLabel.setFontScale(2f);
        messageLabel.setColor(Color.GREEN);

        // Create a window to display the message
        Window messageWindow = new Window("", Gwent.singleton.skin);
        messageWindow.add(messageLabel);
        messageWindow.pack();
        messageWindow.setPosition((Gdx.graphics.getWidth() - messageWindow.getWidth()) / 2, (Gdx.graphics.getHeight() - messageWindow.getHeight()) / 2);
        messageWindow.setVisible(true);
        stage.addActor(messageWindow);

        // Enable touchability of all actors in the stage
        for (Actor actor : stage.getActors()) {
            actor.setTouchable(Touchable.enabled);
        }

        // Remove the message window after 5 seconds
        messageWindow.addAction(Actions.sequence(
                Actions.delay(5f),
                Actions.removeActor()
        ));
    }
    public void setOppositionDisconnect() {
        oppositionDisconnect = true;
    }
    public void setOppositionReconnect() {
        oppositionReconnect = true;
    }
}
