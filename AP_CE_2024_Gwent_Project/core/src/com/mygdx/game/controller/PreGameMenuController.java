package com.mygdx.game.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygdx.game.model.*;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.CommanderCard;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.CommanderCards;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;

public class PreGameMenuController {
    //    Player player = Game.getCurrentGame().getCurrentPlayer();
    User user = User.getLoggedInUser();

    public void gotoMainMenu() {
        ScreenManager.setMainMenuScreen();
    }

    public void startGame() {
        //hard coding a deck
        CommanderCard commanderCard = CommanderCards.EMHYR_INVADER.getAbstractCard();
        LinkedList<AbstractCard> deck = new LinkedList<>();
        deck.add(AllCards.COMMANDER_HORN.getAbstractCard());
        deck.add(AllCards.SCORCH.getAbstractCard());
        deck.add(AllCards.YENNEFER_OF_VENGENBERG.getAbstractCard());
        deck.add(AllCards.KEIRA_METZ.getAbstractCard());
        deck.add(AllCards.POOR_FUCKING_INFANTRY.getAbstractCard());
        deck.add(AllCards.CIRILLA_FIONA_ELEN_RIANNON.getAbstractCard());

        Faction faction = Faction.NILFGAARD;
        Player player = new Player(User.getLoggedInUser(), commanderCard, deck, faction);

        new Game(player, player);
        Game.getCurrentGame().setCurrentPlayer(player); // Set the current player
        Game.getCurrentGame().setOpposition(player);

        ScreenManager.setGameScreen();
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
        /* TODO : fix this method

        // Open a file chooser dialog
//        JFileChooser fileChooser = new JFileChooser();
//        int returnValue = fileChooser.showOpenDialog(null);
//
//        If the user selected a file
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//        Read the JSON string from the file
//            try (FileReader file = new FileReader(fileChooser.getSelectedFile().getPath())) {
//                Gson gson = new Gson();
//                Type deckType = new TypeToken<LinkedList<AbstractCard>>() {}.getType();
//                LinkedList<AbstractCard> deck = gson.fromJson(file, deckType);
//
//        Set the user 's deck to the deck read from the file
//                User.getLoggedInUser().setDeck(deck);
//                System.out.println("Deck successfully uploaded from " + fileChooser.getSelectedFile().getName());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    }
*/
    }
}