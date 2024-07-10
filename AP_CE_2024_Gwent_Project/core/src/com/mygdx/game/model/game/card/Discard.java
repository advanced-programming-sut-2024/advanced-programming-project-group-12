package com.mygdx.game.model.game.card;

import java.util.ArrayList;

public class Discard {
    private ArrayList<String> playableCards;
    private ArrayList<String> cards;
    public Discard() {
        cards = new ArrayList<>();
        playableCards = new ArrayList<>();
    }
    public Discard(Discard discard) {
        cards = new ArrayList<>(discard.cards);
        playableCards = new ArrayList<>(discard.playableCards);
    }

    public void addCard(AbstractCard abstractCard) {
        cards.add(abstractCard.getName());
        if(abstractCard instanceof PlayableCard) {
            playableCards.add(abstractCard.getName());
        }
    }

    public ArrayList<AbstractCard> getDiscardCards() {
        ArrayList<AbstractCard> discardCards = new ArrayList<>();
        for(String i: cards) {
            discardCards.add(AllCards.getCardByCardName(i));
        }
        return discardCards;
    }

    public ArrayList<PlayableCard> getPlayableCards() {
        ArrayList<PlayableCard> playableCards = new ArrayList<>();
        for(String i: cards) {
            playableCards.add((PlayableCard) AllCards.getCardByCardName(i));
        }
        return playableCards;
    }

    public void removeCardFromName(String name) {
        playableCards.remove(name);
        cards.remove(name);
    }
}
