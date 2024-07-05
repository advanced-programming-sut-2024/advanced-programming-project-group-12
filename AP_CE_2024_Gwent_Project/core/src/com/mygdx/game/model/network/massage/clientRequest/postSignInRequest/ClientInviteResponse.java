package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

public class ClientInviteResponse extends ClientRequest {
    private String invitor;
    private String response;

    public ClientInviteResponse(Session session, String invitor, String response) {
        super(ClientRequestType.INVITE_ANSWER, session);
        this.invitor = invitor;
        this.response = response;
    }

    public String getInvitor() {
        return invitor;
    }

    public String getResponse() {
        return response;
    }
}
