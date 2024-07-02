package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.network.session.InvalidSessionException;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.network.session.SessionExpiredException;

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

    public Session getSession() throws SessionExpiredException, InvalidSessionException {
        if(session.isExpired()) throw new SessionExpiredException();
        if(session.isValid()) throw new InvalidSessionException();
        return session;
    }
}
