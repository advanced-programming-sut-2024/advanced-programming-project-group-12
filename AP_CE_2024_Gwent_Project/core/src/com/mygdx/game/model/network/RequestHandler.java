package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.massage.clientRequest.*;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.massage.serverResponse.preGameRosponse.InviteUserToPlay;
import com.mygdx.game.model.network.session.Session;

import java.io.DataOutputStream;
import java.io.IOException;

public class RequestHandler extends Thread {
    private Server server;
    private String request;
    private Gson gson;
    private DataOutputStream dataOutputStream;

    public RequestHandler(Server server, Gson gson) {
        this.server = server;
        this.gson = gson;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (server.clientRequests) {
                while (server.clientRequests.isEmpty()) {
                    try {
                        server.clientRequests.wait();
                    } catch (InterruptedException e) {
                        System.err.println("interrupted in request handler");
                    }
                }
                request = server.clientRequests.removeFirst();
            }
            handleRequest();
        }
    }

    private void handleRequest() {
        ClientRequest clientRequest = gson.fromJson(request, ClientRequest.class);
        User user = Session.getUser(clientRequest.getSession());
        ServerResponse serverResponse = null;
        switch (clientRequest.getType()) {
            case SIGN_IN :;//handle the shit
            case LOGIN :;
            case ADD_TO_FRIEND :;
            case ACCEPT_FRIEND_REQUEST:;
            case START_GAME :
                StartGameRequest startGameRequest = gson.fromJson(request, StartGameRequest.class);
                User target = startGameRequest.getUserToBeInvited();
                AnswerUserInvite answer = (AnswerUserInvite) target.sendMassage(new InviteUserToPlay(Session.getUser(startGameRequest.getSession())));
                serverResponse =  new ServerResponse(answer.isAccept() ? ServerResponseType.CONFIRM : ServerResponseType.DENY, startGameRequest.getSession());
            break;
        };

        try {
            dataOutputStream.writeUTF(gson.toJson(serverResponse));
        } catch (IOException e) {
            System.err.println("IO exception in request handler");
        }
    }

    private void sendMassage(ServerResponse massage) {
        try {
            dataOutputStream.writeUTF(gson.toJson(massage));
        } catch (IOException e) {
            System.err.println("IO exception in sendMassage");
        }
    }

    public void terminate() {
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            System.err.println("IO exception in request handler ");
        }
    }
}
