package com.mygdx.game.controller;


import com.badlogic.gdx.scenes.scene2d.Stage;

import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.view.screen.GameScreen;

import java.util.ArrayList;
import java.util.LinkedList;

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
}
