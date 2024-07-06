package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.ChatController;
import com.mygdx.game.controller.local.GameController;
import com.mygdx.game.model.game.card.Action;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.actors.*;
import com.mygdx.game.model.game.card.AbstractCard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameScreen implements Screen {
    private final Stage stage;
    private final Texture background;
    private GameState gameState = GameState.NORMAL;
    //Buttons for veto, pass round, end round, end game
    private final TextButton passButton;
    private final GameController controller;
    // info boxes and Actor
    private final WeatherBox weatherBox;
    private HandTable hand;
    private ArrayList<RowTable> playerRows;
    private ArrayList<RowTable> enemyRows;
    private CardActor selectedCardActor;
    private PlayerInfoBox playerInfoBox;
    private PlayerInfoBox oppositionInfoBox;
    private Table playerDiscards;
    private Table opposiytionDiscards;
    private Player player;
    private Player opposition;
    //chat parts
    private ChatBox chatBox;
    private TextButton chatButton;

    public GameScreen() {
        if(Client.getInstance().getGame().getCurrentPlayer().getUsername().equals(Client.getInstance().getUser().getUsername())) {
            player = Client.getInstance().getGame().getCurrentPlayer();
            opposition = Client.getInstance().getGame().getOpposition();
        } else {
            player = Client.getInstance().getGame().getOpposition();
            opposition = Client.getInstance().getGame().getCurrentPlayer();
        }

        stage = new Stage();
        background = new Texture("bg/board.jpg");
        passButton = new TextButton("Pass", Gwent.singleton.skin);
        passButton.setPosition(220, 120);
        passButton.setSize(150, 80);
        weatherBox = new WeatherBox();

        chatBox = new ChatBox(Gwent.singleton.skin);
        chatBox.setPosition(400, 500); // set the position of the chat box
        stage.addActor(chatBox);

        chatButton = new TextButton("Chat", Gwent.singleton.skin);
        chatButton.setPosition(1440, 60); // set the position of the chat button
        stage.addActor(chatButton);

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


        playerDiscards = new Table(Gwent.singleton.skin);
        playerDiscards.setTouchable(Touchable.enabled);
        opposiytionDiscards = new Table(Gwent.singleton.skin);
        playerDiscards.setTouchable(Touchable.enabled);
        stage.addActor(weatherBox);
        weatherBoxListener();
        stage.addActor(passButton);
        controller = new GameController();
        initialRows();
        displayInfo();

        displayLeaderCard();
        displayHand();
        displayPlayerDeckStack(player, 97);
        displayPlayerDeckStack(opposition, 785);
        Gdx.input.setInputProcessor(stage);
    }

    private void initialRows() {
        playerRows = new ArrayList<>();
        enemyRows = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RowTable playerRow = new RowTable(i, true);
            playerRows.add(playerRow);
            stage.addActor(playerRow);
            playerRow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                   if (controller.getSelectedCard() != null) {
                        if (controller.isAllowedToPlay(controller.getSelectedCard(), playerRow.getSide(), playerRow.getRowNumber())) {
                            playCard(selectedCardActor, playerRow);
                        }
                   }
                }
            });
        }
        for (int i = 0; i < 3; i++) {
            RowTable enemyRow = new RowTable(i, false);
            enemyRows.add(enemyRow);
            stage.addActor(enemyRow);
            enemyRow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (controller.getSelectedCard() != null) {
                        if (controller.isAllowedToPlay(controller.getSelectedCard(), enemyRow.getSide(), enemyRow.getRowNumber())) {
                            playCard(selectedCardActor, enemyRow);
                        }
                    }
                }
            });
        }
    }

    private void weatherBoxListener() {
        weatherBox.setTouchable(Touchable.enabled);
        weatherBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AbstractCard selectedCard = controller.getSelectedCard();
                if (selectedCard != null && selectedCard.getFaction().equals(Faction.WEATHER)) {
                    playWeatherCard(selectedCardActor);
                    // Add the card to the weather box
                    // Remove the card from the player's hand
                    controller.playCard(selectedCard, 3);
                    // Unselect the card
                    if (selectedCardActor != null) {
                        selectedCardActor.remove();
                        selectedCardActor = null;
                    }
                    // Redraw the player's hand
                    resetBackgroundColors();
                    hand.clear();
                    displayHand();
                }
            }
        });
    }

    private void displayInfo() {
        playerInfoBox = new PlayerInfoBox(player.getHandAsCards().size(), player.getUsername(),
                player.getFaction().toString());
        stage.addActor(playerInfoBox.getInfoTable());
        oppositionInfoBox = new PlayerInfoBox(opposition.getHandAsCards().size(), opposition.getUsername(),
                opposition.getFaction().toString());
        stage.addActor(oppositionInfoBox.getInfoTable());
        playerInfoBox.setPosition(50, 260);
        oppositionInfoBox.setPosition(50, 610);
    }

    @Override
    public void show() {
        passButton.setVisible(true);
        passButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.passRound();
            }
        });
        chatBox.getSendButton().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String message = chatBox.getInputText();
                if(message.isEmpty()) return true;
                ChatController.sendMessage(Client.getInstance().getUser().getUsername(), message);
                chatBox.clearInput();
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        // Check the current game state
        switch(gameState) {
            case NORMAL:
                renderNormalState(delta);
                break;
            case SHOW_CARD:
                renderShowingCardsState(delta);
                break;
        }
    }
    private void renderNormalState(float delta) {
        // Render the game normally
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
        // Add your normal game rendering logic here
    }

    private void renderShowingCardsState(float delta) {
        // Example:
        blurScreen(); // Add your blur effect here
    }
    private void blurScreen() {
        // Create a new FrameBuffer for rendering the blurred screen
        FrameBuffer blurBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        // Begin rendering to the FrameBuffer
        blurBuffer.begin();

        // Render the game screen to the FrameBuffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        blurBuffer.end();


        // Draw the blurred texture onto the screen
        TextureRegion blurredRegion = new TextureRegion(blurBuffer.getColorBufferTexture());
        blurredRegion.flip(false, true);
        Image blurredImage = new Image(blurredRegion);
        stage.addActor(blurredImage);
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
        LeaderActor leaderCard = new LeaderActor(player.getLeader());
        leaderCard.getImage().setWidth((float) (leaderCard.getWidth() * 1.15));
        leaderCard.getImage().setPosition(115, 100);
        stage.addActor(leaderCard.getImage());
        LeaderActor oppositeLeaderCard = new LeaderActor(opposition.getLeader());
        oppositeLeaderCard.getImage().setWidth((float) (oppositeLeaderCard.getWidth() * 1.15));
        oppositeLeaderCard.getImage().setPosition(115, 780);
        stage.addActor(oppositeLeaderCard.getImage());
        leaderCard.getImage().setTouchable(Touchable.enabled);
        leaderCard.getImage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLeaderCard(leaderCard.getCard());
            }
        });
        oppositeLeaderCard.getImage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLeaderCard(oppositeLeaderCard.getCard());
            }
        });
    }

    private void showLeaderCard(AbstractCard card) {
        // Create the blur effect
        Image blurEffect = new Image(new Texture("bg/Blur-Effect.png")); // Replace with the path to your blur effect
        blurEffect.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blurEffect.setColor(new Color(0, 0, 0, 0)); // Start with fully transparent black

        // Create the enlarged card
        LeaderActor enlargedCard = new LeaderActor(card);
        enlargedCard.getImage().setSize(0, 0); // Start with size 0
        enlargedCard.getImage().setPosition(700, 450);

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

                // Re-enable all other actors on the stage
                for (Actor actor : stage.getActors()) {
                    actor.setTouchable(Touchable.enabled);
                }
            }
        });

        // Add animations
        blurEffect.addAction(Actions.fadeIn(0.5f)); // Fade in over 0.5 seconds
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
                        if (selectedCardActor != null) {
                            selectedCardActor.remove(); // Remove the currently selected card actor from the stage
                            selectedCardActor = null;
                        }
                        resetBackgroundColors();
                    } else {
                        // Otherwise, select the clicked card
                        controller.setSelectedCard(card);
                        if (selectedCardActor != null) {
                            selectedCardActor.remove(); // Remove the currently selected card actor from the stage
                        }
                        selectedCardActor = new CardActor(card);
                        selectedCardActor.getCardTable().setSize(200, 300);
                        selectedCardActor.getCardTable().setPosition(1300, 400);
                        stage.addActor(selectedCardActor.getCardTable());
                        resetBackgroundColors();
                        highlightAllowablePlaces(card);
                    }
                }
            });
        }
    }


    private void playCard(CardActor cardActor, RowTable row) {
        // Get the card from the CardActor
        AbstractCard card = cardActor.getCard();

        controller.playCard(card, row.getRowNumber());

        // Unselect the card
        controller.setSelectedCard(null);
        if (selectedCardActor != null) {
            selectedCardActor.remove();
            selectedCardActor = null;
        }
        resetBackgroundColors();
        hand.clear();
        // Redraw the player's hand
        displayHand();

        playerInfoBox.updatePlayerInfo(player.getHandAsCards().size());
    }

    private void playWeatherCard(CardActor card) {
        weatherBox.add(selectedCardActor.getCardTable()).size(80, 110).expand().fill();
    }

    private void highlightAllowablePlaces(AbstractCard card) {
        if (card.getAllowableRows() == null) return;
        List<Integer> allowableRows = card.getAllowableRows();

        if(allowableRows.contains(3)) {
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
    public void putCardToDiscard(boolean side, CardActor cardActor) {
        if(side) {
            playerDiscards.clear();
            playerDiscards.addActor(cardActor);
            playerDiscards.addListener(new ClickListener() {
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   showCards(player.getGame().getGameBoard().getDiscardCards(player), false);
               }
            });
        } else {
            opposiytionDiscards.clear();
            opposiytionDiscards.addActor(cardActor);
            opposiytionDiscards.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showCards(player.getGame().getGameBoard().getDiscardCards(opposition), false);
                }
            });
        }
    }

    private void showCards(ArrayList<AbstractCard> cards, boolean touch) {
        TextButton closeButton = new TextButton("X", Gwent.singleton.skin);
        closeButton.setSize(80, 80);
        closeButton.setPosition(Gdx.graphics.getWidth() - closeButton.getWidth(), Gdx.graphics.getHeight() - closeButton.getHeight());

        float x = 200;
        float y = 800;
        for(int i = 0; i < cards.size(); i++) {
            Texture texture = new Texture(cards.get(i).getAssetName());
            Image cardImage = new Image(texture);
            cardImage.setSize(80, 120);
            cardImage.setPosition(x, y);
            x += 100;
            if(i % 4 == 0) {
                y -= 150;
                x = 200;
            }
            if(touch) {
                cardImage.addListener(new ClickListener() {
                   @Override
                   public void clicked(InputEvent event, float x, float y) {
                       //TODO
                   }
                });
            }
        }
        // Add a ClickListener to the close button
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO : close screen

                // Remove the close button from the stage immediately
                closeButton.remove();
                gameState = GameState.NORMAL;
                // Re-enable all other actors on the stage
                for (Actor actor : stage.getActors()) {
                    actor.setTouchable(Touchable.enabled);
                }
            }
        });
    }
}

enum GameState {
    SHOW_CARD,
    NORMAL;
}
