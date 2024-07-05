package com.mygdx.game.controller.local;

import com.mygdx.game.Gwent;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import com.google.gson.Gson;
import com.mygdx.game.view.Screens;

import javax.swing.event.CaretListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class PreGameMenuController {
    User user = User.getLoggedInUser();

    public void gotoMainMenu() {
        Gwent.singleton.changeScreen(Screens.MAIN_MENU);
    }

    public void startGame() {
        //hard coding a deck

        LinkedList<AbstractCard> deck = User.getLoggedInUser().getDeckAsCard();
        CommanderCard leader =(CommanderCard) User.getLoggedInUser().getLeaderAsCard();
        Faction faction = User.getLoggedInUser().getFaction();

        Player player = new Player(User.getLoggedInUser(), leader, deck, faction);

        new Game(player, player);


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

    public void sendGameRequest(String username) {
        Client.getInstance().sendMassage(new StartGameRequest(username, User.getLoggedInUser().getUsername()));
    }
}