package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Row;
import com.mygdx.game.model.*;

import java.util.ArrayList;
import java.util.List;

public class PlayableCard extends AbstractCard implements Comparable<PlayableCard>{
    private int power;
    private boolean isDead;
    private AbstractCard legacyCard;
    private boolean canBeReplaced;

    public PlayableCard(String name, String description, Action action, List<Integer> rows , int power, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, typeNumber, faction);
        canBeReplaced = rows != null && rows.size()> 1;
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

    public boolean isDead() {
        return isDead;
    }

    public boolean canBeReplaced() {
        return canBeReplaced;
    }

    public AbstractCard getLegacyCard() {
        return legacyCard.clone();
    }

    public void replace(int rowNumber) {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        Row row = Game.getCurrentGame().getGameBoard().getRowForCurrentPlayer(rowNumber);
        row.removeCard(this);

        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        gameBoard.addCard(player, rowNumber, this);
    }

    public void revive() {
        if(isDead) {
            isDead = false;
            place(super.row);
        }
    }

    @Override
    public void kill() {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        Row row = Game.getCurrentGame().getGameBoard().getRowForCurrentPlayer(super.row);
        row.removeCard(this);
        isDead = true;
    }

    @Override
    public void place(int row) {
        super.place(row);
        isDead = false;
        Player player;
        if(action.equals(Action.SPY)) {
            player = Game.getCurrentGame().getOpposition();
        }
        else {
            player = Game.getCurrentGame().getCurrentPlayer();
        }
        doAction();
        Game.getCurrentGame().getGameBoard().addCard(player, row, this);
        ArrayList<PlayableCard> boardRow = Game.getCurrentGame().getGameBoard().getRowCards(player, row);
        boardRow.sort(null);
    }

    @Override
    public int compareTo(PlayableCard o) {
        if(o.getPower() > power) return 1;
        else if(o.power == power) return 0;
        else return -1;
    }
}
