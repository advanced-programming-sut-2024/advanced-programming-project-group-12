package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

public class ClientInviteResponse extends PostSignInRequest{
    private User invitor;
    private String response;

    public ClientInviteResponse(Session session, User invitor, String response) {
        super(ClientRequestType.INVITE_ANSWER, session);
        this.invitor = invitor;
        this.response = response;
    }

    public User getInvitor() {
        return invitor;
    }

    public String getResponse() {
        return response;
    }
}
