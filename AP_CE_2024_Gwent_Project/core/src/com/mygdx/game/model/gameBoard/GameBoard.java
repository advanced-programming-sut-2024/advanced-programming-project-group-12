package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GameBoard {
    private ArrayList<TwoSideRow> rows;

    public GameBoard() {
        rows = new ArrayList<>(3);
    }

    public void addPlayableToBoard(Player player, Rows row, PlayableCard card) {
        rows.get(row.getPosition()).addPlayableCard(player,card);
    }

    public ArrayList<AbstractCard> allPlayerPlayableCards(Player player) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for(TwoSideRow i: rows) {
            cards.addAll(i.getAllCards(player));
        }
        return cards;
    }
}

class TwoSideRow {
    private HashMap<Player,Row> subRows;
    private SpellCard spellCard;

    public TwoSideRow() {
        this.subRows = new HashMap<>();
        this.spellCard = null;
    }
    public void addSpellCard(Player player, SpellCard spellCard) {
        if(AllCards.COMMANDER_HORN.getAbstractCard().equals(spellCard)) {
            subRows.get(player).addCommanderHorn();
            return;
        }
        this.spellCard = spellCard;
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

