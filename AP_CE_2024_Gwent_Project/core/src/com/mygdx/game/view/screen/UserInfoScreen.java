package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.ScreenManager;
import com.mygdx.game.controller.UserInfoController;

public class UserInfoScreen implements Screen {
    private static final float FIELD_WIDTH = 400;
    private static final float FIELD_HEIGHT = 80;
    private final Stage stage;
    private final Table table;
    private final UserInfoController controller = new UserInfoController();

    // Labels
    private final Label highestScoreLabel;
    private final Label totalScoreLabel;
    private final Label gamesPlayedLabel;
    private final Label gamesWonLabel;
    private final Label gamesLostLabel;
    private final Label gamesDrawnLabel;

    // Buttons
    private TextButton backButton;



    public UserInfoScreen() {
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        highestScoreLabel = new Label("Highest Score: " + controller.getUserInfo().get("highest score"), Gwent.singleton.getSkin());
        totalScoreLabel = new Label("Total Score: " + controller.getUserInfo().get("total score"), Gwent.singleton.getSkin());
        gamesPlayedLabel = new Label("Games Played: " + controller.getUserInfo().get("games played"), Gwent.singleton.getSkin());
        gamesWonLabel = new Label("Games Won: " + controller.getUserInfo().get("games won"), Gwent.singleton.getSkin());
        gamesLostLabel = new Label("Games Lost: " + controller.getUserInfo().get("games lost"), Gwent.singleton.getSkin());
        gamesDrawnLabel = new Label("Games Drawn: " + controller.getUserInfo().get("games drawn"), Gwent.singleton.getSkin());

        backButton = new TextButton("Back", Gwent.singleton.getSkin());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.setProfileMenuScreen();
            }
        });

        table.add(highestScoreLabel).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row();
        table.add(totalScoreLabel).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row();
        table.add(gamesPlayedLabel).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row();
        table.add(gamesWonLabel).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row();
        table.add(gamesLostLabel).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row();
        table.add(gamesDrawnLabel).width(FIELD_WIDTH).height(FIELD_HEIGHT);
        table.row();
        table.add(backButton).width(FIELD_WIDTH).height(FIELD_HEIGHT);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        updateLabels();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        Gwent.singleton.getBatch().begin();
        Gwent.singleton.getBatch().end();
        stage.act();
        stage.draw();
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

    private void updateLabels() {
        highestScoreLabel.setText("Highest Score: " + controller.getUserInfo().get("high score"));
        totalScoreLabel.setText("Total Score: " + controller.getUserInfo().get("total score"));
        gamesPlayedLabel.setText("Games Played: " + controller.getUserInfo().get("games played"));
        gamesWonLabel.setText("Games Won: " + controller.getUserInfo().get("games won"));
        gamesLostLabel.setText("Games Lost: " + controller.getUserInfo().get("games lost"));
        gamesDrawnLabel.setText("Games Drawn: " + controller.getUserInfo().get("games drawn"));
    }
}
