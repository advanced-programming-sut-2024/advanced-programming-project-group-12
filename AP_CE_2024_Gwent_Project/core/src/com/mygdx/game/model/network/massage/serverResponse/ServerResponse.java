package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;

import java.lang.reflect.Executable;

public class ServerResponse {
    private ServerResponseType type;
    private Session session;

    public ServerResponse(ServerResponseType type, Session session) {
        this.type = type;
        this.session = session;
    }

    public ServerResponseType getType() {
        return type;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
