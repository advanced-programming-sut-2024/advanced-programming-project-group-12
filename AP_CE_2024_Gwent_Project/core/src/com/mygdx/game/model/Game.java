package com.mygdx.game.model;

import com.mygdx.game.model.card.PlayableCard;
import com.mygdx.game.model.gameBoard.GameBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private static Game currentGame;
    private final List<Player> players;
//    private final LocalDate date;

    private final ArrayList<Round> rounds;
    private Round currentRound;

    private final GameBoard gameBoard;
    private Player currentPlayer;
    private Player opposition;

    private boolean isOver;

    public Game(Player player, Player opposition) {
        players = Arrays.asList(player, opposition);

        //todo
//        date = LocalDate.now();
        rounds = new ArrayList<>();
        gameBoard = new GameBoard(player, opposition);
        currentRound = new Round(1, player, opposition);

        currentGame = this;
        this.currentPlayer = player;
        this.opposition = opposition;

        isOver = false;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getOpposition() {
        return opposition;
    }

    public void setOpposition(Player opposition) {
        this.opposition = opposition;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void switchTurn() {
        if(currentPlayer.getHand().isEmpty() && currentPlayer.getLeader().HasPlayedAction()) {
            currentPlayer.setPassed(true);
        }
        Player temp = currentPlayer;
        currentPlayer = opposition;
        opposition = temp;

        if(currentPlayer.isPassed() && opposition.isPassed()) {
            endRound();
        }
    }

    private void endRound() {
        currentRound.endRound(gameBoard);
        gameBoard.reset();
        rounds.add(currentRound);

        for(Player p: players) {
            if(p.getFaction().equals(Faction.MONSTERS)) {
                ArrayList<PlayableCard> cardsList = currentRound.gameBoardCopy.allPlayerPlayableCards(p);
                //the deep copied gameBoard means that the card remains in discard and can be revived again
                PlayableCard playableCard = cardsList.remove((int) (Math.random() * cardsList.size()));
                playableCard.place(playableCard.getRow());
            }
        }


        if(!isOver) {
            currentRound = new Round(rounds.size() + 1, currentPlayer, opposition);
        } else {
            finishGame();
            return;
        }

        for(Player p: players) {
            if(p.getFaction().equals(Faction.SKELLIGE)) {
                ArrayList<PlayableCard> cardsList = gameBoard.getDiscardPlayableCards(p);
                for(int i = 0; i< 2 ; i++) {
                    PlayableCard playableCard = cardsList.remove((int) (Math.random() * cardsList.size()));
                    playableCard.place(playableCard.getRow());
                }
            }
        }
    }

    private void finishGame() {
        //todo
    }

    public void isOver() {
        isOver = true;
    }
}
