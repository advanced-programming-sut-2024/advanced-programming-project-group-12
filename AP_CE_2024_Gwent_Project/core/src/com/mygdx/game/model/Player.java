package com.mygdx.game.model;

import com.mygdx.game.model.card.CommanderCard;
import com.mygdx.game.model.card.AbstractCard;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private User user;
    private CommanderCard leader;
    private ArrayList<AbstractCard> deck;
    private LinkedList<AbstractCard> hand;
    private Faction faction;
    private int roundsLost;
    private boolean won;

    public Player(User user, CommanderCard leader, ArrayList<AbstractCard> deck, Faction faction) {
        this.user = user;
        this.leader = leader;
        this.deck = deck;
        this.faction = faction;
        hand = new LinkedList<>();
        this.roundsLost = 0;
    }

    public ArrayList<AbstractCard> getDeck() {
        return deck;
    }

    public LinkedList<AbstractCard> getHand() {
        return hand;
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
        Player player = Game.getCurrentGame().getCurrentPlayer();
        // Implement card drawing logic
        return player;
    }
}
