package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.AllCards;
import com.mygdx.game.model.PlayableCard;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.SpellCard;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {
    private ArrayList<twoSideRow> rows;

    public GameBoard() {
        rows = new ArrayList<>(3);
    }

    public void addPlayableToBoard(Player player, Rows row, PlayableCard card) {
        rows.get(row.getPosition()).addPlayableCard(player,card);
    }
}

class twoSideRow {
    private HashMap<Player,Row> subRows;
    private SpellCard spellCard;

    public twoSideRow() {
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
}

class Row {
    ArrayList<PlayableCard> cards;
    boolean isCommanderHornPresent;

    public void addCard(PlayableCard card) {
        cards.add(card);
    }
    public void addCommanderHorn() {
        isCommanderHornPresent = true;
    }
}

