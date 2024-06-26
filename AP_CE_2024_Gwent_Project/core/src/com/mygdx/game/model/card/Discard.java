package com.mygdx.game.model.card;

import com.mygdx.game.model.Player;

import java.util.ArrayList;

public class Discard {
    private ArrayList<AbstractCard> cards;
    public Discard() {
        cards = new ArrayList<AbstractCard>();
    }

    public ArrayList<AbstractCard> getDiscardCards(Player player) {
        return cards;
    }
}
