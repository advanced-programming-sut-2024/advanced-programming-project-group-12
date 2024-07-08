package com.mygdx.game.model.game;

import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.PlayableCard;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.CardSelectionAnswer;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponseType;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;
import com.mygdx.game.model.user.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public enum CardSelectHandler {
    MEDIC((cardSelectionAnswer, player) -> {
        List<AbstractCard> abstractCards = cardSelectionAnswer.getSelection();
        if(abstractCards.isEmpty()) {
            player.getGame().switchTurn();
            return new PlayCardResponse(player.getGame());
        }
//        if(abstractCards.size() != 1) return null;

        player.getGame().switchTurn();
        player.getGame().setCardSelectHandler(null);
        return ((PlayableCard)abstractCards.getFirst()).revive();
    }),
    ERIDIN_DESTROYER_ADD((cardSelectionAnswer, player) -> {
        List<AbstractCard> abstractCards = cardSelectionAnswer.getSelection();
        if(abstractCards.isEmpty()) {
            player.getGame().switchTurn();
            return new PlayCardResponse(player.getGame());
        }
        //if(abstractCards.size() != 1) return new ServerResponse(new InvalidRequestException());

        player.getDeckAsCards().add(abstractCards.getFirst());
        player.getGame().setCardSelectHandler(null);
        return new PlayCardResponse(player.getGame(), null);
    }),
    ERIDIN_DESTROYER_DISCARD(((cardSelectionAnswer, player) -> {
        List<AbstractCard> abstractCards = cardSelectionAnswer.getSelection();
        if(abstractCards.isEmpty()) {
            player.getGame().switchTurn();
            return new PlayCardResponse(player.getGame());
        }
        //if(abstractCards.size() != 2) return new ServerResponse(new InvalidRequestException());

        for(AbstractCard card : abstractCards) {
            card.discard();
        }

        player.getGame().setCardSelectHandler(ERIDIN_DESTROYER_ADD);
        return new PlayCardResponse(player.getGame(), new ActionResponse(ActionResponseType.SELECTION, player.getDeckAsCards(), 1));
    })),
    ERIDIN_KING((answer,player) -> {
        List<AbstractCard> abstractCards = answer.getSelection();
        if(abstractCards.isEmpty()) {
            player.getGame().switchTurn();
            return new PlayCardResponse(player.getGame());
        }
        //if(abstractCards.size() != 1) return new ServerResponse(new InvalidRequestException());
        player.getGame().setCardSelectHandler(null);
        return abstractCards.getFirst().place(3, player);
    }),
    ENEMY_MEDIC((cardSelectionAnswer, player) -> {
        List<AbstractCard> abstractCards = cardSelectionAnswer.getSelection();
        if(abstractCards.isEmpty()) {
            player.getGame().switchTurn();
            return new PlayCardResponse(player.getGame());
        }
        //if(abstractCards.size() != 1) return new ServerResponse(new InvalidRequestException());
        ArrayList<AbstractCard>discardCards = player.getGame().getGameBoard().getDiscardCards(player.getGame().getOpposition());
        //might get problems due to difference in references, if so work with indexes
        AbstractCard abstractCard = abstractCards.getFirst();
        discardCards.remove(abstractCard);
        player.getGame().setCardSelectHandler(null);
        return abstractCard.place(abstractCard.getRow(), player);
    });
    private final BiFunction<CardSelectionAnswer, Player, ServerResponse> handler;

    CardSelectHandler(BiFunction<CardSelectionAnswer,Player ,ServerResponse> handler) {
        this.handler = handler;
    }
    public ServerResponse handle(CardSelectionAnswer a, Player p) {
        return handler.apply(a, p);
    }
}
