package com.mygdx.game.model;

import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.gameBoard.GameBoard;
import com.mygdx.game.model.card.PlayableCard;

import java.util.ArrayList;

public enum Action {
    SCORCH(() -> {
        Player opposition = Game.getCurrentGame().getOpposition();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        ArrayList<PlayableCard> allOppositionPlayableCards = gameBoard.allPlayerPlayableCards(opposition);

        if(allOppositionPlayableCards.size() == 0) {
            return;
        }

        allOppositionPlayableCards.sort(null);

        ArrayList<PlayableCard> cardsToBeScorched = new ArrayList<>();
        int maxPower = allOppositionPlayableCards.get(0).getPower();
        for(PlayableCard i: allOppositionPlayableCards) {
            if(i.getPower() == maxPower) {
                cardsToBeScorched.add(i);
            }
            else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeScorched) {
            i.kill();
        }
    }),
    TIGHT_BOND(() -> {
    }),
    MEDIC(() -> {
        // should open a menu in game screen to choose from one card of the below list
        Player player = Game.getCurrentGame().getCurrentPlayer();
        ArrayList<AbstractCard> discard = Game.getCurrentGame().getGameBoard().getDiscard(player);
        AbstractCard chosenCard;
//        chosenCard.place();
    }),
    SPY(() -> {

    }),

    //faction actions
    NORTHERN_REALMS(() -> {
        Player player1 = Game.getCurrentGame().getCurrentPlayer();
        Player player2 = Game.getCurrentGame().getOpposition();
        if(player1.isWon() && player1.getFaction().equals(Faction.NORTHERN_REALMS)) {
            player1.drawCard();
        } else if (player2.isWon() && player2.getFaction().equals(Faction.NORTHERN_REALMS)) {
            player2.drawCard();
        }
    }),
    NILFGAARD(() -> {
        Player player1 = Game.getCurrentGame().getCurrentPlayer();
        Player player2 = Game.getCurrentGame().getOpposition();
        if(player1.getFaction().equals(Faction.NILFGAARD) && player2.getFaction().equals(Faction.NILFGAARD)) {
            return;
        }

        if(!player1.isWon() && !player2.isWon()) {
            if(player1.getFaction().equals(Faction.NORTHERN_REALMS) ) {
                player1.setWon(true);
            } else if (player2.getFaction().equals(Faction.NORTHERN_REALMS)) {
                player2.setWon(true);
            }
        }
    }),
    MORALE(() -> {}),
    COW(null), HORN(null),
    MUSKET(null),
    BEAR(null), MUSHROOM(null), DECOY(null),

    //wather actions
    CLEAR(null),
    FOG(null),
    FROST(null),
    RAIN(null),
    STORM(null),
    //leader actions,
    FOLTEST1(() -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(2);
    }),
    NO_ACTION(() -> {}),
    ;
    private Runnable action;

    Action(Runnable action) {
        this.action = action;
    }

    public void execute() {
        action.run();
    }
}
