package com.mygdx.game.controller.local;


import com.mygdx.game.Gwent;
import com.mygdx.game.model.game.card.*;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.*;

import com.mygdx.game.view.Screens;
import com.mygdx.game.view.screen.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private AbstractCard selectedCard;
    private boolean permission = false;
    private boolean changeTurn = false;
    private ArrayList<AbstractCard> cardsToShow;
    private int numberOfCardsToChoose;
    private boolean canChooseLess;
    private boolean showSelectCardCalled = false;
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
        ((GameScreen)Gwent.singleton.getCurrentScreen()).endRound(winner);
    }

    public void endGame(String winnerName, boolean hasWinner) {
        int state = 0;
        if(hasWinner) {
            if(Client.getInstance().getUser().getUsername().equals(winnerName))
                state = 1;
             else state = -1;
        }
        ((GameScreen)Gwent.singleton.getCurrentScreen()).setGameEnd(state);
    }

    public void playCard(AbstractCard card, int row) {
        Client.getInstance().sendMassage(new PlayCardRequest(row, card.getName()));
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
    public void chooseCardInSelectCardMode(ArrayList<AbstractCard> cards, boolean canChooseLess) {
        ArrayList<String> cardList = new ArrayList<>();
        for(AbstractCard abstractCard: cards) {
            cardList.add(abstractCard.getName());
        }
        if(canChooseLess) {
            Client.getInstance().sendMassage(new ReDrawResponse(cardList));
        }
        else {
            Client.getInstance().sendMassage(new CardSelectionAnswer(cardList));
        }
    }

    public void chooseWhichPlayerStartFirst(String username) {
        Client.getInstance().sendMassage(new TurnDecideResponse(username));
    }

    public void goToMainMenu() {

        Gwent.singleton.changeScreen(Screens.MAIN_MENU);
    }
    public void chooseStarter() {
        ((GameScreen)Gwent.singleton.getCurrentScreen()).showChooseStarter();
    }

    public void setShowSelectedCard(List<? extends AbstractCard> cards, int numberOfCards, boolean canChooseLess) {
        showSelectCardCalled = true;
        this.cardsToShow = new ArrayList<>(cards);
        this.numberOfCardsToChoose = numberOfCards;
        this.canChooseLess = canChooseLess;
    }
    public void setOffShowCardToSelect() {
        this.cardsToShow = null;
        this.numberOfCardsToChoose = -1;
        this.canChooseLess = false;
        showSelectCardCalled = false;
    }
    public boolean isShowSelectCardCalled() {
        return showSelectCardCalled;
    }

    public ArrayList<AbstractCard> getCardsToShow() {
        return cardsToShow;
    }

    public int getNumberOfCardsToChoose() {
        return numberOfCardsToChoose;
    }

    public boolean isCanChooseLess() {
        return canChooseLess;
    }

    public void update() {
        ((GameScreen)Gwent.singleton.getCurrentScreen()).setUpdate();
    }


    public void playDecoy(AbstractCard card, int rowNumber) {
        Client.getInstance().sendMassage(new PlayDecoyRequest(rowNumber, card.getName()));
    }
    public void setOppositionDisconnect() {
        ((GameScreen)Gwent.singleton.getCurrentScreen()).setOppositionDisconnect();
    }
    public void setOppositionReconnect() {
        ((GameScreen)Gwent.singleton.getCurrentScreen()).setOppositionReconnect();
    }
}
