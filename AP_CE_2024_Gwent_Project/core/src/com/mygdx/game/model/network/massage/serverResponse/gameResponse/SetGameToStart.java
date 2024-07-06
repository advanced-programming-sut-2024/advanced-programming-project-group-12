package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class SetGameToStart extends ServerResponse {
    private Game game;
    public SetGameToStart(Game game) {
        super(ServerResponseType.START_GAME, null);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
