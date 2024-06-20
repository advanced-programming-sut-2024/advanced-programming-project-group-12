package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.*;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;

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

    public void addCard(Player player, int row, AbstractCard card) {
        rows.get(row).addCard(player,card);
    }

    public void addCard(Player player, int row, SpellCard spellCard) {
        rows.get(row).addCard(player, spellCard);
    }

    public ArrayList<PlayableCard> allPlayerPlayableCards(Player player) {
        ArrayList<PlayableCard> cards = new ArrayList<>();
        for(TwoSideRow i: rows) {
            cards.addAll(i.getAllCards(player));
        }
        return cards;
    }

    public ArrayList<AbstractCard> getDiscard(Player player) {
        return discard.getDiscard(player);
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
    public void addCard(Player player, SpellCard spellCard) {
        if(AllCards.COMMANDER_HORN.getAbstractCard().equals(spellCard)) {
            subRows.get(player).addCommanderHorn();
            return;
        }
        this.weatherCard = spellCard;
    }
    public void addCard(Player player, PlayableCard playableCard) {
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

