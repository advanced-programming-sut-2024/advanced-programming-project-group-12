package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientInviteResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

import java.util.List;

public class ServerInviteResponse extends ServerResponse {
    private ClientInviteResponse response;
    private List<String> randomGames;
    String string;
    public ServerInviteResponse(ClientInviteResponse response) {
        super(ServerResponseType.INVITE_TO_PLAY_RESPONSE, null);
        this.response = response;
    }

    public ServerInviteResponse(String string) {
        super(ServerResponseType.INVITE_TO_PLAY_RESPONSE, null);
        this.string = string;
    }

    public ServerInviteResponse(List<String> randomGames) {
        super(ServerResponseType.RANDOM_GAMES_LIST, null);
        this.randomGames = randomGames;
    }

    public ClientInviteResponse getResponse() {
        return response;
    }
}
