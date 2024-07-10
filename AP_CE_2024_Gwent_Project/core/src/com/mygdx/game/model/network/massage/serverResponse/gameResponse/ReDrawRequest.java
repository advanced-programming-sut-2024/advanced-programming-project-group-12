package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ReDrawRequest extends ServerResponse {
    private List<String> hand;
    private boolean permission;
    public ReDrawRequest(List<String> hand, boolean permission) {
        super(ServerResponseType.RE_DRAW, null);
        this.permission = permission;
        this.hand = hand;
    }

    public List<AbstractCard> getHandAsCards() {
        ArrayList<AbstractCard> hand = new ArrayList<>();
        for(String i: this.hand) {
            hand.add(AllCards.getCardByCardName(i));
        }
        return hand;
    }

    public List<String> getHand() {
        return hand;
    }

    public boolean isPermission() {
        return permission;
    }
}
