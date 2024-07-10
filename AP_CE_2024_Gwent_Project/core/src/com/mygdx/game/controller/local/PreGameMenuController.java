package com.mygdx.game.controller.local;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import com.mygdx.game.Gwent;
import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import com.google.gson.Gson;
import com.mygdx.game.view.Screens;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PreGameMenuController {
    User user = User.getLoggedInUser();

    public void gotoMainMenu() {
        Gwent.singleton.changeScreen(Screens.MAIN_MENU);
    }

    public void startGame() {

        LinkedList<AbstractCard> deck = User.getLoggedInUser().getDeckAsCard();
        CommanderCard leader = (CommanderCard) User.getLoggedInUser().getLeaderAsCard();
        Faction faction = User.getLoggedInUser().getFaction();

        Player player = new Player(User.getLoggedInUser());

        Gwent.singleton.changeScreen(Screens.GAME);
    }


    public void setFaction(String factionName) {
        Faction faction = Faction.getFactionByName(factionName);
        if (user.getFaction() != faction) {
            User.getLoggedInUser().resetDeck();
        }
        user.setFaction(faction);
        user.updateInfo();
    }

    public void downloadDeck() {
        // Convert the deck to a JSON string
        Gson gson = new Gson();
        String deckJson = gson.toJson(User.getLoggedInUser().getDeck());

        // Write the JSON string to a file
        try (FileWriter file = new FileWriter("/Users/alinr/Desktop/deck.json")) {
            file.write(deckJson);
            System.out.println("Deck successfully downloaded to deck.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadDeck() {
        // Run the file chooser on the UI thread
        Gdx.app.postRunnable(() -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                processDeckFile(selectedFile);
            }
        });
    }

    private void processDeckFile(File file) {
        try {
            FileHandle fileHandle = new FileHandle(file);
            String deckJson = fileHandle.readString();
            Gson gson = new Gson();
            String[] cardNames = gson.fromJson(deckJson, String[].class);
            List<AbstractCard> newDeck = new ArrayList<>();
            for (String cardName : cardNames) {
                AbstractCard card = AllCards.getCardByCardName(cardName);
                if (card != null) {
                    newDeck.add(card);
                }
            }
            User.getLoggedInUser().setDeck(newDeck);
            User.getLoggedInUser().updateInfo();
            Gdx.app.postRunnable(() -> {
                Gwent.singleton.changeScreen(Screens.PRE_GAME_MENU);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//    public void sendGameRequest(String username) {
//        Client.getInstance().sendMassage(new StartGameRequest(username, User.getLoggedInUser().getSender()));
//    }
