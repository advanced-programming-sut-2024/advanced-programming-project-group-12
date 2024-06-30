package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.session.Session;

public class PlayCardRequest extends ClientRequest {
    AbstractCard card;
    int row;


    public PlayCardRequest(ClientRequestType type, Session session) {
        super(type, session);
    }
}
