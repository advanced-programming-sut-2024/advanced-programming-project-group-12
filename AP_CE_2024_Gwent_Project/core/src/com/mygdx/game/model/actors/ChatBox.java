package com.mygdx.game.model.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ChatBox extends Table {
    private TextField chatInput;
    private Button sendButton;
    private ChatLog chatLog;
    private Skin skin;
    private boolean isVisible = false;
    public ChatBox(Skin skin) {
        this.skin = skin;
        chatInput = new TextField("", skin);
        sendButton = new TextButton("Send", skin);
        chatLog = new ChatLog(skin);
        this.add(chatLog);
        this.row();
        this.add(chatInput);
        this.add(sendButton);
    }

    public void addMessage(String sender, String message) {
        chatLog.addMessage(sender, message);
    }

    public String getInputText() {
        return chatInput.getText();
    }

    public void clearInput() {
        chatInput.setText("");
    }

    public TextField getChatInput() {
        return chatInput;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public Table getChatLogTable() {
        return chatLog.getChatLogTable();
    }
    public void show() {
        chatInput.setVisible(true);
        sendButton.setVisible(true);
        chatLog.getChatLogTable().setVisible(true);
        isVisible = true;
    }

    public void hide() {
        chatInput.setVisible(false);
        sendButton.setVisible(false);
        chatLog.getChatLogTable().setVisible(false);
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }
}