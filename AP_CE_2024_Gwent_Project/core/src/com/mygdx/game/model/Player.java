package com.mygdx.game.model;

import java.util.ArrayList;

public class Player {
    private User user;
    private CommanderCard leader;
    private ArrayList<AbstractCard> deck;
    private Faction faction;
    private int roundsLost;

    public Player(User user, CommanderCard leader, ArrayList<AbstractCard> deck, Faction faction) {
        this.user = user;
        this.leader = leader;
        this.deck = deck;
        this.faction = faction;
        this.roundsLost = 0;
    }
}
