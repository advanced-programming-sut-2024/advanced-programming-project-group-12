package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.network.massage.CardPlayAnimation;
import com.mygdx.game.model.network.massage.Session;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;

public class PlayCardRequest extends ClientRequest {
    AbstractCard card;
    int row;


    public PlayCardRequest(ClientRequestType type, Session session) {
        super(type, session);
    }
}
