package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AbstractCard;

import java.util.ArrayList;
import java.util.HashMap;

public class Discard {
    private HashMap<Player, ArrayList<AbstractCard>> discard;
    public Discard(Player player1, Player player2) {
        discard = new HashMap<>(2);
        discard.put(player1, new ArrayList<>());
        discard.put(player2, new ArrayList<>());
    }

    public ArrayList<AbstractCard> getDiscardCards(Player player) {
        return discard.get(player);
    }
}
