package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.session.Session;

import java.util.ArrayList;
import java.util.List;

public class CardSelectionAnswer extends ClientRequest {
    private List<String> selection;
    private int row;

    public CardSelectionAnswer(List<String> selection, int row) {
        super(ClientRequestType.CARD_SELECT_ANSWER);
        this.selection = selection;
        this.row = row;
    }

    public List<AbstractCard> getSelection() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for(String i: selection) {
            cards.add(AllCards.getCardByCardName(i));
        }
        return cards;
    }

    public int getRow() {
        return row;
    }
}
