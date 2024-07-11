package com.mygdx.game.model.network.massage.clientRequest;

public class GetRandomGamesRequest extends ClientRequest{
    public GetRandomGamesRequest() {
        super(ClientRequestType.GET_RANDOM_GAMES);
    }
}
