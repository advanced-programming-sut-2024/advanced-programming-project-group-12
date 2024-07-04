package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponse;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;

import java.util.List;

public class CommanderCard extends AbstractCard{
    private boolean hasPlayedAction;

    public CommanderCard(String name, String description, Action action, List<Integer> rows, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, typeNumber, faction);
        super.typeNumber = "";
        hasPlayedAction = false;
    }

    public void setHasPlayedAction(boolean hasPlayedAction) {
        this.hasPlayedAction = hasPlayedAction;
    }

    @Override
    public ActionResponse doAction() {
        if(!hasPlayedAction) {
            hasPlayedAction = true;
            return super.doAction();
        }
        //todo:
        //should the method return null?
        return new ActionResponse(null, null);
    }

    @Override
    public void kill() {}

    @Override
    public PlayCardResponse place(int row, Player player) {
        return new PlayCardResponse(this.player.getGame(), doAction());
    }

    public boolean HasPlayedAction() {
        return hasPlayedAction;
    }
}
