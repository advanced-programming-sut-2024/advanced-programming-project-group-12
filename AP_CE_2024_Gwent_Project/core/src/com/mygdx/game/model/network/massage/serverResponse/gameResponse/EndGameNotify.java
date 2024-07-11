package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.Round;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

import java.util.ArrayList;

public class EndGameNotify extends ServerResponse {
    private boolean hasWinner;
    private String winner;
    private Game game;

    public EndGameNotify( boolean hasWinner, String winner, Game game) {
        super(ServerResponseType.END_GAME, null);
        this.hasWinner = hasWinner;
        this.winner = winner;
        this.game = game;
    }

    public boolean isHasWinner() {
        return hasWinner;
    }

    public String getWinner() {
        return winner;
    }

    public Game getGame() {
        return game;
    }
}
