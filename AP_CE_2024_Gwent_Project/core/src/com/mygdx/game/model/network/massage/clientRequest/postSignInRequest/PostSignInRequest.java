package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.session.InvalidSessionException;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.network.session.SessionExpiredException;

public abstract class PostSignInRequest extends ClientRequest{
    Session session;
    public PostSignInRequest(ClientRequestType type, Session session) {
        super(type);
        this.session = session;
    }
    public Session getSession() throws SessionExpiredException, InvalidSessionException {
        if(session.isExpired()) throw new SessionExpiredException();
        if(session.isValid()) throw new InvalidSessionException();
        return session;
    }
}
