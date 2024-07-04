package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.user.User;

public class ServerPlayInvite extends ServerResponse {
    private StartGameRequest clientRequest;

    public ServerPlayInvite( StartGameRequest req) {
        super(ServerResponseType.INVITE_TO_PLAY, null);
        this.clientRequest = req;
    }

    public StartGameRequest getClientRequest() {
        return clientRequest;
    }
}
