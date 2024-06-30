package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;

public class ServerResponse {
    private ServerResponseType type;
    Session session;

    public ServerResponse(ServerResponseType type) {
        this.type = type;
    }
    public ServerResponse(ServerResponseType type, Session session) {
        this(type);
        session.renewSession();
        this.session = session;
    }

    public ServerResponseType getType() {
        return type;
    }
}
