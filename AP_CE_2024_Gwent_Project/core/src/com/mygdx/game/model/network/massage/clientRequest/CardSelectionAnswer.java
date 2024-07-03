package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.session.Session;

import java.util.List;

public class CardSelectionAnswer extends ClientRequest{
    private List<AbstractCard> selection;
    private int row;

    public CardSelectionAnswer(ClientRequestType type, Session session) {
        super(type, session);
    }

    public List<AbstractCard> getSelection() {
        return selection;
    }

    public int getRow() {
        return row;
    }
}
