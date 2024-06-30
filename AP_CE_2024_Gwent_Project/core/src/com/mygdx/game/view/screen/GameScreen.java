package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.actors.CardActor;
import com.mygdx.game.model.actors.HandTable;
import com.mygdx.game.model.actors.PlayerInfoBox;
import com.mygdx.game.model.actors.RowTable;
import com.mygdx.game.model.card.AbstractCard;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameScreen implements Screen {
    private Stage stage;
    private Texture background;
    //Buttons for veto, pass round, end round, end game
    private TextButton passButton;
    private GameController controller;
    // info boxes and Actor
    private Table weatherBox;
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
        weatherBox = new Table(Gwent.singleton.skin);
        weatherBox.setSize(255, 160);
        weatherBox.setPosition(110, 425);
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
                    System.out.println("click on row : " + playerRow.getRowNumber() + " and side " + playerRow.getSide());
                    if (controller.getSelectedCard() != null) {
                        if (controller.isAllowedToPlay(playerRow.getSide(), playerRow.getRowNumber())) {
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
                    System.out.println("click on row : " + enemyRow.getRowNumber() + " and side " + enemyRow.getSide());
                    if (controller.getSelectedCard() != null) {
                        if (controller.isAllowedToPlay(enemyRow.getSide(), enemyRow.getRowNumber())) {
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
                if (controller.getSelectedCard() != null) {
                    if (controller.getSelectedCard().getFaction().equals(Faction.WEATHER)) {
                        controller.playWeatherCard();
                        playWeatherCard(selectedCardActor);
                    }
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

    public Stage getStage() {
        return stage;
    }

    public void displayLeaderCard() {
        CardActor leaderCard = new CardActor(Game.getCurrentGame().getCurrentPlayer().getLeader());
        leaderCard.setWidth((float) (leaderCard.getWidth() * 1.25));
        leaderCard.setPosition(115, 100);
        stage.addActor(leaderCard);
        CardActor oppositeLeaderCard = new CardActor(Game.getCurrentGame().getOpposition().getLeader());
        oppositeLeaderCard.setWidth((float) (oppositeLeaderCard.getWidth() * 1.25));
        oppositeLeaderCard.setPosition(115, 780);
        stage.addActor(oppositeLeaderCard);
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
                        highlightAllowablePlaces(card);

                    }
                }
            });
        }
    }

    private void resetBackgroundColors() {
        // Reset the background color of all rows and boxes
    }

    private void highlightAllowablePlaces(AbstractCard card) {

    }

    private void playCard(CardActor cardActor, RowTable row) {
        // Get the card from the CardActor
        AbstractCard card = cardActor.getCard();

        // Remove the card from the player's hand
        Player currentPlayer = Game.getCurrentGame().getCurrentPlayer();
        currentPlayer.getHand().remove(card);

        // Check if the card is a horn card
        if (controller.isCardAHorn(card)) {
            // Check if the horn area already contains a card
            if (row.getHornArea().getChildren().size == 0) {
                // If not, add the card to the horn area of the row
                row.getHornArea().add(cardActor.getImage()).size(80, 110).expand().fill();
            } else {
                // If it does, inform the player that they can't play another card in the horn area
                System.out.println("You can only play one card in the horn area.");
            }
        } else {
            // Add the card to the specified row
            row.add(cardActor.getImage()).size(80, 110);
            row.getCards().add(cardActor);
        }


        // Remove the CardActor from the stage
        cardActor.remove();

        // Create a new CardActor for the card, set its size and position to make it appear in the row, and add it to the stage
        CardActor newCardActor = new CardActor(card);
        newCardActor.getImage().setSize(80, 120); // Set the size to make the card appear in the row
        float x = (row.getCards().size() - 1) * newCardActor.getWidth() + row.getX() + 10;
        float y = row.getY();
        newCardActor.getImage().setPosition(x, y);
        stage.addActor(newCardActor.getImage());

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
    }

    private void playWeatherCard(CardActor card) {

    }
}
