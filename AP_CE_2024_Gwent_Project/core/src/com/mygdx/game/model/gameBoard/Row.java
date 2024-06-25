package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.Hero;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;

import java.util.ArrayList;
import java.util.HashSet;

public class Row {
    private ArrayList<PlayableCard> cards;
    private HashSet<SpellCard> spellCards;
    private int morale;
    private boolean hasHorn;
    private boolean hasMushroom;
    private boolean hasWeatherBuffer;

    public Row() {
        this.cards = new ArrayList<>();
        spellCards = new HashSet<>();
        hasMushroom = false;
        morale = 0;
        hasWeatherBuffer = false;
    }

    public void addCard(AbstractCard card) {
        if(card instanceof PlayableCard) {
            cards.add((PlayableCard) card);
        } else if(card instanceof SpellCard) {
            spellCards.add((SpellCard) card);
        }
        cards.sort(null);
    }

    public ArrayList<PlayableCard> getCards() {
        return cards;
    }

    public void removeCard(PlayableCard playableCard) {
        cards.remove(playableCard);
    }
    public void removeCard(SpellCard spellCard) {
        spellCards.remove(spellCard);
    }

    public void increaseMorale() {
        morale++;
    }
    
    public void setHasMushroom() {
        hasMushroom= true;
    }
    public boolean hasMushroom() {
        return hasMushroom;
    }

    public boolean HasWeatherBuffer() {
        return hasWeatherBuffer;
    }

    public void setWeatherBuffer(boolean hasWeatherBuffer) {
        this.hasWeatherBuffer = hasWeatherBuffer;
    }

    public boolean hasHorn() {
        return hasHorn;
    }

    public void setHorn(boolean hasHorn) {
        this.hasHorn = hasHorn;
    }

    public int calculatePowerOfPlayableCard(PlayableCard playableCard) {
        int power = playableCard.getPower();
        if(playableCard instanceof Hero) {
            return power;
        }
        if(hasWeatherBuffer) {
            power = 1;
        }
        if(hasHorn) {
            power *= 2;
        }
        if(playableCard.getAction().equals(Action.MORALE)) {
            power += morale -1;
        }
        else {
            power += morale;
        }
        return power;
    }
}
