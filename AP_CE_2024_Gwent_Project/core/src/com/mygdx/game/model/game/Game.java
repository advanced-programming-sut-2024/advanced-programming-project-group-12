package com.mygdx.game.model.game;

import com.google.gson.annotations.Expose;
import com.mygdx.game.model.game.card.PlayableCard;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.EndGameNotify;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.EndRoundNotify;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayTurnPermission;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private final List<User> allUsers;
    private final List<Player> players;

    @Expose
    private CardSelectHandler cardSelectHandler;
//    private final LocalDate date;

    @Expose
    private final ArrayList<Round> rounds;
    private Round currentRound;

    private final GameBoard gameBoard;
    private Player currentPlayer;
    private Player opposition;
    private boolean pendingRoundEnd;

    private boolean isOver;
    private boolean randomMedic;

    public Game(Player player1, Player player2) {
        allUsers = Arrays.asList(player1.getUser(), player2.getUser());
        players = Arrays.asList(player1, player2);
        currentPlayer = player1;
        currentPlayer.setGame(this);
        opposition = player2;
        opposition.setGame(this);

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

    public void switchTurn() {
        Player temp = currentPlayer;
        currentPlayer = opposition;
        opposition = temp;

        RequestHandler.allUsers.get(currentPlayer.getUsername()).sendMassage(new PlayTurnPermission(this));

        if(currentPlayer.doesNotHaveGameToPlay()) {
            currentPlayer.setPassed(true);
        }
        if(currentPlayer.isPassed() && opposition.isPassed()) {
            endRound();
        }
    }

    private void sendEndRoundMassages(Player toStartNext) {
        Player toWait = toStartNext == currentPlayer? opposition: currentPlayer;
        RequestHandler.allUsers.get(toStartNext).sendMassage(new EndRoundNotify(true));
        RequestHandler.allUsers.get(toWait).sendMassage(new EndRoundNotify(false));
    }

    private void sendEndGameMassages(boolean hasWinner, String winnerUsername) {
        RequestHandler.allUsers.get(currentPlayer).sendMassage(new EndGameNotify(hasWinner, winnerUsername));
        RequestHandler.allUsers.get(opposition).sendMassage(new EndGameNotify(hasWinner, winnerUsername));
    }


    private void endRound() {
        Player winner = currentRound.endRound(gameBoard);
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
            sendEndRoundMassages(winner);
        } else {
            finishGame();
            String gameWinner;
            if(currentPlayer.getHealth() == 0 && opposition.getHealth() == 0) {
                gameWinner = null;
            }
            else if(currentPlayer.getHealth() == 0) {
                gameWinner = currentPlayer.getUsername();
            }
            else {
                gameWinner = opposition.getUsername();
            }

            sendEndGameMassages(gameWinner != null, gameWinner);
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
