package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;

import java.util.List;

public class SpellCard extends AbstractCard {
    public SpellCard(String name, String description, Action action, List<Integer> rows) {
        super(name, description, action, rows);
    }

    @Override
    public void place(int row) {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        doAction();
        if(allowableRows!= null && allowableRows.contains(row)) {
            Game.getCurrentGame().getGameBoard().addCard(player, row, this);
        }
        PlayableCard.updatePowers();
    }

    @Override
    public void kill() {

    }
}
