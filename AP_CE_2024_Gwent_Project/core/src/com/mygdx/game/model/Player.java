package com.mygdx.game.model;

import java.util.ArrayList;

public class Player extends User {
    private CommanderCard leader;
    private ArrayList<AbstractCard> deck;
    private Faction faction;
    private int roundsLost;

    public Player(String username, String nickname, String password, String email, SecurityQuestion question, String answer, CommanderCard leader, ArrayList<AbstractCard> deck, Faction faction, int roundsLost) {
        super(username, nickname,password, email, question, answer);


    }}
