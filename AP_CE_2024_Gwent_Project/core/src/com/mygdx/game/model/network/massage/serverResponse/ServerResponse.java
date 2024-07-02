package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;

import java.lang.reflect.Executable;

public class ServerResponse {
    private Exception exception;
    private ServerResponseType type;
    Session session;

    public ServerResponse(ServerResponseType type) {
        this.type = type;
        exception = null;
    }
    public ServerResponse(ServerResponseType type, Session session) {
        this(type);
        this.session = session;
    }
    public ServerResponse(Exception exception) {
        this.exception = exception;
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
