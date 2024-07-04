package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.Gwent;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.view.Screens;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.serverResponse.LoginResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.SignUpResponse;
import com.mygdx.game.model.user.User;
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

        switch (serverResponse.getType()) {
            case SIGN_IN_CONFIRM:
                SignUpResponse confirm = gson.fromJson(request, SignUpResponse.class);
//                User
                Gwent.singleton.changeScreen(Screens.CHOOSE_SECURITY_QUESTION);
                break;
            case SIGN_IN_DENY:
                SignUpResponse deny = gson.fromJson(request, SignUpResponse.class);
                ((RegisterMenuScreen) Gwent.singleton.getCurrentScreen()).showError(deny.getError(), deny.getUsername());
                break;
            case LOGIN_CONFIRM :
                LoginResponse loginResponseAccept = gson.fromJson(request, LoginResponse.class);
                user = loginResponseAccept.getUser();
                User.setLoggedInUser(user);
                Gwent.singleton.changeScreen(Screens.MAIN_MENU);
                break;
            case LOGIN_DENY:
                System.out.println("login deny");
                LoginResponse loginResponseDeny = gson.fromJson(request, LoginResponse.class);
                ((LoginMenuScreen)Gwent.singleton.getCurrentScreen()).showError(loginResponseDeny.getError());
                break;
            case FRIEND_REQUEST_ACCEPT:
                /* TODO
//                FriendRequest friendRequest
                 */

        }
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
