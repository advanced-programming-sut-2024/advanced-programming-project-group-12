package com.mygdx.game.controller;


import com.mygdx.game.model.Action;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;
import com.mygdx.game.model.GameBoard;

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

    public void playCard(AbstractCard card, int row) {
        Player currentPlayer = Game.getCurrentGame().getCurrentPlayer();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        if(card instanceof PlayableCard) {
            gameBoard.addCard(currentPlayer, row, (PlayableCard)card);
        } else {
            gameBoard.addCard(currentPlayer, row,(SpellCard)card);
        }
        currentPlayer.removeCardFromHand(card);
    }

    public void playWeatherCard() {
    }
    /*
     this method has two inputs
     a boolean for side and this represent this : true -> player, false -> opposition
     a number that show player wants to play selected card in this row
     output of this method should be a boolean that tell if player can play this card in this row or not
     */
    public boolean isAllowedToPlay(AbstractCard card, boolean side, int rowNumber) {
        if(!side) {
            return card.getAction().equals(Action.SPY) &&
                    card.getAllowableRows().contains(rowNumber);
        } else {
            return card.getAllowableRows().contains(rowNumber);
        }
    }

    public boolean isCardAHorn(AbstractCard card) {
        //TODO :
        return card.equals(AllCards.COMMANDER_HORN.getAbstractCard());
    }
}
