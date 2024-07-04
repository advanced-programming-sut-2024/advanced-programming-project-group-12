package com.mygdx.game.model.network.massage.clientRequest;

public class ClientRequest {
    private ClientRequestType type;

    public ClientRequest(ClientRequestType type) {
        this.type = type;
    }

    public ClientRequestType getType() {
        return type;
    }


}
