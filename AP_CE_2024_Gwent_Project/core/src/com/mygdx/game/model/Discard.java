package com.mygdx.game.model;

import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AbstractCard;

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
