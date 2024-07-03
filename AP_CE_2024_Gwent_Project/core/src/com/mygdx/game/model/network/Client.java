package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.user.User;

import java.io.DataOutputStream;
import java.io.IOException;

public class Client extends Thread {
    private static Client instance;
    private ClientListener clientListener;
    private DataOutputStream dataOutputStream;
    private Gson gson;
    private User user;
    private String request;

    public Client() {
        clientListener = new ClientListener();
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

    private void sendMassage(ClientRequest massage) {
        //perhaps wait for response
        try {
            dataOutputStream.writeUTF(gson.toJson(massage));
        } catch (IOException e) {
            System.err.println("IO exception in sendMassage");
        }
    }

    private void handleRequest() {
        
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
