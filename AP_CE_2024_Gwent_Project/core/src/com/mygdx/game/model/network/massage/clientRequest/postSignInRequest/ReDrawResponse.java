package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

import java.util.List;

public class ReDrawResponse extends ClientRequest {
    List<AbstractCard> removedHand;
    public ReDrawResponse(List<AbstractCard> removedHand) {
        super(ClientRequestType.RE_DRAW);
        this.removedHand = removedHand;
    }

    public List<AbstractCard> getRemovedHand() {
        return removedHand;
    }
}
