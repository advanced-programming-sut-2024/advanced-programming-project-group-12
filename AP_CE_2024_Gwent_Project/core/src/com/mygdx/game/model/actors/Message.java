package com.mygdx.game.model.actors;

public class Message {
    private boolean isSender;
    private String Sender;
    private String Message;
    private String time;
    private String replyToSender;
    private String replyToMessage;

    public Message(boolean isSender, String sender, String message, String time, String replyToSender, String replyToMessage) {
        this.isSender = isSender;
        Sender = sender;
        Message = message;
        this.time = time;
        this.replyToSender = replyToSender;
        this.replyToMessage = replyToMessage;
    }

    public boolean isSender() {
        return isSender;
    }

    public String getSender() {
        return Sender;
    }

    public String getMessage() {
        return Message;
    }

    public String getTime() {
        return time;
    }

    public String getReplyToSender() {
        return replyToSender;
    }

    public String getReplyToMessage() {
        return replyToMessage;
    }
}
