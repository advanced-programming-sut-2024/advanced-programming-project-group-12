package com.mygdx.game.controller.local;


import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.*;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.game.GameBoard;

import java.util.ArrayList;

public class GameController {
    private AbstractCard selectedCard;
    private boolean permission;
    private boolean changeTurn = false;
    private Game game;
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
        //todo
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

    public boolean isHorn(AbstractCard card) {
        //TODO :
        return card.equals(AllCards.COMMANDER_HORN.getAbstractCard());
    }
    public boolean getPermission() {
        return permission;
    }
    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    /*
    in this method i assume that player is in select mode
    and i give you card's that is selected and you should handle it
     */
    public void chooseCardInSelectCardMode(ArrayList<AbstractCard> cards) {
        //TODO :
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public Game getGme() {
        return game;
    }

    public void playLeader(CommanderCard leader) {
        //Play leader
    }

    public void chooseWhichPlayerStartFirst(String username) {

    }
}
