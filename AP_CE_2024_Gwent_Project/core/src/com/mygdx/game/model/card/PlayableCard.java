package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.gameBoard.GameBoard;

import java.util.ArrayList;
import java.util.List;

public class PlayableCard extends AbstractCard {
    private int power;
    private int row;
    private boolean isDead;
    private AbstractCard legacyCard;
    public PlayableCard(String name, String description, Action action, List<Integer> rows , int power, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, typeNumber, faction);
        this.power = power;
        legacyCard = null;
        this.isDead = false;
    }
    public PlayableCard(String name, String description, Action action, List<Integer> rows , int power, Integer typeNumber, Faction faction, PlayableCard legacyCard) {
        super(name, description, action, rows, typeNumber, faction);
        this.power = power;
        this.isDead = false;
        this.legacyCard = legacyCard;
    }

    public static void updatePowers() {
    }

    public int getPower() {
        return power;
    }

    public int getRow() {
        return row;
    }

    public boolean isDead() {
        return isDead;
    }

    public AbstractCard getLegacyCard() {
        return legacyCard.clone();
    }

    @Override
    public void kill() {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        gameBoard.removeCard(this, row, player);
        isDead = true;
    }

    @Override
    public void place(int row) {
        super.place(row);
        Player player;
        if(action.equals(Action.SPY)) {
            player = Game.getCurrentGame().getOpposition();
        }
        else {
            player = Game.getCurrentGame().getCurrentPlayer();
        }
        this.row = row;
        doAction();
        Game.getCurrentGame().getGameBoard().addCard(player, row, this);
        ArrayList<PlayableCard> boardRow = Game.getCurrentGame().getGameBoard().getRowCards(player, row);
        boardRow.sort(null);
    }
}
