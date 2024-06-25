package com.mygdx.game.model;

import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.gameboard.GameBoard;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.gameboard.Row;

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
        Player opposition = Game.getCurrentGame().getOpposition();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        ArrayList<PlayableCard> allOppositionPlayableCards = gameBoard.allPlayerPlayableCards(opposition);

        if(allOppositionPlayableCards.isEmpty()) {
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
    SCORCH_S(card -> {
        Player opposition = Game.getCurrentGame().getOpposition();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
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
        Player opposition = Game.getCurrentGame().getOpposition();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
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
        Player opposition = Game.getCurrentGame().getOpposition();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
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
    }),
    MEDIC(card -> {
        // should open a menu in game screen to choose from one card of the below list
        Player player = Game.getCurrentGame().getCurrentPlayer();
        ArrayList<AbstractCard> discard = Game.getCurrentGame().getGameBoard().getDiscard(player);
        AbstractCard chosenCard;
//        chosenCard.place();
    }),
    SPY(card -> {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        player.drawCard().drawCard();
    }),
    MORALE(card -> {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        gameBoard.increaseMorale(card.getRow());
    }),
    COW(card -> {
        if(((PlayableCard) card).isDead()) {
            //avenger.place(row);
        }
    }),
    HORN(card -> {
        Row row = Game.getCurrentGame().getGameBoard().getRowForCurrentPlayer(card.getRow());
        row.setHorn(true);
    }),
    HORN_CARD(card -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(card.getRow());
    }),
    MUSKET(card -> {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        LinkedList<AbstractCard> deck = player.getDeck();
        int row = card.getRow();
        for (AbstractCard i : deck) {
            if (i.getName().equals(card.getName())) {
                deck.remove(i);
                if (i.getAllowableRows().contains(row)) {
                    i.place(row);
                } else {
                    i.place(i.getDefaultRow());
                }
            }
        }

        LinkedList<AbstractCard> hand = player.getHand();
        for (AbstractCard i : hand) {
            if (i.getName().equals(card.getName())) {
                if (i.getAllowableRows().contains(row)) {
                    i.place(row);
                } else {
                    i.place(i.getDefaultRow());
                }
            }
        }
    }),
    BEAR(card -> {
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        Player player = Game.getCurrentGame().getCurrentPlayer();
        int rowNumber = card.getRow();
        Row row = gameBoard.getRowForCurrentPlayer(rowNumber);
        if(row.hasMushroom()) {
            card.kill();
            ((PlayableCard) card).getLegacyCard().place(card.getDefaultRow());
        }
    }),
    MUSHROOM(card -> {
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
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
    DECOY(null),

    //weather actions
    CLEAR(card -> {
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        for(int i = 0; i< 3; i++ ){
            Row row = gameBoard.getRowForCurrentPlayer(i);
            row.setWeatherBuffer(false);
        }
    }),
    FOG(card -> Game.getCurrentGame().getGameBoard().getRowForCurrentPlayer(1).setWeatherBuffer(true)
    ),
    FROST(card -> Game.getCurrentGame().getGameBoard().getRowForCurrentPlayer(0).setWeatherBuffer(true)),
    RAIN(card -> Game.getCurrentGame().getGameBoard().getRowForCurrentPlayer(2).setWeatherBuffer(true)),
    STORM(card -> {
        FOG.action.accept(null);
        RAIN.action.accept(null);
    }),
    //faction actions
    NORTHERN_REALMS(card -> {
        Player player1 = Game.getCurrentGame().getCurrentPlayer();
        Player player2 = Game.getCurrentGame().getOpposition();
        if(player1.isWon() && player1.getFaction().equals(Faction.NORTHERN_REALMS)) {
            player1.drawCard();
        } else if (player2.isWon() && player2.getFaction().equals(Faction.NORTHERN_REALMS)) {
            player2.drawCard();
        }
    }),
    NILFGAARD(card -> {
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

    //leader actions,
    FOLTEST_SIEGE(card -> {
        AllCards.FOG.getAbstractCard().place(3);
    }),
    FOLTEST_STEEL(card -> {
        AllCards.CLEAR.getAbstractCard().place(3);
    }),
    FOLTEST_KING(card -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(2);
    }),

    EMHYR_EMPERIAL(card -> {
       AllCards.RAIN.getAbstractCard().place(3);
    }),
    EMHYR_EMPEROR(card -> {
        Player opponent = Game.getCurrentGame().getOpposition();
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
       Player opponent = Game.getCurrentGame().getOpposition();
       opponent.getLeader().setHasPlayedAction(true);
    }),
    EMHYR_RELENTLESS(abstractCard -> {
        Player opponent = Game.getCurrentGame().getOpposition();
        //todo
        //sent to secket a request to ask for the choosing card interface
    }),
    EMHYR_INVADER(abstractCard -> {

    }),

    ERIDIN_COMMANDER(abstractCard -> {
        AllCards.COMMANDER_HORN.getAbstractCard().place(0);
    }),
    ERIDIN_BRINGER(abstractCard -> {
        //todo
    }),
    ERIDIN_DESTROYER(abstractCard -> {

    }),
    ERIDIN_KING(abstractCard -> {

    }),
    ERIDIN_TREACHEROUS(abstractCard -> {
        Game.getCurrentGame().getGameBoard().setDoubleSpyPower();
    }),

    FRANCESCA_QUEEN(abstractCard -> SCORCH_C.execute(null)),
    FRANCESCA_BEAUTIFUL(abstractCard -> AllCards.COMMANDER_HORN.getAbstractCard().place(1)),
    FRANCESCA_DAISY(abstractCard -> {
        //probably has to be hardcoded
    }),
    FRANCESCA_PUREBLOOD(abstractCard -> AllCards.FROST.getAbstractCard().place(3)),
    FRANCESCA_HOPE(abstractCard -> {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
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
        Game currentGame = Game.getCurrentGame();
        GameBoard gameBoard = currentGame.getGameBoard();

        ArrayList<Player> players = new ArrayList<>(2);
        players.add(Game.getCurrentGame().getCurrentPlayer());
        players.add(Game.getCurrentGame().getOpposition());

        for(Player i : players) {
            ArrayList<AbstractCard> discard = gameBoard.getDiscard(i);
            Collections.shuffle(discard);
            i.addCardsToDeck(discard);
            gameBoard.resetDiscard(i);
        }
    }),
    KING_BRAN(abstractCard -> Game.getCurrentGame().getGameBoard().setHalfAttrition()
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
