package com.mygdx.game.controller;

import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.CommanderCards;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.util.LinkedList;

public class PreGameMenuController {
//    Player player = Game.getCurrentGame().getCurrentPlayer();
    User user = User.getLoggedInUser();

    public void gotoMainMenu() {
        ScreenManager.setMainMenuScreen();
    }

    public void startGame() {
        User toBePlayedWith;
        //hard coding a deck
        CommanderCard commanderCard = CommanderCards.FOLTEST_SIEGE.getAbstractCard();
        LinkedList<AbstractCard> deck = new LinkedList<>();
        deck.add(AllCards.COMMANDER_HORN.getAbstractCard());
        deck.add(AllCards.SCORCH.getAbstractCard());
        deck.add(AllCards.YENNEFER_OF_VENGENBERG.getAbstractCard());
        deck.add(AllCards.KEIRA_METZ.getAbstractCard());
        deck.add(AllCards.POOR_FUCKING_INFANTRY.getAbstractCard());
        deck.add(AllCards.CIRILLA_FIONA_ELEN_RIANNON.getAbstractCard());

        Faction faction = Faction.NORTHERN_REALMS;
        Player player = new Player(user ,commanderCard, deck, faction);
        Player opposition = player;
        Game game = new Game(player, opposition);
        player.setGame(game);
        opposition.setGame(game);

        ScreenManager.setGameMenuScreen();
    }

    public void setFaction(String factionName) {
        Faction faction = Faction.getFactionByName(factionName);
        if (user.getFaction() != faction) {
            User.getLoggedInUser().resetDeck();
        }
        user.setFaction(faction);
        user.updateInfo();
    }
}
