package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.TournamentController;
import com.mygdx.game.model.user.User;


public class TournamentScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;


    public TournamentScreen() {
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("bg/tournament.jpg"));
        TournamentController.getInstance().draw();
        createFinalGame();
        Gdx.input.setInputProcessor(stage);
    }

    private void createRoundOneGames() {
        createGameInfoTable(TournamentController.getInstance().getTournamentParticipants().get(0),
                TournamentController.getInstance().getTournamentParticipants().get(1), 220, 760, 1);
        createGameInfoTable(TournamentController.getInstance().getTournamentParticipants().get(2),
                TournamentController.getInstance().getTournamentParticipants().get(3), 220, 240, 1);
        createGameInfoTable(TournamentController.getInstance().getTournamentParticipants().get(4),
                TournamentController.getInstance().getTournamentParticipants().get(5), 1380, 760, 1);
        createGameInfoTable(TournamentController.getInstance().getTournamentParticipants().get(6),
                TournamentController.getInstance().getTournamentParticipants().get(7), 1380, 240, 1);
    }
    public void createSemiFinalGames() {
        createGameInfoTable(TournamentController.getInstance().getTournamentParticipants().get(0),
                TournamentController.getInstance().getTournamentParticipants().get(1), 450, 475, 2);
        createGameInfoTable(TournamentController.getInstance().getTournamentParticipants().get(2),
                TournamentController.getInstance().getTournamentParticipants().get(3), 1150, 475, 2);
    }
    public void createFinalGame() {
        float middleX = (float) Gwent.WIDTH / 2;
        float middleY = (float) Gwent.HEIGHT / 2;
        User firstFinalist = TournamentController.getInstance().getTournamentParticipants().getFirst();
        User secondFinalist = TournamentController.getInstance().getTournamentParticipants().getLast();
        Label firstFinalistLabel = new Label(firstFinalist.getUsername(), Gwent.singleton.skin);
        firstFinalistLabel.setFontScale(1.5f);
        firstFinalistLabel.setColor(Color.GOLD);
        firstFinalistLabel.setPosition(middleX - 160, middleY - 10);
        Label secondFinalistLabel = new Label(secondFinalist.getUsername(), Gwent.singleton.skin);
        secondFinalistLabel.setFontScale(1.5f);
        secondFinalistLabel.setColor(Color.GOLD);
        secondFinalistLabel.setPosition(middleX + 50, middleY - 60);
        TextButton startFinalGameButton = new TextButton("Start Final Game", Gwent.singleton.skin);

        startFinalGameButton.setSize(600, 160);
        startFinalGameButton.setPosition(middleX - 300, middleY + 300);
        startFinalGameButton.setColor(Color.VIOLET);
        startFinalGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playGame(firstFinalist, secondFinalist, 3);
            }
        });
        stage.addActor(firstFinalistLabel);
        stage.addActor(secondFinalistLabel);
        stage.addActor(startFinalGameButton);
    }
    private void createGameInfoTable(User player1, User player2, float x, float y, int round) {
        Table gameTable = new Table();
        Label player1Label = new Label(player1.getUsername(), Gwent.singleton.skin);
        player1Label.setFontScale(1.5f);
        player1Label.setColor(Color.BLUE);
        Label player2Label = new Label(player2.getUsername(), Gwent.singleton.skin);
        player2Label.setFontScale(1.5f);
        player2Label.setColor(Color.RED);
        TextButton playButton  = new TextButton("play", Gwent.singleton.skin);
        playButton.setSize(200, 120);
        playButton.setColor(Color.ROYAL);
        if(round == 1) {
           gameTable.add(player1Label).center().padBottom(50);
           gameTable.row();
           gameTable.add(playButton).center().padBottom(50);
           gameTable.row();
           gameTable.add(player2Label).center();
       } else if(round == 2) {
            gameTable.add(player1Label).center().padBottom(205);
            gameTable.row();
            gameTable.add(playButton).center().padBottom(210);
            gameTable.row();
            gameTable.add(player2Label).center();
       }
        playButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               playGame(player1, player2, round);
           }
        });
        gameTable.setPosition(x, y);
        stage.addActor(gameTable);
    }

    private void playGame(User player1, User player2, int round) {
        TournamentController.getInstance().startGameInTournament(player1, player2, round);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
        if(TournamentController.getInstance().isNewRoundStarted()) {
            if(TournamentController.getInstance().getTournamentParticipants().size() == 4) {
                createSemiFinalGames();
            } else if(TournamentController.getInstance().getTournamentParticipants().size() == 2) {
                createFinalGame();
            }
            TournamentController.getInstance().setNewRoundStarted();
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}