package com.mygdx.game.model;

import com.mygdx.game.model.card.CommanderCard;
import com.mygdx.game.model.card.AbstractCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Player {
    private final User user;
    private CommanderCard leader;
    private LinkedList<AbstractCard> deck;
    private LinkedList<AbstractCard> hand;
    private Faction faction;
    private int roundsLost;
    private boolean won;

    public Player(User user, CommanderCard leader, LinkedList<AbstractCard> deck, Faction faction) {
        this.user = user;
        this.leader = leader;
        this.faction = faction;
        hand = new LinkedList<>();
        this.roundsLost = 0;

        Collections.shuffle((LinkedList)deck.clone());
        this.deck = deck;
        for(int i = 0; i< 10 ; i++) {
            drawCard();
        }
    }

    public CommanderCard getLeader() {
        return leader;
    }

    public LinkedList<AbstractCard> getDeck() {
        return deck;
    }

    public LinkedList<AbstractCard> getHand() {
        return hand;
    }

    public void reDraw(int cardIndex) {
        deck.add(hand.remove(cardIndex));
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Player drawCard() {
        if(!deck.isEmpty()) {
            hand.add(deck.get(0));
            deck.remove(0);
        }
        return this;
    }

    public void addCardsToDeck(ArrayList<AbstractCard> cards) {
        deck.addAll(cards);
    }
}
