package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.network.massage.CardPlayAnimation;
import com.mygdx.game.model.network.massage.clientRequest.StartGameInviteRequest;

import java.util.List;

public class PlayCardResponse extends ServerResponse{
    String cardPlayAnimation; // path to the png file of the play animation if null , no animation exists
    List<AbstractCard> cardList;
    Action action;
    public PlayCardResponse(ServerResponseType type) {
        super(type);
    }
}
