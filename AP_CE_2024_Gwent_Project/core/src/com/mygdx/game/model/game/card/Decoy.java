package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.GameBoard;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;
import com.mygdx.game.model.user.Player;

import java.util.List;

public class Decoy extends PlayableCard{
    public Decoy(String name, String description, Action action, List<Integer> rows, int power, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, power, typeNumber, faction);
    }

    public PlayCardResponse place(int row, String toBeReplaced , Player player) {
        super.place(row, player);
        GameBoard gameBoard = player.getGame().getGameBoard();
        gameBoard.getRowForPlayer(row, player).replaceByDecoy(toBeReplaced);
        return new PlayCardResponse(player.getGame(), doAction());
    }
}
