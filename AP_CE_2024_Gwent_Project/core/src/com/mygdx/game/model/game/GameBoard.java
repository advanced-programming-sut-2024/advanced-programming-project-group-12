package com.mygdx.game.model.game;

import com.mygdx.game.model.game.card.*;
import com.mygdx.game.model.game.card.Discard;
import com.mygdx.game.model.user.Player;

import java.util.*;

public class GameBoard {
    private HashMap<String, ArrayList<Row>> rows;
    private HashMap<String, Discard> discard;
    private HashSet<SpellCard> weatherCards;

    public GameBoard(Player player1, Player player2) {
        rows = new HashMap<>(2);
        rows.put(player1.getUsername(), new ArrayList<>(3));
        rows.put(player2.getUsername(), new ArrayList<>(3));
        for(int i = 0; i < 3; i++) {
            rows.get(player1.getUsername()).add(new Row());
            rows.get(player2.getUsername()).add(new Row());
        }
        discard = new HashMap<>(2);
        discard.put(player1.getUsername(), new Discard());
        discard.put(player2.getUsername(), new Discard());
        weatherCards = new HashSet<>();
    }

    public GameBoard(HashMap<String, ArrayList<Row>> rows, HashMap<String, Discard> discard, HashSet<SpellCard> weatherCards) {
        this.rows = new HashMap<>();
        for(String p: rows.keySet()) {
            this.rows.put(p, rows.get(p));
        }

        this.discard = new HashMap<>();
        for(String p: rows.keySet()) {
            this.discard.put(p, new Discard(discard.get(p)));
        }

        this.weatherCards = weatherCards;
    }

    public int getCardFinalPower(PlayableCard playableCard) {
        Row row = rows.get(playableCard.getPlayer().getUsername()).get(playableCard.getRow());
        return row.calculatePowerOfPlayableCard(playableCard);
    }

    public void setDoubleSpyPower() {
        for(String i : rows.keySet()) {
            for(Row j : rows.get(i)) {
                j.setDoubleSpyPower(true);
            }
        }
    }

    public void setHalfAttrition() {
        for(String i : rows.keySet()) {
            for(Row j : rows.get(i)) {
                j.setHalfAttrition(true);
            }
        }
    }

    public void addCard(Player player, int row, PlayableCard card) {
        rows.get(player.getUsername()).get(row).addCard(card);
    }

    public void addCard(Player player, int row, SpellCard spellCard) {
        if(row == 3) {
            weatherCards.add(spellCard);
        }
        else {
            rows.get(player.getUsername()).get(row).addCard(spellCard);
        }
    }

    public ArrayList<PlayableCard> allPlayerPlayableCards(Player player) {
        ArrayList<PlayableCard> cards = new ArrayList<>();
        for(Row i: rows.get(player.getUsername())) {
            cards.addAll(i.getCards());
        }
        return cards;
    }

    public ArrayList<PlayableCard> getRowCards(Player player, int row) {
        /*
         * returns all cards associated with a row of index row and for the player.
         */
        if(row < 3 && row >= 0) {
            return rows.get(player.getUsername()).get(row).getCards();
        }
        else {
            System.err.println("Invalid row input");
            return null;
        }
    }

    public ArrayList<Row> getAllRowsForPlayer(Player player) {
        return rows.get(player.getUsername());
    }

    public Row getRowForPlayer(int row, Player player) {
        /**
         * returns a row object of the current player based on the int row provided;
         */
        return rows.get(player.getUsername()).get(row);
    }

    public HashSet<SpellCard> getWeatherCards() {
        return weatherCards;
    }

    public int getRowStrength(Player player, int rowNumber) {
        return getRowStrength(rows.get(player.getUsername()).get(rowNumber));
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
        for(Row r: rows.get(player.getUsername())) {
            totalStrength += getRowStrength(r);
        }
        return totalStrength;
    }

    public void increaseMorale(int row, Player player) {
        rows.get(player.getUsername()).get(row).increaseMorale();
    }

    public Discard getDiscard(Player player) {
        return discard.get(player.getUsername());
    }

    public ArrayList<AbstractCard> getDiscardCards(Player player) {
        return discard.get(player.getUsername()).getDiscardCards();
    }

    public ArrayList<PlayableCard> getDiscardPlayableCards(Player player) {
        return discard.get(player.getUsername()).getPlayableCards();
    }

    public void reset() {
        weatherCards = new HashSet<>();

        ArrayList<PlayableCard> cowCards = new ArrayList<>();

        for(String p: rows.keySet()) {
            for(Row r: rows.get(p)) {
                for(PlayableCard c: r.getCards()) {
                    discard.get(p).addCard(c);
                    if(c.getAction().equals(Action.COW)) {
                        cowCards.add(c);
                    }
                }
            }
            rows.put(p, new ArrayList<>(Arrays.asList(new Row(), new Row(), new Row())));
        }

        for(PlayableCard c: cowCards) {
            c.doAction();
        }
    }

    public void resetDiscard(Player player) {
        discard.put(player.getUsername(), new Discard(discard.get(player.getUsername())));
    }

    public GameBoard copy() {
        return new GameBoard(rows, discard, weatherCards);
    }

    public void clearWeather() {
        weatherCards = new HashSet<>();
    }


}


