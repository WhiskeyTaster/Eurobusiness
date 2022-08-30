package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.Pair;
import com.mygdx.game.ui.CreatePlayerUI;


public class CreatePlayerScreen implements Screen {
    private final Eurobusiness game;
    private final Pair<Integer, Integer> players;

    private CreatePlayerUI createPlayerUI;

    public CreatePlayerScreen(final Eurobusiness game) {
        this.game = game;
        this.players = game.players;
    }

    @Override
    public void show() {
        this.createPlayerUI = new CreatePlayerUI(game, players);
        this.createPlayerUI.initializeStage();
    }

    @Override
    public void render(float delta) {
        createPlayerUI.draw(delta);
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
