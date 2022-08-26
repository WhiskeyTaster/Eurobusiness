package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.ui.GameSetupUI;

public class GameSetupScreen implements Screen {
    private final Eurobusiness game;
    private GameSetupUI gameSetupUI;


    public GameSetupScreen(final Eurobusiness game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.gameSetupUI = new GameSetupUI(game);
        this.gameSetupUI.initializeStage();
    }

    @Override
    public void render(float delta) {

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
