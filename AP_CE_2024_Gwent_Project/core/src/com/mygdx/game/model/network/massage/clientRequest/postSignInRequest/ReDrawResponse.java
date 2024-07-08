package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

import java.util.List;

public class ReDrawResponse extends ClientRequest {
    List<String> removedCards;
    public ReDrawResponse(List<String> removedCards) {
        super(ClientRequestType.RE_DRAW);
        this.removedCards = removedCards;
    }

    public List<String> getRemovedCards() {
        return removedCards;
    }
}
