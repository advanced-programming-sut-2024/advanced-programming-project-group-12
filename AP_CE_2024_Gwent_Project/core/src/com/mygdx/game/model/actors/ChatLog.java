package com.mygdx.game.model.actors;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatLog extends Actor {
    private Table chatLogTable;
    private ScrollPane scrollPane;
    private Skin skin;
    private int maxSize = 200; // fixed size of the chat log

    public ChatLog(Skin skin) {
        this.skin = skin;
        chatLogTable = new Table();
        chatLogTable.setDebug(true);

        // Create a background image for the chat log
        Image backgroundImage = new Image(new Texture("bg/background-image.jpg"));
        backgroundImage.setColor(1, 1, 1, 1);
        backgroundImage.setFillParent(true);

        // Create a container to hold the background image and the chat log table
        Table container = new Table();
        container.add(backgroundImage).width(300).height(600).fill().expand();
        container.add(chatLogTable).fill().expand();

        scrollPane = new ScrollPane(container);
        scrollPane.setScrollingDisabled(true, false); // disable horizontal scrolling
        scrollPane.setFadeScrollBars(false); // show scrollbars always

        // Add a padding to the table
        chatLogTable.pad(10);

        // Set a fixed size
        scrollPane.setWidth(300);
        scrollPane.setHeight(400);
    }


    public void addMessage(String sender, String message) {
        // Use a bold font for the sender's name
        Label senderLabel = new Label(sender, skin);

        senderLabel.setFontScale(0.8f);
        senderLabel.setColor(1, 1, 1, 1);

        // Use a regular font for the message
        Label messageLabel = new Label(message, skin);
        messageLabel.setColor(1, 1, 1, 1);

        // Add a timestamp
        Label timestampLabel = new Label(getTimestamp(), skin);
        timestampLabel.setColor(0.5f, 0.5f, 0.5f, 1);

        chatLogTable.row();
        chatLogTable.add(senderLabel).left().padTop(5);
        chatLogTable.add(messageLabel).left().padTop(5).width(200);
        chatLogTable.add(timestampLabel).right().padTop(5);

        // if the number of rows exceeds the fixed size, add a scrollbar
        if (chatLogTable.getRows() > maxSize) {
            scrollPane.setScrollingDisabled(false, false); // enable vertical scrolling
        }
    }

    private String getTimestamp() {
        // Return the current timestamp
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public Table getChatLogTable() {
        return chatLogTable;
    }

    public ScrollPane getChatLogScrollPane() {
        return scrollPane;
    }
}