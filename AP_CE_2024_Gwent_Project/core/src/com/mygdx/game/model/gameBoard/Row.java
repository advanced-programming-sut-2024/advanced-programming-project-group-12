package com.mygdx.game.model.gameBoard;

import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.card.SpellCard;

import java.util.ArrayList;
import java.util.HashSet;

public class Row {
    private ArrayList<PlayableCard> cards;
    private HashSet<SpellCard> spellCards;
    private int morale;
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
}
