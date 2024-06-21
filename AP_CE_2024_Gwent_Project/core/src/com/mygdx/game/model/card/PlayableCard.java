package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.gameBoard.GameBoard;

import java.util.List;

public class PlayableCard extends AbstractCard {
    private int power;
    private int row;
    public PlayableCard(String name, String description, Action action, List<Integer> rows , int power, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, typeNumber, faction);
        this.power = power;
    }

    public static void updatePowers() {
    }

    public int getPower() {
        return power;
    }

    @Override
    public void kill() {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        gameBoard.removeCard(this, row, player);
    }

    @Override
    public void place(int row) {
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
    }
}
