package com.mygdx.game.model.game;

import com.mygdx.game.model.game.card.PlayableCard;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private final List<User> allUsers;
    private final List<Player> players;
    private CardSelectHandler cardSelectHandler;
//    private final LocalDate date;

    private final ArrayList<Round> rounds;
    private Round currentRound;

    private final GameBoard gameBoard;
    private Player currentPlayer;
    private Player opposition;

    private boolean isOver;
    private boolean randomMedic;

    public Game(Player player1, Player player2) {
        allUsers = Arrays.asList(player1.getUser(), player2.getUser());
        players = Arrays.asList(player1, player2);
        currentPlayer = player1;
        opposition = player2;

        //todo
        //date = LocalDate.now();
        rounds = new ArrayList<>();
        gameBoard = new GameBoard(currentPlayer, opposition);
        currentRound = new Round(1, currentPlayer, opposition);

        isOver = false;
        randomMedic = false;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpposition() {
        return opposition;
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

    public CardSelectHandler getCardSelectHandler() {
        return cardSelectHandler;
    }

    public void setCardSelectHandler(CardSelectHandler cardSelectHandler) {
        this.cardSelectHandler = cardSelectHandler;
    }

    public void setRandomMedic(boolean randomMedic) {
        this.randomMedic = randomMedic;
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
                playableCard.place(playableCard.getRow(), p);
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
                    playableCard.place(playableCard.getRow(), p);
                }
            }
        }
    }

    private void finishGame() {
        for(User u: allUsers) {
            u.addGame(this);
        }
        //todo
    }

    public void isOver() {
        isOver = true;
    }
}
