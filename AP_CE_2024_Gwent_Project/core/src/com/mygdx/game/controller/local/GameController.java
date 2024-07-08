package com.mygdx.game.controller.local;


import com.mygdx.game.Gwent;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.*;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.CardSelectionAnswer;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.PassRoundRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.PlayCardRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.TurnDecideResponse;

import com.mygdx.game.view.screen.GameScreen;

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

    public void passRound() {
        Client.getInstance().sendMassage(new PassRoundRequest());
    }

    public void endRound(String winner) {
    }

    public void endGame(String winnerName, boolean hasWinner) {
        int state = 0;
        if(hasWinner) {
            if(Client.getInstance().getUser().getUsername().equals(winnerName))
                state = 1;
             else state = -1;
        }
        ((GameScreen)Gwent.singleton.getCurrentScreen()).endGame(state);
    }

    public void playCard(AbstractCard card, int row) {
        Client.getInstance().sendMassage(new PlayCardRequest(row, card.toString()));
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
        return card.equals(AllCards.COMMANDER_HORN.getAbstractCard()) || card.equals(AllCards.MARDROEME.getAbstractCard());
    }
    public boolean getPermission() {
        return permission;
    }
    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    /*
    in this method I assume that player is in select mode
    and I give you card's that is selected and you should handle it
     */
    public void chooseCardInSelectCardMode(ArrayList<AbstractCard> cards) {
        ArrayList<String> cardList = new ArrayList<>();
        for(AbstractCard abstractCard: cards) {
            cardList.add(abstractCard.getName());
        }
        Client.getInstance().sendMassage(new CardSelectionAnswer(cardList));
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public Game getGame() {
        return game;
    }


    public void chooseWhichPlayerStartFirst(String username) {
        Client.getInstance().sendMassage(new TurnDecideResponse(username));
    }

    public void goToMainMenu() {

    }
    public void chooseStarter() {
        ((GameScreen)Gwent.singleton.getCurrentScreen()).showChooseStarter();
    }

}
