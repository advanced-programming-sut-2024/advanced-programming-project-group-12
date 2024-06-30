package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;

import java.util.List;

public class SpellCard extends AbstractCard {
    public SpellCard(String name, String description, Action action, List<Integer> rows, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, typeNumber, faction);
    }

    @Override
    public PlayCardResponse place(int row, Player player) {
        doAction();
        player.getGame().getGameBoard().addCard(player, row, this);
        return null;
    }

    @Override
    public void kill() {

    }
}
