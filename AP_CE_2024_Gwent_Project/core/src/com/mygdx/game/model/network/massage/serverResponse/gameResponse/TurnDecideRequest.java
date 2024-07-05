package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class TurnDecideRequest extends ServerResponse {
    public TurnDecideRequest() {
        super(ServerResponseType.GAME_TURN_DECIDE, null);
    }
}
