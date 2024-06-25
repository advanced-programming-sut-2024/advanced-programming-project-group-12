package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AbstractCard;

import java.util.ArrayList;
import java.util.HashMap;

public class Discard {
    private ArrayList<AbstractCard> cards;
    public Discard() {
        cards = new ArrayList<AbstractCard>();
    }

    public ArrayList<AbstractCard> getDiscardCards(Player player) {
        return cards;
    }
}
