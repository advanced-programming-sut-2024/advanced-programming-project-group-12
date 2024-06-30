package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;

public class PlayCardResponse extends ServerResponse {
    ActionResponse actionResponse;
    public PlayCardResponse(ServerResponseType type) {
        super(type);
    }
}
