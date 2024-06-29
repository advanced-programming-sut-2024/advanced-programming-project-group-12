package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Row;
import com.mygdx.game.model.actors.CardActor;
import com.mygdx.game.model.actors.PlayerInfoBox;
import com.mygdx.game.model.actors.RowActor;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.gameBoard.GameBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameScreen implements Screen {
    //
    private Stage stage;
    private Texture background;
    //Buttons for veto, pass round, end round, end game
    private TextButton passButton;
    private GameController controller;
    private Actor weatherBox;
    // info boxes
    private PlayerInfoBox playerInfoBox;
    private PlayerInfoBox oppositionInfoBox;
    public GameScreen() {
        stage = new Stage();
        background = new Texture("bg/board.jpg");
        passButton = new TextButton("Pass", Gwent.singleton.skin);
        passButton.setPosition(220, 120);
        passButton.setSize(150, 80);
        stage.addActor(passButton);
        controller = new GameController();
        weatherBox = new Actor();
        weatherBox.setSize(200,100);
        weatherBox.setPosition(70, 420);
        weatherBoxListener();
        displayInfo();



        handleAddCardToRows();
        displayLeaderCard();
        displayRows();
        displayHand();
        Gdx.input.setInputProcessor(stage);
    }

    private void weatherBoxListener() {

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
        CardActor leaderCard = new CardActor(Game.getCurrentGame().getCurrentPlayer().getLeader(), controller);
        leaderCard.setWidth((float) (leaderCard.getWidth() * 1.25));
        leaderCard.setPosition(115, 100);
        stage.addActor(leaderCard);
        CardActor oppositeLeaderCard = new CardActor(Game.getCurrentGame().getOpposition().getLeader(), controller);
        oppositeLeaderCard.setWidth((float) (oppositeLeaderCard.getWidth() * 1.25));
        oppositeLeaderCard.setPosition(115, 780);
        stage.addActor(oppositeLeaderCard);
    }
    public void displayRows() {
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        List<Player> players = Arrays.asList(Game.getCurrentGame().getCurrentPlayer(),
                Game.getCurrentGame().getOpposition());
        for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
            Player player = players.get(playerIndex);
            ArrayList<Row> rows = gameBoard.getAllRowsForPlayer(player);

            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                Row row = rows.get(rowIndex);
                RowActor rowActor = new RowActor(row, controller);

                // Set the position of the row actor based on the row and player
                // This is just an example, adjust the values to suit your needs
                float x = 50;
                float y = (playerIndex * rows.size() + rowIndex) * rowActor.getHeight() + 50;
                rowActor.setPosition(x, y);

                // Add the row actor to the stage
                stage.addActor(rowActor);
            }
        }
    }
    public void displayHand() {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        LinkedList<AbstractCard> hand = player.getHand();

        for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
            AbstractCard card = hand.get(cardIndex);
            CardActor cardActor = new CardActor(card, controller);

            // Set the position of the card actor based on the card index
            // This is just an example, adjust the values to suit your needs
            float x = (cardIndex * cardActor.getWidth() + 10) + 300;
            float y = 200;
            cardActor.setPosition(x, y);

            // Add the card actor to the stage
            stage.addActor(cardActor);
        }
    }

}
