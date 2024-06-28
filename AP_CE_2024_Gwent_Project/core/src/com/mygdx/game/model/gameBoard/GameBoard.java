package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.*;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.Discard;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;

import java.util.*;

public class GameBoard {
    private HashMap<Player, ArrayList<Row>> rows;
    private HashMap<Player, Discard> discard;
    private HashSet<SpellCard> weatherCards;


    public GameBoard(Player player1, Player player2) {
        rows = new HashMap<>(2);
        rows.put(player1, new ArrayList<>(3));
        rows.put(player2, new ArrayList<>(3));
        discard = new HashMap<>(2);
        discard.put(player1, new Discard());
        discard.put(player2, new Discard());
        weatherCards = new HashSet<>();
    }

    public GameBoard(HashMap<Player, ArrayList<Row>> rows, HashMap<Player, Discard> discard, HashSet<SpellCard> weatherCards) {
        this.rows = new HashMap<>();
        for(Player p: rows.keySet()) {
            this.rows.put(p, rows.get(p));
        }

        this.discard = new HashMap<>();
        for(Player p: rows.keySet()) {
            this.discard.put(p, new Discard(discard.get(p)));
        }

        this.weatherCards = weatherCards;
    }

    public void setDoubleSpyPower() {
        for(Player i : rows.keySet()) {
            for(Row j : rows.get(i)) {
                j.setDoubleSpyPower(true);
            }
        }
    }

    public void setHalfAttrition() {
        for(Player i : rows.keySet()) {
            for(Row j : rows.get(i)) {
                j.setHalfAttrition(true);
            }
        }
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

    public int getRowStrength(Player player, int rowNumber) {
        ArrayList<PlayableCard> cards = getRowCards(player, rowNumber);
        Row row = rows.get(player).get(rowNumber);
        int totalStrength = 0;
        for(PlayableCard i: cards) {
            totalStrength += row.calculatePowerOfPlayableCard(i);
        }
        return totalStrength;
    }
    public int getRowStrength(Row row) {
        ArrayList<PlayableCard> cards = row.getCards();
        int totalStrength = 0;
        for(PlayableCard i: cards) {
            totalStrength += row.calculatePowerOfPlayableCard(i);
        }
        return totalStrength;
    }

    public int getPlayerStrength(Player player) {
        int totalStrength = 0;
        for(Row r: rows.get(player)) {
            totalStrength += getRowStrength(r);
        }
        return totalStrength;
    }

    public void increaseMorale(int row) {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        rows.get(player).get(row).increaseMorale();
    }


    public ArrayList<AbstractCard> getDiscardCards(Player player) {
        return discard.get(player).getDiscardCards();
    }

    public ArrayList<PlayableCard> getDiscardPlayableCards(Player player) {
        return discard.get(player).getPlayableCards();
    }

    public void reset() {
        weatherCards = new HashSet<>();

        ArrayList<PlayableCard> cowCards = new ArrayList<>();

        for(Player p: rows.keySet()) {
            for(Row r: rows.get(p)) {
                for(PlayableCard c: r.getCards()) {
                    if(c.getAction().equals(Action.COW)) {
                        cowCards.add(c);
                    }
                    c.kill();
                }
            }
            rows.put(p, new ArrayList<>(Arrays.asList(new Row(), new Row(), new Row())));
        }

        for(PlayableCard c: cowCards) {
            c.doAction();
        }
    }

    public void resetDiscard(Player player) {
        discard.put(player, new Discard(discard.get(player)));
    }

    public GameBoard copy() {
        return new GameBoard(rows, discard, weatherCards);
    }

}


