package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.session.Session;

public class PlayCardRequest extends ClientRequest {
    private String card;
    private int row;

    public PlayCardRequest(Session session, int row , String abstractCard) {
        super(ClientRequestType.PLAY_CARD_REQUEST, session);
        this.row = row;
        this.card = abstractCard;
    }

    public String getCard() {
        return card;
    }

    public int getRow() {
        return row;
    }
}
