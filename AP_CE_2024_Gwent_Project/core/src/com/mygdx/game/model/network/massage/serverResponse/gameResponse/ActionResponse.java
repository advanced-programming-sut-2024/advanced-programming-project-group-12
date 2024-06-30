package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.card.Action;
import com.mygdx.game.model.game.card.AbstractCard;

import java.util.List;

public class ActionResponse {
    private final Action action;
    private final String animationAssetPath;
    private final List<AbstractCard> affectedCards;

    public ActionResponse() {
        action = Action.NO_ACTION;
        animationAssetPath = null;
        affectedCards = null;
    }

    public ActionResponse(Action action, String animationAssetPath, List<AbstractCard> affectedCards) {
        this.action = action;
        this.animationAssetPath = animationAssetPath;
        this.affectedCards = affectedCards;
    }
}
