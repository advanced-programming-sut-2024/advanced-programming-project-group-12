package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientInviteResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class ServerInviteResponse extends ServerResponse {
    ClientInviteResponse response;
    public ServerInviteResponse(ClientInviteResponse response) {
        super(ServerResponseType.INVITE_TO_PLAY_RESPONSE, null);
        this.response = response;
    }
}
