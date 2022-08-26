package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.ui.MainMenuUI;

public class MainMenuScreen implements Screen {
    private final Eurobusiness game;
    private MainMenuUI mainMenuUI;

    public MainMenuScreen(final Eurobusiness game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.mainMenuUI = new MainMenuUI(game);
        mainMenuUI.initializeStage();
    }

    @Override
    public void render(float delta) {
        mainMenuUI.draw(delta);
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
