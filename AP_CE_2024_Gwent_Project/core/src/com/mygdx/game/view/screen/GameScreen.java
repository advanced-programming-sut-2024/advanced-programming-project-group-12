package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.ScreenManager;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Row;
import com.mygdx.game.model.actors.CardActor;
import com.mygdx.game.model.actors.HandActor;
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

    public GameScreen() {
        stage = new Stage();
        background = new Texture("bg/board.jpg");
        passButton = new TextButton("Pass", Gwent.singleton.skin);
        passButton.setPosition(220, 120);
        passButton.setSize(150, 80);
        stage.addActor(passButton);
        playerRowActors = new ArrayList<>();
        oppositionRowActors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RowActor rowActor = new RowActor(i);
            rowActor.setPosition(stage);
            playerRowActors.add(rowActor);
        }

//        for (int i = 0; i < 3; i++) {
//            RowActor rowActor = new RowActor(i, new ArrayList<>(), new ArrayList<>());
//            rowActor.setPosition(stage);
//            oppositionRowActors.add(rowActor);
//        }
        handActor = new HandActor(Game.getCurrentGame().getOpposition().getHand());
        handActor.setPosition(stage);
        stage.addActor(handActor.getTable());
        controller = new GameController();

        passButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                controller.passRound();
            }
        });

        for (RowActor rowActor : playerRowActors) {
            stage.addActor(rowActor.getTableContainer());

            // Add a click listener to the row
            rowActor.getTableContainer().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Get the selected card from the hand actor
                    CardActor selectedCard = handActor.getSelectedCard();

                    if (selectedCard != null) {
                        // Add the selected card to the row
                        if(selectedCard.getCard() instanceof PlayableCard) {
                            rowActor.addPlayableCard((PlayableCard) selectedCard.getCard());
                        } else {
                            rowActor.addSpellCard(selectedCard.getCard());
                        }

                        // Remove the selected card from the hand
                        handActor.removeCard(selectedCard.getCard());

                        // Reset the selected card in the hand actor
                        handActor.setSelectedCard(null);
                    }
                }
            });
        }
        for (RowActor rowActor : oppositionRowActors) {
            stage.addActor(rowActor.getTable());
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
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
}
