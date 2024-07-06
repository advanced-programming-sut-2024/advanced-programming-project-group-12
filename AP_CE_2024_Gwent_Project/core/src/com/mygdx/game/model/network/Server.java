package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Server extends Thread {
    /**
     * launcher ond listener threads of the game;
     * they launch a handler thread that becomes the game's main thread
     */
    public static final int THREAD_NUMBER = 10;

    public static final HashMap<Session, Server> servers = new HashMap<>();
    private static final LinkedList<Socket> clients = new LinkedList<>();
    private static ServerSocket serverSocket;
    private static final GsonBuilder builder = new GsonBuilder();

    private final Gson gson;
    private Socket socket;
    private DataInputStream dataInputStream;

    private User user;
    private RequestHandler requestHandler;
    final ArrayList<String> clientRequests;
    private boolean listen;

    static {
        try {
            serverSocket = new ServerSocket(5002);
        } catch (IOException e) {
            System.err.println("server error");
        }
    }

    Server(Socket socket) {
        gson = builder.create();
        this.socket = socket;
        clientRequests = new ArrayList<>();
        requestHandler = new RequestHandler(this,  builder.create());
        requestHandler.start();
    }

    public static void main(String[] args) {
        listen();
    }

    public static void listen() {
        while(true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("connected");
                new Server(client).start();
            } catch (IOException e) {
                System.err.println("connection error");
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        while(true) {
                listen = true;
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    requestHandler.setDataOutputStream(new DataOutputStream(socket.getOutputStream()));
                    handleConnection();
                } catch (IOException e) {
                    System.err.println("failure in connection in Server thread run method");
                    break;
                }
            //}
        }
    }

    private void handleConnection() throws IOException {
        while(listen) {
            String request = dataInputStream.readUTF();
            synchronized (clientRequests) {
                clientRequests.add(request);
                clientRequests.notify();
            }
        }
        try {
            dataInputStream.close();
        }catch (IOException e) {
            System.err.println("listener interrupted");
        }
        socket.close();
        dataInputStream.close();
    }

    public void terminate() {
        listen = false;
        requestHandler.terminate();
    }
}
