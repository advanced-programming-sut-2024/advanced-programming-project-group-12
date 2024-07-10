package com.mygdx.game.model.network.massage.clientRequest;

public class ChatInGame extends ClientRequest {
    private String sender;
    private String time;
    private String replyToSender;
    private String replyToMassage;
    private String massage;
    private boolean isCheat;

    public ChatInGame(String sender, String time, String replyToSender, String replyToMassage, String massage, boolean spectator) {
        super(spectator?ClientRequestType.SPECTATOR_CHAT:ClientRequestType.CHAT);
        this.sender = sender;
        this.time = time;
        this.replyToSender = replyToSender;
        this.replyToMassage = replyToMassage;
        this.massage = massage;
        if(massage.startsWith("\\")) {
            isCheat = true;
            this.massage = massage.substring(1);
        }
    }

    public String getMassage() {
        return massage;
    }

    public String getSender() {
        return sender;
    }

    public boolean isCheat() {
        return isCheat;
    }

    public String getTime() {
        return time;
    }

    public String getReplyToSender() {
        return replyToSender;
    }

    public String getReplyToMassage() {
        return replyToMassage;
    }
}
