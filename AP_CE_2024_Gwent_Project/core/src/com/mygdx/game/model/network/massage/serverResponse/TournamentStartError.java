package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;

public class TournamentStartError extends ServerResponse{
    String error;
    public TournamentStartError(String error) {
        super(ServerResponseType.JOIN_TOURNAMENT_ERROR, null);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
