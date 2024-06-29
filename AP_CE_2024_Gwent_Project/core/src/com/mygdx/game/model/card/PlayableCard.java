package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Row;
import com.mygdx.game.model.*;

import java.util.ArrayList;
import java.util.List;

public class PlayableCard extends AbstractCard {
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
        Row row = player.getGame().getGameBoard().getRowForCurrentPlayer(rowNumber);
        row.removeCard(this);

        GameBoard gameBoard = player.getGame().getGameBoard();
        gameBoard.addCard(player, rowNumber, this);
    }

    public void revive() {
        if(isDead) {
            isDead = false;
            place(super.row, player);
        }
    }

    @Override
    public void kill() {
        Row row = player.getGame().getGameBoard().getRowForCurrentPlayer(super.row);
        row.removeCard(this);
        isDead = true;
    }

    @Override
    public void place(int row, Player player) {
        super.place(row, player);
        this.isDead = false;

        Game game = player.getGame();

        if(action.equals(Action.SPY)) {
            this.player = game.getOpposition();
        }
        doAction();
        player.getGame().getGameBoard().addCard(player, row, this);
        ArrayList<PlayableCard> boardRow = player.getGame().getGameBoard().getRowCards(player, row);
        boardRow.sort(null);
    }
}
