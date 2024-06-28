package com.mygdx.game.model.card;

import com.mygdx.game.model.Player;

import java.util.ArrayList;

public class Discard {
    private ArrayList<PlayableCard> playableCards;
    private ArrayList<AbstractCard> cards;
    public Discard() {
        cards = new ArrayList<>();
        playableCards = new ArrayList<>();
    }

    public void addCard(AbstractCard abstractCard) {
        cards.add(abstractCard);
        if(abstractCard instanceof PlayableCard) {
            playableCards.add((PlayableCard) abstractCard);
        }
    }

    public ArrayList<AbstractCard> getDiscardCards() {
        return cards;
    }

    public ArrayList<PlayableCard> getPlayableCards() {
        return playableCards;
    }
}
