package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class JoinPublicGame extends ClientRequest {
    private String serverName;

    public JoinPublicGame(String serverName) {
        super(ClientRequestType.JOIN_AS_SPECTATOR);
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }
}
