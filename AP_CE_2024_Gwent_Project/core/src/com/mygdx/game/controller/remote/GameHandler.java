package com.mygdx.game.controller.remote;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.game.card.CommanderCards;
import com.mygdx.game.model.game.card.Decoy;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.PlayCardRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.PlayDecoyRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ReDrawResponse;
import com.mygdx.game.model.network.massage.serverResponse.ChatInGameWrapper;
import com.mygdx.game.model.network.massage.serverResponse.GetPublicGamesResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.*;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class GameHandler {
    private static final ArrayList<GameHandler> allGames = new ArrayList<>();
    private final boolean isPrivate = false;

    private User user1;
    private User user2;
    private Game game;

    private ArrayList<User> spectators;

    public static ServerResponse sendAllGamesList() {
        ArrayList<String> games = new ArrayList<>();
        for(GameHandler i: allGames) {
            if(!i.isPrivate) {
                games.add(i.user1.getUsername()+ " : " + i.user2.getUsername() );
            }
        }
        return new GetPublicGamesResponse(games);
        //
    }

    public GameHandler(User user1) {
        this.user1 = user1;
    }

    public void addUserAndStart(User user) {
        this.user2 = user;
        allGames.add(this);
        spectators = new ArrayList<>();
        start();
    }

    private void start() {
        user1.setPlayer(new Player(user1));
        user2.setPlayer(new Player(user2));
        game = new Game(user1.getPlayer(), user2.getPlayer(), this);

        RequestHandler.allUsers.get(user1.getUsername()).sendMassage(new SetGameToStart(game));
        RequestHandler.allUsers.get(user2.getUsername()).sendMassage(new SetGameToStart(game));

        if(user1.getFaction().equals(Faction.SCOIATAEL) && !user2.getFaction().equals(Faction.SCOIATAEL)) {
            RequestHandler.allUsers.get(user1.getUsername()).sendMassage(new TurnDecideRequest());
        }
        else if(!user1.getFaction().equals(Faction.SCOIATAEL) && user2.getFaction().equals(Faction.SCOIATAEL)) {
            RequestHandler.allUsers.get(user2.getUsername()).sendMassage(new TurnDecideRequest());
        }
        else {
            //let current player start
            letCurrentPlayerPlay(user1.getPlayer().getUsername());
        }
    }

    public void letCurrentPlayerPlay(String playerToStartName) {
        Player playerToStart = user1.getUsername().equals(playerToStartName)? user1.getPlayer(): user2.getPlayer();
        game.setCurrentPlayer(playerToStart);
        User otherUser = getTheOtherUser(playerToStart.getUser());
        game.setOpposition(otherUser.getPlayer());
        RequestHandler.allUsers.get(playerToStart.getUsername()).sendMassage(new ReDrawRequest(playerToStart.getHand(), true));
        RequestHandler.allUsers.get(otherUser.getUsername()).sendMassage(new ReDrawRequest(otherUser.getPlayer().getHand(), false));
    }

    public void handleChat(ChatInGame chat, User user) {
        if(chat.isCheat()) {
            cheatHandler(chat, user);
        } else {
            User toUser = getTheOtherUser(user);
            RequestHandler.allUsers.get(toUser.getUsername()).sendMassage(new ChatInGameWrapper(chat));
            sendMassageToSpectators(new ChatInGameWrapper(chat));
        }
    }

    private void cheatHandler(ChatInGame chat, User user) {
        switch (chat.getMassage()) {
            case "ali jan <3":
                break;
            case "ktkh":
                break;
            case "hemasian esfehan":
                break;
            case "zallnejan babol" :

        }
    }

    public void spectatorChatHandle(ChatInGame chat, User user) {
        ChatInGameWrapper wrapper = new ChatInGameWrapper(chat);
        RequestHandler.allUsers.get(user1.getUsername()).sendMassage(wrapper);
        RequestHandler.allUsers.get(user2.getUsername()).sendMassage(wrapper);

        for(User spectator: spectators) {
            RequestHandler.allUsers.get(spectator.getUsername()).sendMassage(wrapper);
        }
    }

    public void addAsAnSpectator(User user ) {
        spectators.add(user);
    }

    public void sendMassageToSpectators(ServerResponse serverResponse) {
        for(User user: spectators) {
            RequestHandler.allUsers.get(user.getUsername()).sendMassage(serverResponse);
        }
    }

    public void updateOpponent() {
        RequestHandler.allUsers.get(game.getOpposition().getUsername()).sendMassage(new PlayCardResponse(game));
    }

    public ServerResponse playCard(PlayCardRequest playCardRequest, User user) {
        Player player = user.getPlayer();
        boolean isEnemyPassed = player.getGame().getOpposition().isPassed();
        AbstractCard abstractCard = CommanderCards.getCardByCardName(playCardRequest.getCard());
        if(abstractCard == null) {
            abstractCard = AllCards.getCardByCardName(playCardRequest.getCard());
        }
        PlayCardResponse response = abstractCard.place(playCardRequest.getRow(), player);
        response.setGame(game);
        if(isEnemyPassed) {
            response.setPermission(true);
        }
        if(response.isPermission()) {
            System.out.println(game);
            RequestHandler.allUsers.get(getTheOtherUser(user).getUsername()).sendMassage(new PlayCardResponse(game));
        }
        return response;
    }

    private User getTheOtherUser(User user) {
        if(user == user1) return user2;
        else if(user == user2) return user1;
        else return null;
    }

    public ServerResponse reDraw(Player player, ReDrawResponse response) {
        for(String card: response.getRemovedCards()) {
            player.reDraw(card);
        }
        if(player.equals(game.getCurrentPlayer())) {
            return new PlayCardResponse(game, true);
        }
        else {
            return new PlayCardResponse(game);
        }
    }

    public void gameAborted(User user) {
        User otherUser = getTheOtherUser(user);
        if(RequestHandler.allUsers.get(otherUser.getUsername()) == null) return;
        game.finishGame(otherUser);
    }

    public Game getGame() {
        return game;
    }

    public ServerResponse playDecoy(PlayDecoyRequest playDecoyRequest, User user) {
        return ((Decoy) AllCards.DECOY.getAbstractCard()).place(playDecoyRequest.getRow(),playDecoyRequest.getToBeReplace(), user.getPlayer());
    }
}
