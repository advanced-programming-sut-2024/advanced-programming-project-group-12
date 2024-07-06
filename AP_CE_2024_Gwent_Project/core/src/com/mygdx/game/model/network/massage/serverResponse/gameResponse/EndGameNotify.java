package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class EndGameNotify extends ServerResponse {
    private boolean hasWinner;
    private String winner;

    public EndGameNotify( boolean hasWinner, String winner) {
        super(ServerResponseType.END_GAME, null);
        this.hasWinner = hasWinner;
        this.winner = winner;
    }

    public boolean isHasWinner() {
        return hasWinner;
    }

    public String getWinner() {
        return winner;
    }
}
