package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.*;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {
    private HashMap<Player, ArrayList<Row>> rows;
    private Discard discard;
    private ArrayList<SpellCard> weatherCards;

    public GameBoard(Player player1, Player player2) {
        rows = new HashMap<>(2);
        rows.put(player1, new ArrayList<>());
        rows.put(player2, new ArrayList<>());
        discard = new Discard(player1, player2);
        weatherCards = new ArrayList<>();
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

    public ArrayList<PlayableCard> getRow(Player player, int row) {
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

    public ArrayList<SpellCard> getWeatherCards() {
        return weatherCards;
    }

    public int getRowStrength(int row) {
        //todo
        return 0;
    }

    public ArrayList<AbstractCard> getDiscard(Player player) {
        return discard.getDiscard(player);
    }

}


class Row {
    private ArrayList<PlayableCard> cards;
    private ArrayList<SpellCard> spellCards;

    public Row() {
        this.cards = new ArrayList<>();
        spellCards = new ArrayList<>();
    }

    public void addCard(AbstractCard card) {
        if(card instanceof PlayableCard) {
            cards.add((PlayableCard) card);
        } else if(card instanceof SpellCard) {
            spellCards.add((SpellCard) card);
        }
        cards.sort(null);
    }

    public ArrayList<PlayableCard> getCards() {
        return cards;
    }
}

class Discard {
    private HashMap<Player, ArrayList<AbstractCard>> discard;
    public Discard(Player player1, Player player2) {
        discard = new HashMap<>(2);
        discard.put(player1, new ArrayList<>());
        discard.put(player2, new ArrayList<>());
    }

    public ArrayList<AbstractCard> getDiscard(Player player) {
        return discard.get(player);
    }
}

