package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.session.Session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientListener extends Thread{
    /**
     * the listener thread for server massages
     * they also include a handler that is connected to the main game thread
     */

    private Socket server;
    private DataInputStream dataInputStream;

    final ArrayList<String> serverResponses;

    public ClientListener(Client client) {
        setDaemon(true);
        serverResponses = new ArrayList<>();

        try {
            server = new Socket("127.0.0.1", 5002);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            client.setDataOutputStream(new DataOutputStream(server.getOutputStream()));
            dataInputStream = new DataInputStream(server.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
