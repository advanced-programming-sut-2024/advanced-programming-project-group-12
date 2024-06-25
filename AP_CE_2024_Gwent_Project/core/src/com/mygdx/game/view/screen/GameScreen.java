package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.CardActor;
import jdk.javadoc.internal.doclets.formats.html.Table;

public class GameScreen implements Screen {
    //
    private Stage stage;
    private Texture background;
    //Buttons for veto, pass round, end round, end game
    private GameController controller;
    public GameScreen() {
        stage = new Stage();
        background = new Texture("bg/board.jpg");
        controller = new GameController();
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This will clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1); // This will set the clear color to black

        // Draw the background
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(delta);
        controller.displayDeck(Player.getCurrentPlayer().getDeck(), stage);
        if(controller.getSelectedCard() != null) {
            showCardOnRightSide();
        }
        displayLeaderCards();
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
    public void showCardOnRightSide() {
        // Create a new CardActor for the card
        CardActor cardActor = new CardActor(controller.getSelectedCard(), false);
        cardActor.setSize(225, 330); // set the size of the card actor

        // Set the position of the card actor on the right side of the screen
        cardActor.setPosition(Gdx.graphics.getWidth() - cardActor.getWidth() - 60, (float) Gdx.graphics.getHeight() / 2 - cardActor.getHeight() / 2 + 50);

        // Add the card actor to the stage
        stage.addActor(cardActor);
    }
    public void displayLeaderCards() {
        AbstractCard playerCommander = Player.getCurrentPlayer().getLeader();
        AbstractCard opponentCommander = Game.getCurrentGame().getOpposition().getLeader();
        // Create a new CardActor for the player's commander card
        CardActor playerCommanderActor = new CardActor(playerCommander, false);
        playerCommanderActor.setSize(85, 120); // set the size of the card actor
        playerCommanderActor.setPosition(90, (float) 75);
        // Add the player's commander card actor to the stage
        stage.addActor(playerCommanderActor);
        // Create a new CardActor for the opponent's commander card
        CardActor opponentCommanderActor = new CardActor(opponentCommander, false);
        opponentCommanderActor.setSize(85, 120); // set the size of the card actor
        opponentCommanderActor.setPosition(90, (float)  660);
        // Add the opponent's commander card actor to the stage
        stage.addActor(opponentCommanderActor);
    }
    public GameController getController() {
        return controller;
    }
}
