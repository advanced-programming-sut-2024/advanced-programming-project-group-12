package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

public class BroadCastScreen implements Screen {
    private final Stage stage;
    private final Texture background;
    private final GameController controller;
    private Player player;
    private Player opposition;
    private PlayerInfoBox playerInfoBox;
    private PlayerInfoBox oppositionInfoBox;

    public BroadCastScreen() {
        controller = new GameController();
        this.stage = new Stage(new ScreenViewport());
        this.background = new Texture("bg/board.jpg");

        player = Client.getInstance().getGame().getCurrentPlayer();
        opposition = Client.getInstance().getGame().getOpposition();


        displayInfo();
        displayLeaderCard();
        displayPlayerDeckStack(player, 97);
        displayPlayerDeckStack(opposition, 785);
    }

    private void displayInfo() {
        playerInfoBox = new PlayerInfoBox(player.getHandAsCards().size(), player.getUsername(), player.getFaction().toString());
        stage.addActor(playerInfoBox.getInfoTable());
        oppositionInfoBox = new PlayerInfoBox(opposition.getHandAsCards().size(), opposition.getUsername(), opposition.getFaction().toString());
        stage.addActor(oppositionInfoBox.getInfoTable());
        playerInfoBox.setPosition(50, 260);
        oppositionInfoBox.setPosition(50, 610);
    }

    private void displayLeaderCard() {
        InfoCardActor leaderCard = new InfoCardActor(player.getLeader());
        leaderCard.getImage().setWidth((float) (leaderCard.getWidth() * 1.15));
        leaderCard.getImage().setPosition(115, 100);
        stage.addActor(leaderCard.getImage());
        InfoCardActor oppositeLeaderCard = new InfoCardActor(opposition.getLeader());
        oppositeLeaderCard.getImage().setWidth((float) (oppositeLeaderCard.getWidth() * 1.15));
        oppositeLeaderCard.getImage().setPosition(115, 780);
        stage.addActor(oppositeLeaderCard.getImage());
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
            cardImage.setPosition(1440 - (i * CARD_OFFSET), y + (i * CARD_OFFSET));
            stage.addActor(cardImage);
        }

        Label numberOfCards = new Label("Cards: " + deckSize, Gwent.singleton.skin);
        numberOfCards.setColor(Color.ORANGE);
        numberOfCards.setPosition(1440 + 45 - numberOfCards.getWidth() / 2, y - 60);
        stage.addActor(numberOfCards);
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
        stage.getViewport().update(width, height, true);
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
        background.dispose();
    }
}
