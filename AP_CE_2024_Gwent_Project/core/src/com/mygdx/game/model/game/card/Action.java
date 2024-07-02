package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.GameBoard;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.game.Row;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Function;

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
            return null;
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
        return null;
    }),
    SCORCH_S(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 2);
        if(gameBoard.getRowStrength(opposition, 2)  < 10) return null;

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
        return null;
    }),
    SCORCH_R(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 1);
        if(gameBoard.getRowStrength(opposition,1)  < 10) return null;

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
        return null;
    }),
    SCORCH_C(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 0);
        if(gameBoard.getRowStrength(opposition, 0)  < 10) return null;

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
        return null;
    }),
    TIGHT_BOND(card -> {
        return null;
    }),
    MEDIC(card -> {
        // should open a menu in game screen to choose from one card of the below list
        Player player = ((PlayableCard)card).getPlayer();
        ArrayList<PlayableCard> discard = card.getPlayer().getGame().getGameBoard().getDiscardPlayableCards(player);
        //todo
        PlayableCard chosenCard = null;
        chosenCard.revive();
        return null;
    }),
    SPY(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        player.drawCard().drawCard();
        return null;
    }),
    MORALE(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        gameBoard.increaseMorale(card.getRow(), card.getPlayer());
        return null;
    }),
    COW(card -> {
        PlayableCard playableCard = (PlayableCard) card;
        if(playableCard.isDead()) {
            playableCard.getLegacyCard().place(card.getRow(), card.getPlayer());
        }
        return null;
    }),
    HORN(card -> {
        Row row = card.getPlayer().getGame().getGameBoard().getRowForPlayer(card.getRow(), card.getPlayer());
        row.setHorn(true);
        return null;
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
        return null;
    }),
    BEAR(card -> {
        Player player = card.getPlayer();
        GameBoard gameBoard = player.getGame().getGameBoard();
        int rowNumber = card.getRow();
        Row row = gameBoard.getRowForPlayer(rowNumber, card.getPlayer());
        if(row.hasMushroom()) {
            card.kill();
            ((PlayableCard) card).getLegacyCard().place(card.getDefaultRow(), player);
        }
        return null;
    }),
    MUSHROOM(card -> {
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        int rowNumber = card.getRow();
        Row row = gameBoard.getRowForPlayer(rowNumber, card.getPlayer());
        row.setHasMushroom();
        ArrayList<PlayableCard> rowCards = row.getCards();
        for(PlayableCard i : rowCards) {
            if(i.getAction().equals(Action.BEAR)) {
                i.doAction();
            }
        }
        return null;
    }),
    DECOY(abstractCard -> {
        //todo
        return null;
    }),

    //weather actions
    CLEAR(card -> {
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        for(int i = 0; i< 3; i++ ){
            Row row = gameBoard.getRowForPlayer(i, card.getPlayer());
            row.setWeatherBuffer(false);
        }
        return null;
    }),
    FOG(card -> {
        card.getPlayer().getGame().getGameBoard().getRowForPlayer(1, card.getPlayer()).setWeatherBuffer(true);
        return null;
    }),
    FROST(card -> {
        card.getPlayer().getGame().getGameBoard().getRowForPlayer(0, card.getPlayer()).setWeatherBuffer(true);
        return null;
    }),
    RAIN(card -> {
        card.getPlayer().getGame().getGameBoard().getRowForPlayer(2, card.getPlayer()).setWeatherBuffer(true);
        return null;
    }),
    STORM(card -> {
        FOG.action.apply(card);
        RAIN.action.apply(card);
        //consider output of the previous
        return null;
    }),

    //leader actions,
    FOLTEST_SIEGE(card -> {
        AllCards.FOG.getAbstractCard().place(3, card.getPlayer());
        return null;
    }),
    FOLTEST_STEEL(card -> {
        AllCards.CLEAR.getAbstractCard().place(3, card.getPlayer());
        return null;
    }),
    FOLTEST_KING(card -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(2, card.getPlayer());
        return null;
    }),

    EMHYR_EMPERIAL(card -> {
       AllCards.RAIN.getAbstractCard().place(3, card.getPlayer());
        return null;
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
        return null;
    }),
    EMHYR_WHITEFLAME(card -> {
        //play at the begining of the game
       Player opponent = card.getPlayer().getGame().getOpposition();
       opponent.getLeader().setHasPlayedAction(true);
       return null;
    }),
    EMHYR_RELENTLESS(abstractCard -> {
        Player opponent = abstractCard.getPlayer().getGame().getOpposition();
        //todo
        //sent to secket a request to ask for the choosing card interface
        return null;
    }),
    EMHYR_INVADER(abstractCard -> {
        //play it at the begining of the game
        abstractCard.getPlayer().getGame().setRandomMedic(true);
        return null;
    }),

    ERIDIN_COMMANDER(abstractCard -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(0, abstractCard.getPlayer());
        return null;
    }),
    ERIDIN_BRINGER(abstractCard -> {
        //medic
        //todo
        return null;
    }),
    ERIDIN_DESTROYER(abstractCard -> {
        //discard two cards
        //draw one card of choice from deck
        return null;
    }),
    ERIDIN_KING(abstractCard -> {
        //pick any weather card and play it instantly
        //sends a response to pick a weather card then recieves the card
        //and plays it(outside lambda)
        return null;
    }),
    ERIDIN_TREACHEROUS(abstractCard -> {
        abstractCard.getPlayer().getGame().getGameBoard().setDoubleSpyPower();
        return null;
    }),
    FRANCESCA_QUEEN(abstractCard -> {
        SCORCH_C.execute(abstractCard);
        //make sure to include the above response
        return null;
    }),
    FRANCESCA_BEAUTIFUL(abstractCard -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(1, abstractCard.getPlayer());
        return null;
    }),
    FRANCESCA_DAISY(abstractCard -> {
        //send a response thet action has been treegered
        return null;
    }),
    FRANCESCA_PUREBLOOD(abstractCard -> {
        AllCards.FROST.getAbstractCard().place(3, abstractCard.getPlayer());
        return null;
    }),
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
        return null;
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
        return new ActionResponse();
    }),
    KING_BRAN(abstractCard -> {
        abstractCard.getPlayer().getGame().getGameBoard().setHalfAttrition();
        return new ActionResponse();
    }),

    NO_ACTION(card -> null),
    ;
    private final Function<AbstractCard, ActionResponse> action;

    Action(Function<AbstractCard, ActionResponse> action) {
        this.action = action;
    }

    public ActionResponse execute(AbstractCard card) {
        return action.apply(card);
    }
}
