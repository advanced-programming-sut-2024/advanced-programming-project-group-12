package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.*;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GameBoard {
    private HashMap<Player, ArrayList<Row>> rows;
    private Discard discard;
    private HashSet<SpellCard> weatherCards;
    private boolean doubleSpyPower;

    public GameBoard(Player player1, Player player2) {
        rows = new HashMap<>(2);
        rows.put(player1, new ArrayList<>());
        rows.put(player2, new ArrayList<>());
        discard = new Discard(player1, player2);
        weatherCards = new HashSet<>();
    }

    public void setDoubleSpyPower(boolean doubleSpyPower) {
        this.doubleSpyPower = doubleSpyPower;
    }

    public void addCard(Player player, int row, PlayableCard card) {
        rows.get(player).get(row).addCard(card);
    }

    public void addCard(Player player, int row, SpellCard spellCard) {
        rows.get(player).get(row).addCard(spellCard);
    }

    public ArrayList<PlayableCard> allPlayerPlayableCards(Player player) {
        ArrayList<PlayableCard> cards = new ArrayList<>();
        for(Row i: rows.get(player)) {
            cards.addAll(i.getCards());
        }
        return cards;
    }

    public ArrayList<PlayableCard> getRowCards(Player player, int row) {
        /**
         * returns all cards associated with a row of index row and for the player.
         */
        if(row < 3 && row >= 0) {
            return rows.get(player).get(row).getCards();
        }
        else {
            System.err.println("Inalid row input");
            return null;
        }
    }

    public ArrayList<Row> getAllRowsForPlayer(Player player) {
        return rows.get(player);
    }

    public Row getRowForCurrentPlayer(int row) {
        /**
         * returns a row object of the current player based on the int row provided;
         */
        Player player = Game.getCurrentGame().getCurrentPlayer();
        return rows.get(player).get(row);
    }

    public HashSet<SpellCard> getWeatherCards() {
        return weatherCards;
    }

    public int getRowStrength(int row) {
        //todo
        return 0;
    }

    public void increaseMorale(int row) {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        rows.get(player).get(row).increaseMorale();
    }


    public Discard getDiscard(Player player) {
        return discard;
    }

}


