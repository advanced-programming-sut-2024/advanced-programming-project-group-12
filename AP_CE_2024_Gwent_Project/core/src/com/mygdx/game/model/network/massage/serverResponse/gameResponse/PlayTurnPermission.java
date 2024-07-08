package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class PlayTurnPermission extends ServerResponse {
    private final Game game;

    public PlayTurnPermission(Game game) {
        super(ServerResponseType.PLAY_CARD_PERMISSION, null);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
