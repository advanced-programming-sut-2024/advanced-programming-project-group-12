package com.mygdx.game.model.user;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.CommanderCards;

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
    private boolean isPassed;
    private Game game;

    public Player(User user, CommanderCard leader, LinkedList<AbstractCard> deck, Faction faction) {
        this.leader = leader;
        this.faction = faction;
        this.user = user;
        this.hand = new LinkedList<>();
        this.roundsLost = 0;
        this.isPassed = false;


        Collections.shuffle((LinkedList)deck.clone());
        this.deck = deck;
        for(int i = 0; i< 10 ; i++) {
            drawCard();
        }

        if(CommanderCards.FRANCESCA_DAISY.getAbstractCard().equals(leader)) {
            leader.doAction();
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
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

    public void loseRound() {
        roundsLost++;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public int getHealth() {
        return 2 - roundsLost;
    }
    public void removeCardFromHand(AbstractCard card) {
        hand.remove(card);
    }

    public boolean doesNotHaveGameToPlay() {
        return leader.HasPlayedAction() && hand.isEmpty();
    }
}
