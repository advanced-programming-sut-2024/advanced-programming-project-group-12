package com.mygdx.game.model.game;

import com.google.gson.annotations.Expose;
import com.mygdx.game.controller.remote.GameHandler;
import com.mygdx.game.model.game.card.PlayableCard;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.EndGameNotify;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.EndRoundNotify;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private transient final List<User> allUsers;
    private transient final List<Player> players;

    private transient CardSelectHandler cardSelectHandler;
//    private final LocalDate date;

    private transient final ArrayList<Round> rounds;
    private transient GameHandler gameHandler;
    private Round currentRound;


    private final GameBoard gameBoard;
    private Player currentPlayer;


    private Player opposition;

    private boolean isOver;
    private boolean randomMedic;

    public Game(Player player1, Player player2, GameHandler gameHandler) {
        allUsers = Arrays.asList(player1.getUser(), player2.getUser());
        players = Arrays.asList(player1, player2);
        currentPlayer = player1;
        currentPlayer.setGame(this);
        opposition = player2;
        opposition.setGame(this);

        this.gameHandler = gameHandler;

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

    public CardSelectHandler getCardSelectHandler() {
        return cardSelectHandler;
    }

    public void setCardSelectHandler(CardSelectHandler cardSelectHandler) {
        this.cardSelectHandler = cardSelectHandler;
    }

    public void setRandomMedic(boolean randomMedic) {
        this.randomMedic = randomMedic;
    }
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setOpposition(Player opposition) {
        this.opposition = opposition;
    }

    public void switchTurn() {
        if(!opposition.isPassed()) {
            Player temp = currentPlayer;
            currentPlayer = opposition;
            opposition = temp;
            RequestHandler.allUsers.get(currentPlayer.getUsername()).sendMassage(new PlayCardResponse(this, true));
        }

        if(opposition.isPassed()) {
            RequestHandler.allUsers.get(opposition.getUsername()).sendMassage(new PlayCardResponse(this, false));
        }

        if(opposition.doesNotHaveGameToPlay()) {
            opposition.setPassed(true);
        }
        if(currentPlayer.isPassed() && opposition.isPassed()) {
            endRound();
        }

        gameHandler.sendMassageToSpectators(new PlayCardResponse(this));
    }

    private void sendEndRoundMassages(Player toStartNext) {
        Player toWait = toStartNext == currentPlayer? opposition: currentPlayer;
        currentPlayer = toStartNext == null? currentPlayer :toStartNext;
        opposition = toWait;
        RequestHandler.allUsers.get(toStartNext.getUsername()).sendMassage(new EndRoundNotify(true, rounds.getLast(), this));
        RequestHandler.allUsers.get(toWait.getUsername()).sendMassage(new EndRoundNotify(false, rounds.getLast(), this));
        gameHandler.sendMassageToSpectators(new EndRoundNotify(false, rounds.getLast(), this));
    }

    private void sendEndGameMassages(EndGameNotify endGameNotify) {
        if(RequestHandler.allUsers.get(currentPlayer.getUsername()) != null)
            RequestHandler.allUsers.get(currentPlayer.getUsername()).sendMassage(endGameNotify);
        if(RequestHandler.allUsers.get(opposition.getUsername()) != null)
            RequestHandler.allUsers.get(opposition.getUsername()).sendMassage(endGameNotify);
        gameHandler.sendMassageToSpectators(endGameNotify);
    }


    private void endRound() {
        currentPlayer.setPassed(false);
        opposition.setPassed(false);

        Player winner = currentRound.endRound(gameBoard);

        gameBoard.reset();
        rounds.add(currentRound);

        for(Player p: players) {
            if(p.getFaction().equals(Faction.MONSTERS)) {
                ArrayList<PlayableCard> cardsList = currentRound.gameBoardCopy.allPlayerPlayableCards(p);
                //the deep copied gameBoard means that the card remains in discard and can be revived again
                PlayableCard playableCard = cardsList.remove((int) (Math.random() * cardsList.size()));
                System.out.println("monsters ability triggered keeping card: " + playableCard.getAbsName());
                playableCard.place(playableCard.getRow(), p);
            }
        }

        if(!isOver) {
            currentRound = new Round(rounds.size() + 1, currentPlayer, opposition);
        } else {
            User gameWinner;
            if(currentPlayer.getHealth() == 0 && opposition.getHealth() == 0) {
                gameWinner = null;
            }
            else if(currentPlayer.getHealth() == 0) {
                gameWinner = currentPlayer.getUser();
            }
            else {
                gameWinner = opposition.getUser();
            }
            finishGame(gameWinner);
            return;
        }

        for(Player p: players) {
            System.out.println(p.getFaction());
            if(p.getFaction().equals(Faction.SKELLIGE) && currentRound.getRoundNumber() == 3) {
                System.out.println("skellige action triggered: reviving cards :");
                ArrayList<PlayableCard> cardsList = gameBoard.getDiscardPlayableCards(p);
                for(int i = 0; i< 2 ; i++) {
                    PlayableCard playableCard = cardsList.remove((int) (Math.random() * cardsList.size()));
                    System.out.println(playableCard.getName());
                    playableCard.place(playableCard.getRow(), p);
                }
            }
        }
        sendEndRoundMassages(winner);
    }

    public void finishGame(User winner) {
        for(User u: allUsers) {
            u.addGame(this);
            u.save();
        }
        if(winner != null) {
            winner.addToWin();
        }
        sendEndGameMassages(new EndGameNotify(winner != null, winner == null? null:winner.getUsername(), this));
        gameHandler.endGame(winner);
    }

    public void isOver() {
        isOver = true;
    }
}
