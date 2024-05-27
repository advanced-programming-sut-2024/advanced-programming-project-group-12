package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.AbstractCard;
import com.mygdx.game.model.PlayableCard;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.SpellCard;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {
    private ArrayList<DoubleRow> rows;

    public GameBoard() {
        rows = new ArrayList<>(3);
    }

    public void addCardToBoard(Player player, Rows row, AbstractCard card) {
    }
}

class DoubleRow {
    private HashMap<Player,Row> subRows;
    private SpellCard spellCard;

    public DoubleRow() {
        this.subRows = new HashMap<>();
        this.spellCard = null;
    }

}

class Row {
    ArrayList<PlayableCard> cards;
    boolean isCommanderHornPresent;
}

