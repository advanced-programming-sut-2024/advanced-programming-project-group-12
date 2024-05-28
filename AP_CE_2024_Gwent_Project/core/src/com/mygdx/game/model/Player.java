package com.mygdx.game.model;

import java.util.ArrayList;

public class Player {
    private User user;
    private CommanderCard leader;
    private ArrayList<AbstractCard> deck;
    private Faction faction;
    private int roundsLost;
    private boolean won;

    public Player(User user, CommanderCard leader, ArrayList<AbstractCard> deck, Faction faction) {
        this.user = user;
        this.leader = leader;
        this.deck = deck;
        this.faction = faction;
        this.roundsLost = 0;
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

    public void drawCard() {
    }
}
