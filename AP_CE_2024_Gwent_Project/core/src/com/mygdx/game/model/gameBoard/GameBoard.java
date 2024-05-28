package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {
    private ArrayList<TwoSideRow> rows;
    private Discard discard;
    private ArrayList<SpellCard> spellCards;

    public GameBoard(Player player1, Player player2) {
        rows = new ArrayList<>(3);
        discard = new Discard(player1, player2);
        spellCards = new ArrayList<>();
    }

    public void addPlayableToBoard(Player player, Rows row, PlayableCard card) {
        rows.get(row.getPosition()).addPlayableCard(player,card);
    }

    public ArrayList<PlayableCard> allPlayerPlayableCards(Player player) {
        ArrayList<PlayableCard> cards = new ArrayList<>();
        for(TwoSideRow i: rows) {
            cards.addAll(i.getAllCards(player));
        }
        return cards;
    }

    public void addSpellCard(Player player,SpellCard spellCard, int row) {
        spellCards.add(spellCard);
        rows.get(row).addSpellCard(player, spellCard);
    }
}

class TwoSideRow {
    private HashMap<Player,Row> subRows;
    private SpellCard weatherCard;
    public TwoSideRow(Player player1, Player player2) {
        this.subRows = new HashMap<>(2);
        subRows.put(player1,new Row());
        subRows.put(player2,new Row());
    }
    public void addSpellCard(Player player, SpellCard spellCard) {
        if(AllCards.COMMANDER_HORN.getAbstractCard().equals(spellCard)) {
            subRows.get(player).addCommanderHorn();
            return;
        }
        this.weatherCard = spellCard;
    }
    public void addPlayableCard(Player player, PlayableCard playableCard) {
        subRows.get(player).addCard(playableCard);
    }

    public ArrayList<PlayableCard> getAllCards(Player player) {
        return subRows.get(player).getCards();
    }
}

class Row {
    private ArrayList<PlayableCard> cards;
    private boolean isCommanderHornPresent;

    public Row() {
        this.cards = new ArrayList<>();
    }

    public void addCard(PlayableCard card) {
        cards.add(card);
    }
    public void addCommanderHorn() {
        isCommanderHornPresent = true;
    }

    public ArrayList<PlayableCard> getCards() {
        return cards;
    }
}

class Discard {
    private HashMap<Player, ArrayList<PlayableCard>> discard;
    public Discard(Player player1, Player player2) {
        discard = new HashMap<>(2);
        discard.put(player1, new ArrayList<>());
        discard.put(player2, new ArrayList<>());
    }
}

