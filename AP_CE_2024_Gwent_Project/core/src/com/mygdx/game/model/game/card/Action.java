package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.CardSelectHandler;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.GameBoard;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponseType;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayTurnPermission;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.game.Row;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
        int maxPower = allPlayableCards.getFirst().getPower();
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
        card.getPlayer().getGame().switchTurn();
        return new ActionResponse(ActionResponseType.SCORCH, cardsToBeScorched);
    }),
    SCORCH_S(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 2);
        if(gameBoard.getRowStrength(opposition, 2)  < 10) return null;

        row.sort(null); //sort cards based on power
        ArrayList<PlayableCard> cardsToBeKilled = new ArrayList<>();
        for(PlayableCard i: row) {
            if(i.getPower() == row.getFirst().getPower()) {
                cardsToBeKilled.add(i);
            } else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeKilled) {
            i.kill();
        }
        card.getPlayer().getGame().switchTurn();

        return new ActionResponse(ActionResponseType.SCORCH, cardsToBeKilled);
    }),
    SCORCH_R(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 1);
        if(gameBoard.getRowStrength(opposition,1)  < 10) return null;

        row.sort(null); //sort cards based on power
        ArrayList<PlayableCard> cardsToBeKilled = new ArrayList<>();
        for(PlayableCard i: row) {
            if(i.getPower() == row.getFirst().getPower()) {
                cardsToBeKilled.add(i);
            } else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeKilled) {
            i.kill();
        }
        card.getPlayer().getGame().switchTurn();

        return new ActionResponse(ActionResponseType.SCORCH, cardsToBeKilled);
    }),
    SCORCH_C(card -> {
        Player opposition = card.getPlayer().getGame().getOpposition();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        ArrayList<PlayableCard> row = gameBoard.getRowCards(opposition, 0);
        if(gameBoard.getRowStrength(opposition, 0)  < 10) return null;

        row.sort(null); //sort cards based on power
        ArrayList<PlayableCard> cardsToBeKilled = new ArrayList<>();
        for(PlayableCard i: row) {
            if(i.getPower() == row.getFirst().getPower()) {
                cardsToBeKilled.add(i);
            } else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeKilled) {
            i.kill();
        }
        card.getPlayer().getGame().switchTurn();

        return new ActionResponse(ActionResponseType.SCORCH, cardsToBeKilled);
    }),
    TIGHT_BOND(card -> {
        card.getPlayer().getGame().switchTurn();
        return new ActionResponse(ActionResponseType.TIGHT_BOND);
    }),
    MEDIC(card -> {
        card.getPlayer().getGame().setCardSelectHandler(CardSelectHandler.MEDIC);
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();

        return new ActionResponse(ActionResponseType.SELECTION,gameBoard.getDiscardPlayableCards(card.getPlayer()) ,1);
    }),
    SPY(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        player.drawCard().drawCard();
        card.getPlayer().getGame().switchTurn();

        return null;
    }),
    MORALE(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        gameBoard.increaseMorale(card.getRow(), card.getPlayer());
        card.getPlayer().getGame().switchTurn();

        return null;
    }),
    COW(card -> {
        PlayableCard playableCard = (PlayableCard) card;
        if(playableCard.isDead()) {
            playableCard.getLegacyCard().place(card.getRow(), card.getPlayer());
        }
        card.getPlayer().getGame().switchTurn();

        return null;
    }),
    HORN(card -> {
        Row row = card.getPlayer().getGame().getGameBoard().getRowForPlayer(card.getRow(), card.getPlayer());
        row.setHorn(true);
        card.getPlayer().getGame().switchTurn();

        return null;
    }),
    MUSKET(card -> {
        Player player = card.getPlayer().getGame().getCurrentPlayer();
        LinkedList<AbstractCard> deck = player.getDeckAsCards();
        int row = card.getRow();
        System.out.println("in musket:");
        System.out.println("card name: " + card.getAbsName());
        for (int i = 0; i< deck.size(); i++) {
            System.out.println(deck.get(i).getAbsName());
            AbstractCard musket = deck.get(i);
            if (musket.getAbsName().equals(card.getAbsName())) {
                player.getDeck().remove(i);
                if (musket.getAllowableRows().contains(row)) {
                    return musket.place(row, card.getPlayer()).getActionResponse();
                } else {
                    return musket.place(deck.get(i).getDefaultRow(), card.getPlayer()).getActionResponse();
                }
            }
        }

        LinkedList<AbstractCard> hand = player.getHandAsCards();
        for (AbstractCard i : hand) {
            if (i.getAbsName().equals(card.getAbsName())) {
                if (i.getAllowableRows().contains(row)) {
                    return i.place(row, card.getPlayer()).getActionResponse();
                } else {
                    return i.place(i.getDefaultRow(), i.getPlayer()).getActionResponse();
                }
            }
        }
        card.getPlayer().getGame().switchTurn();

        return new ActionResponse(ActionResponseType.MUSKET);
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
        card.getPlayer().getGame().switchTurn();

        return new ActionResponse(ActionResponseType.BEAR);
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
        card.getPlayer().getGame().switchTurn();

        return new ActionResponse(ActionResponseType.MUSHROOM);
    }),
    DECOY(abstractCard -> {
        //todo
        abstractCard.getPlayer().getGame().switchTurn();
        return null;
    }),

    //weather actions
    CLEAR(card -> {
        GameBoard gameBoard = card.getPlayer().getGame().getGameBoard();
        gameBoard.clearWeather();
        for(int i = 0; i< 3; i++ ){
            Row row = gameBoard.getRowForPlayer(i, card.getPlayer());
            row.setWeatherBuffer(false);
        }
        card.getPlayer().getGame().switchTurn();
        return new ActionResponse(ActionResponseType.CLEAR);
    }),
    FOG(card -> {
        card.getPlayer().getGame().getGameBoard().getRowForPlayer(1, card.getPlayer()).setWeatherBuffer(true);
        card.getPlayer().getGame().switchTurn();
        return new ActionResponse(ActionResponseType.FOG);
    }),
    FROST(card -> {
        card.getPlayer().getGame().getGameBoard().getRowForPlayer(0, card.getPlayer()).setWeatherBuffer(true);
        card.getPlayer().getGame().switchTurn();
        return new ActionResponse(ActionResponseType.FROST);
    }),
    RAIN(card -> {
        card.getPlayer().getGame().getGameBoard().getRowForPlayer(2, card.getPlayer()).setWeatherBuffer(true);
        card.getPlayer().getGame().switchTurn();
        return new ActionResponse(ActionResponseType.RAIN);
    }),
    STORM(card -> {
        FOG.action.apply(card);
        RAIN.action.apply(card);
        card.getPlayer().getGame().switchTurn();
        return new ActionResponse(ActionResponseType.STORM);
    }),

    //leader actions,
    FOLTEST_SIEGE(card -> {
        AllCards.FOG.getAbstractCard().place(3, card.getPlayer());
        card.getPlayer().getGame().switchTurn();
        return null;
    }),
    FOLTEST_STEEL(card -> {
        AllCards.CLEAR.getAbstractCard().place(3, card.getPlayer());
        card.getPlayer().getGame().switchTurn();
        return null;
    }),
    FOLTEST_KING(card -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(2, card.getPlayer());
        card.getPlayer().getGame().switchTurn();
        return null;
    }),

    EMHYR_EMPERIAL(card -> {
       AllCards.RAIN.getAbstractCard().place(3, card.getPlayer());
        card.getPlayer().getGame().switchTurn();
        return null;
    }),
    EMHYR_EMPEROR(card -> {
        Player opponent = card.getPlayer().getGame().getOpposition();
        LinkedList<AbstractCard> hand =(LinkedList<AbstractCard>) opponent.getHandAsCards().clone();
        ArrayList<AbstractCard> toBeShown = new ArrayList<>(3);
        for(int i = 0; i< 3 ;i++) {
            int index = (int) (hand.size() * Math.random());
            toBeShown.add(hand.get(index));
            hand.remove(index);
        }
        card.getPlayer().getGame().switchTurn();

        //add mech to show the array list
        return new ActionResponse(ActionResponseType.EMHYR_EMPEROR, toBeShown);
    }),
    EMHYR_WHITEFLAME(card -> {
        //play at the begining of the game
       Player opponent = card.getPlayer().getGame().getOpposition();
       opponent.getLeader().setHasPlayedAction(true);
        return null;
    }),
    EMHYR_RELENTLESS(abstractCard -> {
        Player opponent = abstractCard.getPlayer().getGame().getOpposition();
        List<PlayableCard> enemyDiscard = opponent.getGame().getGameBoard().getDiscardPlayableCards(opponent);
        //sent to secket a request to ask for the choosing card interface
        abstractCard.getPlayer().getGame().setCardSelectHandler(CardSelectHandler.ENEMY_MEDIC);
        return new ActionResponse(ActionResponseType.SELECTION, enemyDiscard, 1);
    }),
    EMHYR_INVADER(abstractCard -> {
        //play it at the begining of the game
        abstractCard.getPlayer().getGame().setRandomMedic(true);
        abstractCard.getPlayer().getGame().switchTurn();
        return null;
    }),
    ERIDIN_COMMANDER(abstractCard -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(0, abstractCard.getPlayer());
        abstractCard.getPlayer().getGame().switchTurn();
        return null;
    }),
    ERIDIN_BRINGER(MEDIC.action),
    ERIDIN_DESTROYER(abstractCard -> {
        //discard two cards
        //draw one card of choice from deck
        abstractCard.getPlayer().getGame().setCardSelectHandler(CardSelectHandler.ERIDIN_DESTROYER_DISCARD);
        return new ActionResponse(ActionResponseType.SELECTION, abstractCard.getPlayer().getHandAsCards(), 2);
    }),
    ERIDIN_KING(abstractCard -> {
        ArrayList<AbstractCard> selectionList = new ArrayList<>();
        selectionList.add(AllCards.CLEAR.getAbstractCard());
        selectionList.add(AllCards.RAIN.getAbstractCard());
        selectionList.add(AllCards.FOG.getAbstractCard());
        selectionList.add(AllCards.FROST.getAbstractCard());
        selectionList.add(AllCards.STORM.getAbstractCard());

        abstractCard.getPlayer().getGame().setCardSelectHandler(CardSelectHandler.ERIDIN_KING);

        return new ActionResponse(ActionResponseType.SELECTION, selectionList);
    }),
    ERIDIN_TREACHEROUS(abstractCard -> {
        abstractCard.getPlayer().getGame().getGameBoard().setDoubleSpyPower();
        abstractCard.getPlayer().getGame().switchTurn();
        return null;
    }),
    FRANCESCA_QUEEN(SCORCH_C::execute),
    FRANCESCA_BEAUTIFUL(abstractCard -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(1, abstractCard.getPlayer());
        abstractCard.getPlayer().getGame().switchTurn();
        return null;
    }),
    FRANCESCA_DAISY(abstractCard -> {
        //send a response that action has been triggered
        return null;
    }),
    FRANCESCA_PUREBLOOD(abstractCard -> {
        AllCards.FROST.getAbstractCard().place(3, abstractCard.getPlayer());
        abstractCard.getPlayer().getGame().switchTurn();
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

            card.replaceBetweenTwoRows(maxRow);
        }
        abstractCard.getPlayer().getGame().switchTurn();
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
        abstractCard.getPlayer().getGame().switchTurn();
        return null;
    }),
    KING_BRAN(abstractCard -> {
        abstractCard.getPlayer().getGame().getGameBoard().setHalfAttrition();
        abstractCard.getPlayer().getGame().switchTurn();
        return null;
    }),

    NO_ACTION(card -> {
        card.getPlayer().getGame().switchTurn();
        return null;
    }),
    ;

    private final Function<AbstractCard, ActionResponse> action;

    Action(Function<AbstractCard, ActionResponse> action) {
        this.action = action;
    }

    public ActionResponse execute(AbstractCard card) {
        return action.apply(card);
    }
}
