package com.mygdx.game.model;

import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.PlayableCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;

public enum Action {
    /**
     * these constants contain runnables that are executed after each turn is complete and also right
     * after being placed
     */
    SCORCH(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> allPlayableCards = gameBoard.allPlayerPlayableCards(opposition);
        allPlayableCards.addAll(gameBoard.allPlayerPlayableCards(card.getPlayer()));

        if(allPlayableCards.isEmpty()) {
            return;
        }

        allPlayableCards.sort(null);

        ArrayList<PlayableCard> cardsToBeScorched = new ArrayList<>();
        int maxPower = allPlayableCards.get(0).getPower();
        for (PlayableCard i : allPlayableCards) {
            if (i.getPower() == maxPower) {
                cardsToBeScorched.add(i);
            } else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeScorched) {
            i.kill();
        }

        card.kill();
    }),
    SCORCH_S(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 2);
        if(gameBoard.getRowStrength(opposition, 2)  < 10) return;

        row.sort(null); //sort cards based on power
        ArrayList<PlayableCard> cardsToBeKilled = new ArrayList<>();
        for(PlayableCard i: row) {
            if(i.getPower() == row.get(0).getPower()) {
                cardsToBeKilled.add(i);
            } else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeKilled) {
            i.kill();
        }
    }),
    SCORCH_R(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 1);
        if(gameBoard.getRowStrength(opposition,1)  < 10) return;

        row.sort(null); //sort cards based on power
        ArrayList<PlayableCard> cardsToBeKilled = new ArrayList<>();
        for(PlayableCard i: row) {
            if(i.getPower() == row.get(0).getPower()) {
                cardsToBeKilled.add(i);
            } else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeKilled) {
            i.kill();
        }
    }),
    SCORCH_C(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 0);
        if(gameBoard.getRowStrength(opposition, 0)  < 10) return;

        row.sort(null); //sort cards based on power
        ArrayList<PlayableCard> cardsToBeKilled = new ArrayList<>();
        for(PlayableCard i: row) {
            if(i.getPower() == row.get(0).getPower()) {
                cardsToBeKilled.add(i);
            } else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeKilled) {
            i.kill();
        }
    }),
    TIGHT_BOND(card -> {
        //todo
    }),
    MEDIC(card -> {
        // should open a menu in game screen to choose from one card of the below list
        Player player = ((PlayableCard)card).getPlayer();
        ArrayList<PlayableCard> discard = card.getPlayer().getGame().getGameBoard().getDiscardPlayableCards(player);
        //todo
        PlayableCard chosenCard = null;
        chosenCard.revive();
    }),
    SPY(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        player.drawCard().drawCard();
    }),
    MORALE(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        gameBoard.increaseMorale(card.getRow());
    }),
    COW(card -> {
        PlayableCard playableCard = (PlayableCard) card;
        if(playableCard.isDead()) {
            playableCard.getLegacyCard().place(card.getRow(), card.getPlayer());
        }
    }),
    HORN(card -> {
        Row row = card.getPlayer().getGame().getGameBoard().getRowForCurrentPlayer(card.getRow());
        row.setHorn(true);
    }),
    HORN_CARD(card -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(card.getRow(),card.getPlayer());
    }),
    MUSKET(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        LinkedList<AbstractCard> deck = player.getDeck();
        int row = card.getRow();
        for (AbstractCard i : deck) {
            if (i.getName().equals(card.getName())) {
                deck.remove(i);
                if (i.getAllowableRows().contains(row)) {
                    i.place(row, card.getPlayer());
                } else {
                    i.place(i.getDefaultRow(), card.getPlayer());
                }
            }
        }

        LinkedList<AbstractCard> hand = player.getHand();
        for (AbstractCard i : hand) {
            if (i.getName().equals(card.getName())) {
                if (i.getAllowableRows().contains(row)) {
                    i.place(row, card.getPlayer());
                } else {
                    i.place(i.getDefaultRow(), i.getPlayer());
                }
            }
        }
    }),
    BEAR(card -> {
        Player player = card.getPlayer();
        GameBoard gameBoard = player.getGame().getGameBoard();
        int rowNumber = card.getRow();
        Row row = gameBoard.getRowForCurrentPlayer(rowNumber);
        if(row.hasMushroom()) {
            card.kill();
            ((PlayableCard) card).getLegacyCard().place(card.getDefaultRow(), player);
        }
    }),
    MUSHROOM(card -> {
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        int rowNumber = card.getRow();
        Row row = gameBoard.getRowForCurrentPlayer(rowNumber);
        row.setHasMushroom();
        ArrayList<PlayableCard> rowCards = row.getCards();
        for(PlayableCard i : rowCards) {
            if(i.getAction().equals(Action.BEAR)) {
                i.doAction();
            }
        }
    }),
    DECOY(abstractCard -> {
        //todo
    }),

    //weather actions
    CLEAR(card -> {
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        for(int i = 0; i< 3; i++ ){
            Row row = gameBoard.getRowForCurrentPlayer(i);
            row.setWeatherBuffer(false);
        }
    }),
    FOG(card ->card.getPlayer().getGame().getGameBoard().getRowForCurrentPlayer(1).setWeatherBuffer(true)
    ),
    FROST(card -> card.getPlayer().getGame().getGameBoard().getRowForCurrentPlayer(0).setWeatherBuffer(true)),
    RAIN(card -> card.getPlayer().getGame().getGameBoard().getRowForCurrentPlayer(2).setWeatherBuffer(true)),
    STORM(card -> {
        FOG.action.accept(null);
        RAIN.action.accept(null);
    }),

    //leader actions,
    FOLTEST_SIEGE(card -> {
        AllCards.FOG.getAbstractCard().place(3, card.getPlayer());
    }),
    FOLTEST_STEEL(card -> {
        AllCards.CLEAR.getAbstractCard().place(3, card.getPlayer());
    }),
    FOLTEST_KING(card -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(2, card.getPlayer());
    }),

    EMHYR_EMPERIAL(card -> {
       AllCards.RAIN.getAbstractCard().place(3, card.getPlayer());
    }),
    EMHYR_EMPEROR(card -> {
        Player opponent = card.getPlayer().getGame().getOpposition();
        LinkedList<AbstractCard> hand =(LinkedList<AbstractCard>) opponent.getHand().clone();
        ArrayList<AbstractCard> toBeShown = new ArrayList<>(3);
        for(int i = 0; i< 3 ;i++) {
            int index = (int) (hand.size() * Math.random());
            toBeShown.add(hand.get(index));
            hand.remove(index);
        }

        //todo
        //add mech to show the array list
    }),
    EMHYR_WHITEFLAME(card -> {
       Player opponent = card.getPlayer().getGame().getOpposition();
       opponent.getLeader().setHasPlayedAction(true);
    }),
    EMHYR_RELENTLESS(abstractCard -> {
        Player opponent = abstractCard.getPlayer().getGame().getOpposition();
        //todo
        //sent to secket a request to ask for the choosing card interface
    }),
    EMHYR_INVADER(abstractCard -> {

    }),

    ERIDIN_COMMANDER(abstractCard -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(0, abstractCard.getPlayer());
    }),
    ERIDIN_BRINGER(abstractCard -> {
        //todo
    }),
    ERIDIN_DESTROYER(abstractCard -> {

    }),
    ERIDIN_KING(abstractCard -> {

    }),
    ERIDIN_TREACHEROUS(abstractCard -> {
        abstractCard.getPlayer().getGame().getGameBoard().setDoubleSpyPower();
    }),

    FRANCESCA_QUEEN(abstractCard -> SCORCH_C.execute(null)),
    FRANCESCA_BEAUTIFUL(abstractCard -> AllCards.COMMANDER_HORN.getAbstractCard().place(1, abstractCard.getPlayer())),
    FRANCESCA_DAISY(abstractCard -> {
        //probably has to be hardcoded
    }),
    FRANCESCA_PUREBLOOD(abstractCard -> AllCards.FROST.getAbstractCard().place(3, abstractCard.getPlayer())),
    FRANCESCA_HOPE(abstractCard -> {
        Player player = abstractCard.getPlayer().getGame().getCurrentPlayer();
        GameBoard gameBoard = abstractCard.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> allPlayablePlayedCards = gameBoard.allPlayerPlayableCards(player);
        ArrayList<Row> allRows = gameBoard.getAllRowsForPlayer(player);

        for(PlayableCard card : allPlayablePlayedCards) {
            if(!card.canBeReplaced()) {
                continue;
            }

            int maxPower = 0;
            int maxRow = -1;
            for(int allowableRow: card.getAllowableRows()) {
                int rowPower = allRows.get(allowableRow).calculatePowerOfPlayableCard(card);
                if(rowPower > maxPower) {
                    maxPower = rowPower;
                    maxRow = allowableRow;
                }
            }

            card.replace(maxRow);
        }
    }),

    CRACH_AN_CRAITE(abstractCard -> {
        Game currentGame = abstractCard.getPlayer().getGame();
        GameBoard gameBoard = currentGame.getGameBoard();

        ArrayList<Player> players = new ArrayList<>(2);
        players.add(currentGame.getCurrentPlayer());
        players.add(currentGame.getOpposition());

        for(Player i : players) {
            ArrayList<AbstractCard> discard = gameBoard.getDiscardCards(i);
            Collections.shuffle(discard);
            i.addCardsToDeck(discard);
            gameBoard.resetDiscard(i);
        }
    }),
    KING_BRAN(abstractCard -> abstractCard.getPlayer().getGame().getGameBoard().setHalfAttrition()
    ),


    NO_ACTION(card -> {}),
    ;
    private final Consumer<AbstractCard> action;

    Action(Consumer<AbstractCard> action) {
        this.action = action;
    }

    public void execute(AbstractCard card) {
        action.accept(card);
    }
}
