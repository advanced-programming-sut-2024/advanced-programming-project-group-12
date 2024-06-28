package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.network.massage.Session;

public class ClientRequest {
    private ClientRequestType type;
    private Session session;

    public ClientRequest(ClientRequestType type, Session session) {
        this.type = type;
        this.session = session;
    }

    public ClientRequestType getType() {
        return type;
    }
}
