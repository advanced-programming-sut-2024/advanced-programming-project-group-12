package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;

import java.lang.reflect.Executable;

public class ServerResponse {
    private ServerResponseType type;
    Session session;

    public ServerResponse(ServerResponseType type, Session session) {
        this.type = type;
        this.session = session;
    }
    public ServerResponse(Exception exception) {

        type = null;
        session = null;
    }

    public ServerResponseType getType() {
        return type;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
