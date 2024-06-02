package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AbstractCard;

import java.util.List;

public class PlayableCard extends AbstractCard {
    private int power;
    private int row;
    public PlayableCard(String name, String description, Action action, List<Integer> rows , int power) {
        super(name, description, action, rows);
        this.power = power;
    }

    public static void updatePowers() {
    }

    public int getPower() {
        return power;
    }

    @Override
    public void kill() {

    }

    @Override
    public void place(int row) {
        doAction();
        Player player;
        if(action.equals(Action.SPY)) {
            player = Game.getCurrentGame().getOpposition();
        }
        else {
            player = Game.getCurrentGame().getCurrentPlayer();
        }
        Game.getCurrentGame().getGameBoard().addCard(player, row, this);
    }
}
