package com.mygdx.game.model;

import com.mygdx.game.model.gameBoard.GameBoard;

import java.util.ArrayList;

public enum Action {
    SCORCH(() -> {
        Player opposition = Game.getCurrentGame().getOpposition();
        GameBoard gameBoard = Game.getCurrentGame().getGameBoard();
        ArrayList<PlayableCard> allOppositionPlayableCards = gameBoard.allPlayerPlayableCards(opposition);

        if(allOppositionPlayableCards.size() == 0) {
            return;
        }

        allOppositionPlayableCards.sort(null);

        ArrayList<PlayableCard> cardsToBeScorched = new ArrayList<>();
        int maxPower = allOppositionPlayableCards.get(0).getPower();
        for(PlayableCard i: allOppositionPlayableCards) {
            if(i.getPower() == maxPower) {
                cardsToBeScorched.add(i);
            }
            else {
                break;
            }
        }

        for(PlayableCard i: cardsToBeScorched) {
            i.kill();
        }
    }),
    NO_ACTION(() -> {}),
    ;
    private Runnable action;

    Action(Runnable action) {
        this.action = action;
    }

    public void execute() {
        action.run();
    }
}
