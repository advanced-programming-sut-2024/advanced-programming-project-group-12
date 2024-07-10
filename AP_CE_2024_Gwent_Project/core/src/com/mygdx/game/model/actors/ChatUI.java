package com.mygdx.game.model.actors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.ChatController;
import com.mygdx.game.model.network.Client;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatUI extends Window {
    private static ChatUI singleton;

    private Message newMessage = null;
    private VerticalGroup chatLogGroup;
    private TextField messageField;
    private TextButton sendButton;
    private ScrollPane scrollPane;
    private Skin skin;
    private Stage stage;
    private Table rootTable;
    private boolean isReplyMode = false;
    private String replyToSender = null;
    private String replyToMessage = null;
    private boolean spectator;
    public ChatUI(Stage stage, Skin skin, boolean spectator) {
        super("Chat Window", skin);
        this.skin = skin;
        this.spectator = spectator;
        this.stage = stage;
        createUI();
        singleton = this;
        setSize(700, 900);
        setResizable(false);
        setMovable(false);
        add(rootTable).expand().fill();
        pack();
        setPosition((float) Gwent.WIDTH / 2 - getWidth() / 2, 10);
        stage.addActor(this);
    }
    public static ChatUI getInstance() {
        return singleton;
    }
    public void returnToStage(Stage stage) {
        stage.addActor(this);
    }
    private void createUI() {
        // Create the chat log group
        chatLogGroup = new VerticalGroup();
        chatLogGroup.setFillParent(true);
        chatLogGroup.align(Align.bottomLeft);

        // Create the scroll pane
        scrollPane = new ScrollPane(chatLogGroup, skin);
        // Set the scrollbar style
        scrollPane.setScrollingDisabled(true, false); // Disable horizontal scrolling
        scrollPane.setFadeScrollBars(false); // Show the scrollbar always
        scrollPane.setScrollBarPositions(false, true); // Show the scrollbar on the right
        scrollPane.setSmoothScrolling(true); // Add a 10-pixel margin to the right

        // Add the tables to the stage
        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.add(scrollPane).size(500, 700);
        //rootTable.row();
        messageField = new TextField("", skin);
        messageField.setMessageText("Type a message...");
        rootTable.row();
        rootTable.add(messageField).size(500, 110).padBottom(10);

        // Create the send button
        sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendMessage();
            }
        });
        rootTable.row();
        rootTable.add(sendButton).size(200, 90);
        stage.addActor(rootTable);
    }

    private void openReplyMode(String senderName, String messageText) {
        // Set the reply mode
        isReplyMode = true;
        replyToSender = senderName;
        replyToMessage = messageText;
        messageField.setMessageText("Type a reply...");
    }

    private void sendMessage() {
        // Get the message text
        String messageText = messageField.getText();
        String sender = Client.getInstance().getUser().getUsername();
        String time = getTimestamp();
        if (isReplyMode) {
            // Add the reply to the chat log

            ChatController.sendMessage(sender, messageText, time,
                    replyToSender, replyToMessage, spectator);
            isReplyMode = false;
            replyToSender = null;
            replyToMessage = null;
        } else {
            // Add the message to the chat log
            ChatController.sendMessage(sender, messageText, time,
                    null, null, spectator);

        }

        // Clear the message field
        messageField.setText("");
    }

    private String getTimestamp() {
        // Get the current time
        Date date = new Date();
        // Format the time as "HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public void addReceivedMessage(String senderName, String messageText, String timestamp, String replyToSender, String replyToMessage) {
        boolean isSender;
        isSender = Client.getInstance().getUser().getUsername().equals(senderName);
        newMessage = new Message(isSender, senderName, messageText, timestamp, replyToSender, replyToMessage);
    }



    private void addMessageToChatLog(String senderName, String messageText, String timestamp, boolean isSender) {
        // Create a label for the sender's name
        Label senderLabel = new Label(senderName, skin);
        senderLabel.setFontScale(0.9f);
        senderLabel.setColor(Color.RED);

        // Create a label for the message text
        Label messageLabel = new Label(messageText, skin);
        messageLabel.setFontScale(1f);
        messageLabel.setColor(Color.LIGHT_GRAY);
        messageLabel.setWrap(true);

        // Create a label for the timestamp
        Label timestampLabel = new Label(timestamp, skin);
        timestampLabel.setFontScale(0.8f);
        timestampLabel.setColor(Color.GOLD);

        // Create a container for the message bubble
        Table messageBubble = new Table();
        messageBubble.setTransform(true);

        Image background = new Image(new Texture("message-back.jpeg"));
        if (isSender) {
            messageBubble.setColor(Color.BLUE);
        } else {
            messageBubble.setColor(Color.GREEN);
        }
        messageBubble.setBackground(background.getDrawable());
        // Set the scale of the message bubble
        float scaleX = 1.5f;
        float scaleY = 0.6f;
        messageBubble.setScale(scaleX, scaleY);

        // Add the sender's name, message text, and timestamp to the message bubble with appropriate scale
        messageBubble.add(senderLabel).center().padRight(20).width(60).height(100);
        messageBubble.add(messageLabel).center().padRight(20).width(120).height(100);
        messageBubble.add(timestampLabel).center().padRight(20).width(70).height(100);
        // Add a click listener to the message bubble
        messageBubble.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Open reply mode
                openReplyMode(senderName, messageText);
            }
        });

        // Add the message bubble to the chat log
        chatLogGroup.addActor(messageBubble);
        // Scroll to the bottom of the scroll pane
        scrollPane.scrollTo(0, 0, 0, 0);
    }

    private void addMessageToChatLog(String senderName, String messageText, String timestamp, boolean isSender, String replyToSender, String replyToMessage) {
        // Create a label for the sender's name
        Label senderLabel = new Label(senderName, skin);
        senderLabel.setFontScale(0.9f);
        senderLabel.setColor(Color.RED);

        // Create a label for the message text
        Label messageLabel = new Label(messageText, skin);
        messageLabel.setFontScale(1f);
        messageLabel.setColor(Color.LIGHT_GRAY);
        messageLabel.setWrap(true);

        // Create a label for the timestamp
        Label timestampLabel = new Label(timestamp, skin);
        timestampLabel.setFontScale(0.8f);
        timestampLabel.setColor(Color.GOLD);

        // Create a container for the message bubble
        Table messageBubble = new Table();
        messageBubble.setTransform(true);

        Image background = new Image(new Texture("bg/message-back.jpeg"));
        if (isSender) {
            messageBubble.setColor(Color.BLUE);
        } else {
            messageBubble.setColor(Color.GREEN);
        }
        messageBubble.setBackground(background.getDrawable());

        // Add a reply indicator if it's a reply
        if (replyToSender!= null && replyToMessage!= null) {
            Label replyIndicator = new Label("Reply to " + replyToSender + ": " + replyToMessage, skin);
            replyIndicator.setFontScale(0.8f);
            replyIndicator.setColor(Color.GRAY);
            messageBubble.add(replyIndicator).left().padBottom(5).width(60).height(45);
            messageBubble.row();
        }
        // Set the scale of the message bubble
        float scaleX = 1.5f;
        float scaleY = 0.6f;
        messageBubble.setScale(scaleX, scaleY);

        // Add the sender's name, message text, and timestamp to the message bubble with appropriate scale
        messageBubble.add(senderLabel).center().padRight(20).width(60).height(100);
        messageBubble.add(messageLabel).center().padRight(20).width(120).height(100);
        messageBubble.add(timestampLabel).center().padRight(20).width(70).height(100);

        // Add a click listener to the message bubble
        messageBubble.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Open reply mode
                openReplyMode(senderName, messageText);
            }
        });

        // Add the message bubble to the chat log
        chatLogGroup.addActor(messageBubble);

        // Scroll to the bottom of the scroll pane
        scrollPane.scrollTo(0, 0, 0, 0);
    }

    public void putNewMessage() {
        if(newMessage.getReplyToSender() == null) {
            addMessageToChatLog(newMessage.getSender(), newMessage.getMessage()
                    , newMessage.getTime(), newMessage.isSender());
        } else {
            addMessageToChatLog(newMessage.getSender(), newMessage.getMessage(), newMessage.getTime(),
                    newMessage.isSender(), newMessage.getReplyToSender(), newMessage.getReplyToMessage());
        }
        newMessage = null;
    }

    public Message getNewMessage() {
        return newMessage;
    }
    public void hide() {
        setVisible(false);
        System.out.println("hide");
    }
    public void show() {
        setVisible(true);
        System.out.println("show");
    }

}