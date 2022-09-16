package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.Pair;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Player;
import com.mygdx.game.ui.GameUI;

// TODO: to finish

public class GameScreen implements Screen {
    private final Eurobusiness game;
    private Board board;

    private GameUI gameUI;

    public GameScreen(final Eurobusiness game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.board = new Board(game.settings.getResolution(), new Pair<>(game.settings.getScreenWidth() * 0.025f, game.settings.getScreenHeight() * 0.025f),
                game.bank);
        this.gameUI = new GameUI(game, board);
        setUp();
    }

    private void setUp() {
        final int START_FIELD = 1;
        Field startField = board.getField(START_FIELD);
        for (Player player : game.players) {
            player.getPawn().setPosition(startField.getX() + startField.getWidth() / 2 - player.getPawn().getWidth() / 2,
                    startField.getY() + startField.getHeight() / 2 - player.getPawn().getHeight() / 2);
        }
    }

    @Override
    public void render(float delta) {
        gameUI.draw(delta);
        if (gameUI.isTourFinished()) {
            game.nextPlayer();
            gameUI = new GameUI(game, board);
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
