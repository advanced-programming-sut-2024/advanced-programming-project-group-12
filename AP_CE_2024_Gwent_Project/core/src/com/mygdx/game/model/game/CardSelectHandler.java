package com.mygdx.game.model.game;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.PlayableCard;
import com.mygdx.game.model.network.massage.clientRequest.CardSelectionAnswer;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponseType;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.InvalidRequestException;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;
import com.mygdx.game.model.user.Player;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum CardSelectHandler {
    ERIDIN_DESTROYER_ADD((cardSelectionAnswer, player) -> {
        List<AbstractCard> abstractCards = cardSelectionAnswer.getSelection();
        if(abstractCards.size() != 1) return new ServerResponse(new InvalidRequestException());

        player.getDeck().add(abstractCards.get(0));
        player.getGame().switchTurn();
        return new PlayCardResponse(player.getGame(), null);
    }),
    ERIDIN_DESTROYER_DISCARD(((cardSelectionAnswer, player) -> {
        List<AbstractCard> abstractCards = cardSelectionAnswer.getSelection();
        if(abstractCards.size() != 2) return new ServerResponse(new InvalidRequestException());

        for(AbstractCard card : abstractCards) {
            card.discard();
        }

        player.getGame().setCardSelectHandler(ERIDIN_DESTROYER_ADD);
        return new PlayCardResponse(player.getGame(), new ActionResponse(ActionResponseType.SELECTION, player.getDeck(), 1));
    })),
    ERIDIN_KING((answer,player) -> {
        List<AbstractCard> abstractCards = answer.getSelection();
        if(abstractCards.size() != 1) return new ServerResponse(new InvalidRequestException());
        player.getGame().setCardSelectHandler(null);
        player.getGame().switchTurn();
        return abstractCards.get(0).place(answer.getRow(), player);
    })
    ;
    private BiFunction<CardSelectionAnswer, Player, ServerResponse> handler;

    CardSelectHandler(BiFunction<CardSelectionAnswer,Player ,ServerResponse> handler) {
        this.handler = handler;
    }

}
