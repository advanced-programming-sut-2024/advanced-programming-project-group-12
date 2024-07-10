package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.network.session.Session;

public class JoinAsSpectatorResponse extends ServerResponse{
    private Game game;
    public JoinAsSpectatorResponse(Game game) {
        super(ServerResponseType.JOIN_AS_SPECTATOR, null);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
