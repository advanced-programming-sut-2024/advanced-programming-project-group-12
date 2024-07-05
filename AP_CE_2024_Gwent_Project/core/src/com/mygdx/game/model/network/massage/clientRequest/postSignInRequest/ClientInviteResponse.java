package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.game.card.CommanderCards;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.util.LinkedList;

public class ClientInviteResponse extends ClientRequest {
    private String invitor;
    private String response;

    private Faction faction;
    private LinkedList<AbstractCard> deck;
    private CommanderCard commanderCard;

    public ClientInviteResponse(String invitor) {
        super(ClientRequestType.INVITE_ANSWER);
        this.invitor = invitor;
        this.response = "reject";
    }

    public ClientInviteResponse(Faction faction, LinkedList<AbstractCard> deck, CommanderCard commanderCard, String invitor) {
        super(ClientRequestType.INVITE_ANSWER);
        this.response = "accept";
        this.invitor = invitor;
        this.faction = faction;
        this.deck = deck;
        this.commanderCard = commanderCard;
    }

    public String getInvitor() {
        return invitor;
    }

    public String getResponse() {
        return response;
    }

    public Faction getFaction() {
        return faction;
    }

    public LinkedList<AbstractCard> getDeck() {
        return deck;
    }

    public CommanderCard getCommanderCard() {
        return commanderCard;
    }
}
