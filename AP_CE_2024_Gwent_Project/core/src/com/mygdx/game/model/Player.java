package com.mygdx.game.model;

import java.util.ArrayList;

public class Player extends User {
    private CommanderCard leader;
    private ArrayList<AbstractCard> deck;
    private Faction faction;
    private int roundsLost;



    public Player(String username, String password, String email, int questionNumber, String answer, CommanderCard leader, ArrayList<AbstractCard> deck, Faction faction, int roundsLost) {
        super(username, password, email, questionNumber, answer);


    }}
