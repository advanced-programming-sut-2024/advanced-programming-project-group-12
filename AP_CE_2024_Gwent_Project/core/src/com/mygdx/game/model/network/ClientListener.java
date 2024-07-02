package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.session.Session;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientListener extends Thread{
    /**
     * the listener thread for server massages
     * they also include a handler that is connected to the main game thread
     */
    private static GsonBuilder gsonBuilder = new GsonBuilder();

    private Gson gson;
    private Socket server;
    private DataInputStream dataInputStream;
    private Session session;

    final ArrayList<String> serverResponses;

    public ClientListener() {
        setDaemon(true);
        serverResponses = new ArrayList<>();
        server = new Socket();
        try {
            dataInputStream = new DataInputStream(server.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gson = gsonBuilder.create();
    }

    @Override
    public void run() {
        while(true) {
            try {
                String serverMassage = dataInputStream.readUTF();
                synchronized (serverResponses) {
                    serverResponses.add(serverMassage);
                    serverResponses.notify();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
