package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.GameBoard;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponse;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.game.Row;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;

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

    public void replaceBetweenTwoRows(int rowNumber) {
        Row row = player.getGame().getGameBoard().getRowForPlayer(rowNumber, player);
        row.removeCard(this);

        GameBoard gameBoard = player.getGame().getGameBoard();
        gameBoard.addCard(player, rowNumber, this);
    }

    public PlayCardResponse revive() {
        isDead = false;
        player.getGame().getGameBoard().getDiscardCards(player).remove(this);
        return place(super.row, player);
    }

    @Override
    public void kill() {
        Row row = player.getGame().getGameBoard().getRowForPlayer(super.row, player);
        row.removeCard(this);
        player.getGame().getGameBoard().getDiscardCards(player).add(this);
        isDead = true;
    }

    @Override
    public PlayCardResponse place(int row, Player player) {
        super.place(row, player);
        this.isDead = false;

        Game game = player.getGame();

        if(action.equals(Action.SPY)) {
            this.player = game.getOpposition();
        }
        player.getGame().getGameBoard().addCard(player, row, this);
        ArrayList<PlayableCard> boardRow = player.getGame().getGameBoard().getRowCards(player, row);
        boardRow.sort(null);
        return new PlayCardResponse(player.getGame(), doAction());
    }

    @Override
    public int compareTo(PlayableCard o) {
        if(o.getPower() > power) return 1;
        else if(o.power == power) return 0;
        else return -1;
    }
}
