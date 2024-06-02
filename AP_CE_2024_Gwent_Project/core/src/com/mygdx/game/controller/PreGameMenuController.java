package com.mygdx.game.controller;

import com.mygdx.game.model.*;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.CommanderCard;
import com.mygdx.game.model.card.AbstractCard;

import java.util.ArrayList;

public class PreGameMenuController {
    public void gotoMainMenu() {
        ScreenManager.setMainMenuScreen();
    }

    public void startGame() {
        CommanderCard commanderCard = (CommanderCard) AllCards.FOLTEST1.getAbstractCard();
        ArrayList<AbstractCard> deck = new ArrayList<>();

        Faction faction = Faction.NORTHERN_REALMS;
        Player player = new Player(User.getLoggedInUser(), commanderCard, deck, faction);
        new Game(player, player);
    }
}
