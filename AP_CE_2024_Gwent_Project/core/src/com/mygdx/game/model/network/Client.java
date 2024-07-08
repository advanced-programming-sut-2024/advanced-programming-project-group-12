package com.mygdx.game.model.network;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.ChatController;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.Round;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetAllUsersRequest;
import com.mygdx.game.model.network.massage.serverResponse.*;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.*;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.view.Screens;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.screen.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Client extends Thread {
    private static Client instance;
    private ClientListener clientListener;
    private DataOutputStream dataOutputStream;
    private Gson gson;
    private Game game;
    private User user;
    private String request;
    private Session session;

    public Client() {
        setDaemon(true);
        clientListener = new ClientListener(this);
        clientListener.start();
        gson = new GsonBuilder().create();
        instance = this;
    }

    public static Client getInstance() {
        return instance;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (clientListener.serverResponses) {
                while (clientListener.serverResponses.isEmpty()) {
                    try {
                        clientListener.serverResponses.wait();  //wait until a request is issued to the server thread
                    } catch (InterruptedException e) {
                        System.err.println("interrupted in request handler");
                    }
                }
                request = clientListener.serverResponses.removeFirst();
            }
            handleRequest();
        }
    }

    public void sendMassage(ClientRequest massage) {
        massage.setSession(session);
        try {
            dataOutputStream.writeUTF(gson.toJson(massage));
        } catch (IOException e) {
            System.err.println("IO exception in sendMassage");
        }
    }

    private void handleRequest() {
        System.out.println("response at client side ->> " + request);

        ServerResponse serverResponse = gson.fromJson(request , ServerResponse.class);
        if(serverResponse == null) return;

        if(serverResponse.getSession() != null) {
            session = serverResponse.getSession();
        }

        switch (serverResponse.getType()) {
            case CHANGE_SCREEN:
                ChangeMenuResponse changeMenuResponse = gson.fromJson(request, ChangeMenuResponse.class);
                Gwent.singleton.changeScreen(changeMenuResponse.getScreen());
                break;
            case SIGN_IN_CONFIRM:
                SignUpResponse confirm = gson.fromJson(request, SignUpResponse.class);
                User.setToBeSignedUp(confirm.getUser());
                Gwent.singleton.changeScreen(Screens.CHOOSE_SECURITY_QUESTION);
                break;
            case SIGN_IN_DENY:
                SignUpResponse deny = gson.fromJson(request, SignUpResponse.class);
                ((RegisterMenuScreen) Gwent.singleton.getCurrentScreen()).showError(deny.getError(), deny.getUsername());
                break;
            case SECURITY_QUESTION_SET:
                ((ChooseSecurityQuestionScreen) Gwent.singleton.getCurrentScreen()).showWelcomeMessage();
                User.setToBeSignedUp(null);
                Gwent.singleton.changeScreen(Screens.LOGIN);
                break;
            case LOGIN_CONFIRM :
                LoginResponse loginResponseAccept = gson.fromJson(request, LoginResponse.class);
                user = loginResponseAccept.getUser();
                User.setLoggedInUser(user);
                Gwent.singleton.changeScreen(Screens.MAIN_MENU);
                break;
            case LOGIN_DENY:
                LoginResponse loginResponseDeny = gson.fromJson(request, LoginResponse.class);
                ((LoginMenuScreen)Gwent.singleton.getCurrentScreen()).showError(loginResponseDeny.getError());
                break;
            case RETURN_TO_GAME:
                ReturnToGameResponse returnToGameResponse = gson.fromJson(request, ReturnToGameResponse.class);
                break;
            case FRIEND_REQUEST:
                ServerFriendRequest serverFriendRequest = gson.fromJson(request, ServerFriendRequest.class);
                FriendsScreen.setRequestInfoReceived(true);
                FriendsScreen.setRequestsHashMap(serverFriendRequest.getRequests());
                Gdx.app.log("FriendsScreen", "Friend requests received: " + serverFriendRequest.getRequests());

            //todo: add receiving requests

                break;
            case GET_FRIENDS:
                ServerFriend serverFriend = gson.fromJson(request, ServerFriend.class);
                User.getLoggedInUser().setFriends(serverFriend.getFriends());
                //let friends screen know the shit so they can proceed
                break;
            case INVITE_TO_PLAY:
                ServerPlayInvite serverPlayInvite = gson.fromJson(request, ServerPlayInvite.class);
                GameRequestScreen.showRequestWindow(serverPlayInvite.getClientRequest().getInvitor());
                break;
            case INVITE_TO_PLAY_RESPONSE:
                System.out.println("nothing");
                break;
            case START_GAME:
                SetGameToStart setGameToStart = gson.fromJson(request, SetGameToStart.class);
                System.out.println("please remove this console print after implementing game start");
                this.game = setGameToStart.getGame();
                Gwent.singleton.changeScreen(Screens.GAME);
                try{
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    System.err.println("Interruption in client");
                }
                break;
            case GAME_TURN_DECIDE:
                TurnDecideRequest turnDecideRequest = gson.fromJson(request, TurnDecideRequest.class);
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().chooseStarter();
                break;
            case RE_DRAW:
                try {
                    sleep(700);
                } catch (Exception e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
                ReDrawRequest reDrawRequest = gson.fromJson(request, ReDrawRequest.class);
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().setPermission(reDrawRequest.isPermission());
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().setShowSelectedCard(reDrawRequest.getHandAsCards(), 2, true);
                break;
            case PLAY_CARD_RESPONSE:
                PlayCardResponse playCardResponse = gson.fromJson(request, PlayCardResponse.class);
                this.game = playCardResponse.getGame();
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().setPermission(playCardResponse.isPermission());
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().update();
                ActionResponse actionResponse = playCardResponse.getActionResponse();
                if (actionResponse!= null && actionResponse.getAction().equals(ActionResponseType.SELECTION)) {
                    ((GameScreen)Gwent.singleton.getCurrentScreen()).showCardsToSelect(actionResponse.getAffectedCards(), actionResponse.getActionCount(), false);
                }
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().update();
                break;
            case CHAT:
                ChatInGameWrapper chatWrapper = gson.fromJson(request, ChatInGameWrapper.class);
                ChatInGame chat = chatWrapper.getChat();
                ChatController.receiveMassage(chat.getMassage(), chat.getUsername());
                break;
            case END_ROUND:
                EndRoundNotify endRoundNotify = gson.fromJson(request, EndRoundNotify.class);
                Round round = endRoundNotify.getRound();
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().endRound(round.getWinnerName());
                ((GameScreen)Gwent.singleton.getCurrentScreen()).getController().setPermission(endRoundNotify.isToStart());
                break;
            case END_GAME:
                EndGameNotify endGameNotify = gson.fromJson(request, EndGameNotify.class);
                //((GameScreen)Gwent.singleton.getCurrentScreen()).getController().endGame(endGameNotify.getWinner(), endGameNotify.isHasWinner());
                break;
            case GET_PUBLIC_GAMES:
                GetPublicGamesResponse publicGames = gson.fromJson(request, GetPublicGamesResponse.class);
                break;
            case GET_ALL_USERS:
                GetAllUsersResponse getAllUsersResponse = gson.fromJson(request, GetAllUsersResponse.class);
                break;
        }
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    public Game getGame() {
        return game;
    }
}
