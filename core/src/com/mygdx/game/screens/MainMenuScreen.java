package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.ui.MainMenuUI;

public class MainMenuScreen implements Screen {
    private final Eurobusiness game;
    private final MainMenuUI mainMenuUI;

    public MainMenuScreen(final Eurobusiness game) {
        this.game = game;
        this.mainMenuUI = new MainMenuUI();
    }

    @Override
    public void show() {

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
