package com.mygdx.game.model.user;

import com.google.gson.annotations.Expose;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.CommanderCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Player {

    private transient final User user;
    private CommanderCard leader;
    private LinkedList<String> deck;
    private LinkedList<String> hand;
    private String username;
    private Faction faction;
    private int roundsLost;
    private boolean won;
    private boolean isPassed;
    private transient Game game;

    public Player(User user) {
        this.leader = user.getLeaderAsCard();
        this.faction = user.getFaction();
        this.user = user;
        this.username = user.getUsername();
        this.hand = new LinkedList<>();
        this.roundsLost = 0;
        this.isPassed = false;

        this.deck = new LinkedList<>(user.getDeck());
        Collections.shuffle(deck);
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

    public LinkedList<String> getDeck() {
        return deck;
    }

    public LinkedList<AbstractCard> getDeckAsCards() {
        LinkedList<AbstractCard> deckCards = new LinkedList<>();
        for (String cardName : deck) {
            deckCards.add(AllCards.getCardByCardName(cardName));
        }
        return deckCards;
    }



    public LinkedList<String> getHand() {
        return hand;
    }
    public LinkedList<AbstractCard> getHandAsCards() {
        LinkedList<AbstractCard> deckCards = new LinkedList<>();
        for (String cardName : deck) {
            deckCards.add(AllCards.getCardByCardName(cardName));
        }
        return deckCards;
    }

    public void reDraw(int cardIndex) {
        deck.add(hand.remove(cardIndex));
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
            hand.add(deck.getFirst());
            deck.removeFirst();
        }
        return this;
    }

    public void addCardsToDeck(ArrayList<AbstractCard> cards) {
        for(AbstractCard c: cards) {
            deck.add(c.getName());
        }
    }

    public void loseRound() {
        roundsLost++;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public int getHealth() {
        return 2 - roundsLost;
    }
    public void removeCardFromHand(AbstractCard card) {
        hand.remove(card.getName());
    }

    public boolean doesNotHaveGameToPlay() {
        return leader.HasPlayedAction() && hand.isEmpty();
    }
}
