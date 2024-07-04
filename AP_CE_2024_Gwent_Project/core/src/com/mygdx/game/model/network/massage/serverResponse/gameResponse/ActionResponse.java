package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.card.AbstractCard;

import java.util.LinkedList;
import java.util.List;

public class ActionResponse {
    private final ActionResponseType action;
    private final List<? extends AbstractCard> affectedCards;
    int actionCount;

    public ActionResponse(ActionResponseType action) {
        this.action = action;
        affectedCards = null;
    }

    public ActionResponse(ActionResponseType action, int actionCount) {
        this.action = action;
        this.actionCount = actionCount;
        affectedCards = null;
    }

    public ActionResponse(ActionResponseType action, List<? extends AbstractCard> affectedCards) {
        this.action = action;
        this.affectedCards = affectedCards;
    }

    public ActionResponse(ActionResponseType actionResponseType, List<? extends AbstractCard> selection, int actionCount) {
        this.action = actionResponseType;
        this.affectedCards = selection;
        this.actionCount = actionCount;
    }
}
