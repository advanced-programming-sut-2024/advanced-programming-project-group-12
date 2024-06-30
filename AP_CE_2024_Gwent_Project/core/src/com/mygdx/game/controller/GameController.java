package com.mygdx.game.controller;


import com.mygdx.game.model.Row;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.PlayableCard;


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

    public void playCard(AbstractCard selectedCard, Row row) {

    }

    public void playWeatherCard() {
    }
    /*
     this method has two inputs
     a boolean for side and this represent this : true -> player, false -> opposition
     a number that show player wants to play selected card in this row
     output of this method should be a boolean that tell if player can play this card in this row or not
     */
    public boolean isAllowedToPlay(boolean side, int rowNumber) {
        //TODO:

        return true;
    }

    public boolean isCardAHorn(AbstractCard card) {
        //TODO :
        return true;
    }
}
