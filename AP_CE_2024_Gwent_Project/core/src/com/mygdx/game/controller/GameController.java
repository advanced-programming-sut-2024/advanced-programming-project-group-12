package com.mygdx.game.controller;


import com.badlogic.gdx.graphics.Texture;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.CardActor;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    private AbstractCard selectedCard;

    public void setSelectedCard(AbstractCard card) {
        selectedCard = card;
    }
    public AbstractCard getSelectedCard() {
        return selectedCard;
    }
    public void playCard(PlayableCard card) {
        // Play a card

    }
    public void vetoCard(int cardNumber) {
        // Veto a card
    }

    public void inHandDeck(int cardNumber) {

    }

    public void remainingCardsToPlay() {

    }

    public void outOfPlayCards() {

    }

    public void cardsInRow(int rowNumber) {

    }

    public void spellsInPlay() {

    }

    public void putCard(int cardNumber, int rowNumber) {

    }

    public void CommanderInfo() {

    }

    public void PlayersInfo() {

    }

    public void PlayersLivesInfo() {

    }

    public void NumberOfCardsInHand() {

    }

    public void TurnInfo() {

    }

    public void totalScore() {

    }

    public void totalScoreOfRow(int rowNumber) {

    }

    public void passRound() {

    }

    public void endRound() {

    }

    public void endGame() {

    }
    public void displayAllRows(Stage stage) {
        for(int i = 0; i < 3; i++) {
            ArrayList<PlayableCard> cards = Game.getCurrentGame().getGameBoard().getCardsByRow(Game.getCurrentGame().getCurrentPlayer(), i);
            ArrayList<SpellCard> spellCards = Game.getCurrentGame().getGameBoard().getSpellCardsByRow(Game.getCurrentGame().getCurrentPlayer(), i);
            displayARow(cards, spellCards, stage, 100 * (i+1));
        }
    }
    public void displayARow(ArrayList<PlayableCard> cards, ArrayList<SpellCard> spellCards, Stage stage, int heightOfRow) {
        Table table = new Table();
        int cardWidth = (new Texture(cards.get(0).getAssetName())).getWidth() / 7;
        int cardHeight = (new Texture(cards.get(0).getAssetName())).getHeight() / 7;
        int spacing = 5;

        for (AbstractCard card : cards) {
            // Create an Actor for the card
            CardActor cardActor = new CardActor(card, false);
            // Add the card actor to the table
            table.add(cardActor).pad(spacing);
        }
        table.center();
        table.setFillParent(true);
        table.setY(heightOfRow);
        stage.addActor(table);

    }

    public void displayDeck(ArrayList<AbstractCard> deck, Stage stage) {
        Table table = new Table();
        int spacing = 7;

        for (AbstractCard card : deck) {
            // Create an Actor for the card
            CardActor cardActor = new CardActor(card, true);

            // Add the card actor to the table
            table.add(cardActor).pad(spacing);
        }

        table.center();
        table.setFillParent(true);
        table.setY(-280);
        stage.addActor(table);

    }



}
