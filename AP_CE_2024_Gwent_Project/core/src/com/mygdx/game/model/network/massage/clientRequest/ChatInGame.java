package com.mygdx.game.model.network.massage.clientRequest;

public class ChatInGame extends ClientRequest {
    private String massage;
    public ChatInGame(String massage) {
        super(ClientRequestType.CHAT);
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }
}
