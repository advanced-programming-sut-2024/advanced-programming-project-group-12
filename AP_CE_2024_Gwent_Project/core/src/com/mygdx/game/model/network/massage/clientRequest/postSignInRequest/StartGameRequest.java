package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedList;

public class StartGameRequest extends ClientRequest {
    private String invitor;
    private String toBeInvited;
    private boolean randomOpponent;

    private Faction faction;
    private LinkedList<String> deck;
    private CommanderCard commanderCard;

    private boolean isPrivate;
    private boolean isTournament;

    public StartGameRequest(String toBeInvited, String invitor, User user) {
        super(ClientRequestType.START_GAME, null);
        this.toBeInvited = toBeInvited;
        this.invitor = invitor;
        randomOpponent = false;

        this.faction = user.getFaction();
        this.deck = new LinkedList<>(user.getDeck());
        this.commanderCard = user.getLeaderAsCard();
        isTournament = false;
        isPrivate = false;
    }

    public StartGameRequest(String toBeInvited, String invitor, User user, boolean isPrivate) {
        super(ClientRequestType.START_GAME, null);
        this.toBeInvited = toBeInvited;
        this.invitor = invitor;
        randomOpponent = false;

        this.faction = user.getFaction();
        this.deck = new LinkedList<>(user.getDeck());
        this.commanderCard = user.getLeaderAsCard();
        this.isTournament = false;
        this.isPrivate = isPrivate;
    }

    public StartGameRequest(String toBeInvited, String invitor, User user, boolean isTournament, boolean isPrivate) {
        super(ClientRequestType.START_GAME, null);
        this.toBeInvited = toBeInvited;
        this.invitor = invitor;
        randomOpponent = false;

        this.faction = user.getFaction();
        this.deck = new LinkedList<>(user.getDeck());
        this.commanderCard = user.getLeaderAsCard();
        this.isTournament = isTournament;
        isPrivate = false;
    }

    public String getUserToBeInvited() {
        return toBeInvited;
    }

    public String getInvitor() {
        return invitor;
    }

    public Faction getFaction() {
        return faction;
    }

    public LinkedList<String> getDeck() {
        return deck;
    }

    public CommanderCard getCommanderCard() {
        return commanderCard;
    }

    public boolean isRandomOpponent() {
        return randomOpponent;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isTournament() {
        return isTournament;
    }
}
