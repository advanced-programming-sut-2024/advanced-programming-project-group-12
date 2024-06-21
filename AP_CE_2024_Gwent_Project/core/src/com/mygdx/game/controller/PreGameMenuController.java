package com.mygdx.game.controller;

import com.mygdx.game.model.*;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.CommanderCard;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.CommanderCards;

import java.util.ArrayList;

public class PreGameMenuController {
    Player player = Player.getCurrentPlayer();
    User user = User.getLoggedInUser();

    public void gotoMainMenu() {
        ScreenManager.setMainMenuScreen();
    }

    public void startGame() {
        //hard coding a deck
        CommanderCard commanderCard = CommanderCards.FOLTEST_SIEGE.getAbstractCard();
        ArrayList<AbstractCard> deck = new ArrayList<>();
        deck.add(AllCards.COMMANDER_HORN.getAbstractCard());
        deck.add(AllCards.SCORCH.getAbstractCard());
        deck.add(AllCards.YENNEFER_OF_VENGENBERG.getAbstractCard());
        deck.add(AllCards.KEIRA_METZ.getAbstractCard());
        deck.add(AllCards.POOR_FUCKING_INFANTRY.getAbstractCard());
        deck.add(AllCards.CIRILLA_FIONA_ELEN_RIANNON.getAbstractCard());

        Faction faction = Faction.NORTHERN_REALMS;
        Player player = new Player(User.getLoggedInUser(), commanderCard, deck, faction);
        Player.setCurrentPlayer(player); // Set the current player
        new Game(player, player);
        ScreenManager.setGameMenuScreen();
    }

    public void setFaction(String factionName) {
        Faction faction = Faction.getFactionByName(factionName);
        user.setFaction(faction);
        user.updateInfo();
    }
}
