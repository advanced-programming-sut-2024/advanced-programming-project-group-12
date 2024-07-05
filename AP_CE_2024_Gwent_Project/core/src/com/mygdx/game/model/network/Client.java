package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.Gwent;
import com.mygdx.game.model.network.massage.serverResponse.*;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerInviteResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerPlayInvite;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.view.Screens;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.screen.ChooseSecurityQuestionScreen;
import com.mygdx.game.view.screen.GameRequestScreen;
import com.mygdx.game.view.screen.LoginMenuScreen;
import com.mygdx.game.view.screen.RegisterMenuScreen;

import java.io.DataOutputStream;
import java.io.IOException;

public class Client extends Thread{
    private static Client instance;
    private ClientListener clientListener;
    private DataOutputStream dataOutputStream;
    private Gson gson;
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
        //perhaps wait for response
        try {
            dataOutputStream.writeUTF(gson.toJson(massage));
        } catch (IOException e) {
            System.err.println("IO exception in sendMassage");
        }
    }

    private void handleRequest() {
        ServerResponse serverResponse = gson.fromJson(request , ServerResponse.class);
        if(serverResponse == null) return;

        session = serverResponse.getSession();

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
            case FRIEND_REQUEST:
                ServerFriendRequest serverFriendRequest = gson.fromJson(request, ServerFriendRequest.class);
                //todo: add receiving requests
                //User.getLoggedInUser().setReceivedFriendRequests(serverFriendRequest.getRequests());
                //let friends screen know they can proceed
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
                ServerInviteResponse response = gson.fromJson(request, ServerInviteResponse.class);
                if(response.getResponse().getResponse().equals("accept")) {
                    Gwent.singleton.changeScreen(Screens.GAME);
                }
                break;
            case GAME_TURN_DECIDE:
        }
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
