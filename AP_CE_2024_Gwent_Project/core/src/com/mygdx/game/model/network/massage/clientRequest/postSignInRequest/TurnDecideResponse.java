package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class TurnDecideResponse extends ClientRequest {
    private String playerToPlay;
    public TurnDecideResponse(String playerToPlay) {
        super(ClientRequestType.TURN_DECIDE);
        this.playerToPlay = playerToPlay;
    }

    public String getPlayerToPlay() {
        return playerToPlay;
    }
}
