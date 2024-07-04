package com.mygdx.game.model.game;

import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.game.card.AbstractCard;

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
