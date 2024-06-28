package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.actors.CardActor;
import com.mygdx.game.model.actors.HandActor;
import com.mygdx.game.model.actors.PlayerInfoBox;
import com.mygdx.game.model.actors.RowActor;
import com.mygdx.game.model.card.PlayableCard;

import java.util.ArrayList;

public class GameScreen implements Screen {
    //
    private Stage stage;
    private Texture background;
    //Buttons for veto, pass round, end round, end game
    private TextButton passButton;
    private GameController controller;
    //
    private ArrayList<RowActor> playerRowActors;
    private ArrayList<RowActor> oppositionRowActors;
    private HandActor handActor;
    // info boxes
    private PlayerInfoBox playerInfoBox;
    private PlayerInfoBox oppositionInfoBox;
    public GameScreen() {
        stage = new Stage();
        background = new Texture("bg/board.jpg");
        passButton = new TextButton("Pass", Gwent.singleton.skin);
        passButton.setPosition(220, 120);
        passButton.setSize(150, 80);
        displayInfo();

        stage.addActor(passButton);
        playerRowActors = new ArrayList<>();
        oppositionRowActors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RowActor rowActor = new RowActor(i);
            rowActor.setPosition(stage);
            playerRowActors.add(rowActor);
        }

        handActor = new HandActor(Game.getCurrentGame().getCurrentPlayer().getHand());
        handActor.setPosition(stage);
        stage.addActor(handActor.getTable());
        controller = new GameController();

        passButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                controller.passRound();
            }
        });

        handleAddCardToRows();
        displayLeaderCard();

        Gdx.input.setInputProcessor(stage);
    }

    private void displayInfo() {
        playerInfoBox = new PlayerInfoBox(Game.getCurrentGame().getCurrentPlayer().getHand().size(), Game.getCurrentGame().getCurrentPlayer().getUsername(), Game.getCurrentGame().getCurrentPlayer().getFaction().toString());
        stage.addActor(playerInfoBox.getInfoTable());
        oppositionInfoBox = new PlayerInfoBox(Game.getCurrentGame().getOpposition().getHand().size(), Game.getCurrentGame().getOpposition().getUsername(), Game.getCurrentGame().getOpposition().getFaction().toString());
        stage.addActor(oppositionInfoBox.getInfoTable());
        playerInfoBox.setPosition(50, 260);
        oppositionInfoBox.setPosition(50, 610);
    }

    private void handleAddCardToRows() {
        for (RowActor rowActor : playerRowActors) {
            stage.addActor(rowActor.getTableContainer());

            // Add a click listener to the row
            rowActor.getTableContainer().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Get the selected card from the hand actor
                    CardActor selectedCard = handActor.getSelectedCard();

                    if (selectedCard != null) {
                        // Calculate the initial position of the card
                        float initialX = selectedCard.getX();
                        float initialY = selectedCard.getY();

                        // Calculate the final position of the card
                        float finalX = rowActor.getTable().getX() + rowActor.getTable().getWidth() / 2;
                        float finalY = rowActor.getTable().getY() + rowActor.getTable().getHeight() / 2;

                        // Create a MoveToAction that moves the card to the final position
                        Action moveAction = Actions.moveTo(finalX, finalY, 1f); // Adjust the duration as needed

                        // Add the MoveToAction to the card
                        selectedCard.addAction(moveAction);

                        // Add the card to the row after the move animation
                        selectedCard.addAction(Actions.sequence(
                                Actions.delay(1f), // Delay for the movement to complete
                                Actions.run(() -> {
                                    if(selectedCard.getCard() instanceof PlayableCard) {
                                        rowActor.addPlayableCard((PlayableCard) selectedCard.getCard());
                                    } else {
                                        rowActor.addSpellCard(selectedCard.getCard());
                                    }
                                    // Remove the card from the hand
                                    handActor.removeCard(selectedCard.getCard());
                                    // Reset the selected card in the hand actor
                                    handActor.setSelectedCard(null);
                                })
                        ));
                    }
                }
            });
        }
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

        CardActor selectedCard = handActor.getSelectedCard();
        if (selectedCard != null) {
            // Draw a larger version of the selected card in the center of the screen
            Texture cardTexture = new Texture(selectedCard.getCard().getAssetName());
            float cardWidth = (float) (cardTexture.getWidth() / 1.7); // Adjust the scale factor as needed
            float cardHeight = (float) cardTexture.getHeight() / 2; // Adjust the scale factor as needed
            float cardX = 1300;
            float cardY = 340;
            stage.getBatch().begin();
            stage.getBatch().draw(cardTexture, cardX, cardY, cardWidth, cardHeight);
            stage.getBatch().end();
            playerInfoBox.updatePlayerInfo(Game.getCurrentGame().getCurrentPlayer().getHand().size());
            oppositionInfoBox.updatePlayerInfo(Game.getCurrentGame().getOpposition().getHand().size());
        }


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
}
