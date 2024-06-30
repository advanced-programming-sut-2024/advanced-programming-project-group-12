package com.mygdx.game.model.network;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{
    /**
     * the listener thread for server massages
     * they also include a handler that is connected to the main game thread
     */
    Socket server;
    DataInputStream dataInputStream;
    Session session;

    public Client() {
        setDaemon(true);
        server = new Socket();
        try {
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
                ServerResponse serverResponse = extractMassage(serverMassage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private ServerResponse extractMassage(String serverMassage) {
        return null;
    }
}
