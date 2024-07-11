package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientInviteResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;

import java.util.ArrayList;
import java.util.List;

public class ServerInviteResponse extends ServerResponse {
    private ArrayList<String> randomGames;
    String error;

    public ServerInviteResponse(String string) {
        super(ServerResponseType.START_GAME_ERROR, null);
        this.error = string;
    }

    public ServerInviteResponse(ArrayList<String> randomGames) {
        super(ServerResponseType.RANDOM_GAMES_LIST, null);
        this.randomGames = randomGames;
    }

    public ArrayList<String> getRandomGames() {
        return randomGames;
    }

    public String getError() {
        return error;
    }
}
