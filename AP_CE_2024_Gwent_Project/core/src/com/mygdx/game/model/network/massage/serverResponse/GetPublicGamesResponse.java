package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;

import java.util.ArrayList;

public class GetPublicGamesResponse extends ServerResponse{
    ArrayList<String> games;
    public GetPublicGamesResponse(ArrayList<String> games) {
        super(ServerResponseType.GET_PUBLIC_GAMES, null);
        this.games = games;
    }

    public ArrayList<String> getGames() {
        return games;
    }
}
