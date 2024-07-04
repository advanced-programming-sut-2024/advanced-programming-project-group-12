package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.session.Session;

public class AnswerUserInvite extends PostSignInRequest{
    private boolean accept;
    public AnswerUserInvite(ClientRequestType type, Session session) {
        super(type, session);
    }

    public boolean isAccept() {
        return accept;
    }
}
