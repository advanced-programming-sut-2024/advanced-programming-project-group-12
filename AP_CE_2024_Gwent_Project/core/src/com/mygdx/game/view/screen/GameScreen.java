package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Action;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.actors.*;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.gameBoard.GameBoard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameScreen implements Screen {
    private final Stage stage;
    private final Texture background;
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

    public GameScreen() {
        stage = new Stage();
        background = new Texture("bg/board.jpg");
        passButton = new TextButton("Pass", Gwent.singleton.skin);
        passButton.setPosition(220, 120);
        passButton.setSize(150, 80);
        weatherBox = new WeatherBox();

        stage.addActor(weatherBox);
        weatherBoxListener();
        stage.addActor(passButton);
        controller = new GameController();
        initialRows();
        displayInfo();


        displayLeaderCard();

        displayHand();
        Gdx.input.setInputProcessor(stage);
    }

    private void initialRows() {
        Game game = Game.getCurrentGame();
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
                if (selectedCard != null) {
                    playWeatherCard(selectedCardActor);
                    // Add the card to the weather box
                    weatherBox.add(selectedCardActor.getImage()).size(80, 110).expand().fill();
                    // Remove the card from the player's hand
                    Game.getCurrentGame().getCurrentPlayer().getHand().remove(selectedCard);
                    // Unselect the card
                    controller.setSelectedCard(null);
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
        playerInfoBox = new PlayerInfoBox(Game.getCurrentGame().getCurrentPlayer().getHand().size(), Game.getCurrentGame().getCurrentPlayer().getUsername(), Game.getCurrentGame().getCurrentPlayer().getFaction().toString());
        stage.addActor(playerInfoBox.getInfoTable());
        oppositionInfoBox = new PlayerInfoBox(Game.getCurrentGame().getOpposition().getHand().size(), Game.getCurrentGame().getOpposition().getUsername(), Game.getCurrentGame().getOpposition().getFaction().toString());
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
                playerInfoBox.kill();
            }
        });
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
        CardActor leaderCard = new CardActor(Game.getCurrentGame().getCurrentPlayer().getLeader());
        leaderCard.getImage().setWidth((float) (leaderCard.getWidth() * 1.15));
        leaderCard.getImage().setPosition(115, 100);
        stage.addActor(leaderCard.getImage());
        CardActor oppositeLeaderCard = new CardActor(Game.getCurrentGame().getOpposition().getLeader());
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
        CardActor enlargedCard = new CardActor(card);
        enlargedCard.getImage().setSize(0, 0); // Start with size 0
        enlargedCard.getImage().setPosition(700, 450);

        // Create the close button
        TextButton closeButton = new TextButton("X", Gwent.singleton.skin);
        closeButton.setSize(75, 75); // Adjust the size as needed
        closeButton.setPosition(enlargedCard.getImage().getX() + 300 - closeButton.getWidth(),
                enlargedCard.getImage().getY() + 450 - closeButton.getHeight()); // Position the close button at the top right corner of the enlarged card
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

        Player player = Game.getCurrentGame().getCurrentPlayer();
        LinkedList<AbstractCard> handCards = player.getHand();
        hand = new HandTable(handCards);
        hand.addToStageAndAddListener(stage);
        for (CardActor cardActor : hand.getCards()) {
            AbstractCard card = cardActor.getCard();
            cardActor.getImage().addListener(new ClickListener() {
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
                        selectedCardActor.getImage().setSize(200, 300);
                        selectedCardActor.getImage().setPosition(1300, 400);
                        stage.addActor(selectedCardActor.getImage());
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

        // Remove the card from the player's hand
        Player currentPlayer = Game.getCurrentGame().getCurrentPlayer();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
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
        playerInfoBox.updatePlayerInfo(Game.getCurrentGame().getCurrentPlayer().getHand().size());
    }

    private void playWeatherCard(CardActor card) {

    }

    private void highlightAllowablePlaces(AbstractCard card) {
        if (card.getAllowableRows() == null) return;
        List<Integer> allowableRows = card.getAllowableRows();

        if(allowableRows.contains(3)) {
            weatherBox.highlight();
        }
        // Check if the card is a horn card
        if (controller.isCardAHorn(card)) {
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
}
