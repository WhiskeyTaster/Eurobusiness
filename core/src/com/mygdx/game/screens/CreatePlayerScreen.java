package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.Pair;
import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.ui.CreatePlayerUI;

import java.util.ArrayList;

public class CreatePlayerScreen implements Screen {
    private final Eurobusiness game;
    private final Pair<Integer, Integer> players;

    private ArrayList<Owner> owners;

    private CreatePlayerUI createPlayerUI;

    public CreatePlayerScreen(final Eurobusiness game, final Pair<Integer, Integer> players) {
        this.game = game;
        this.players = players;
    }

    @Override
    public void show() {
        this.createPlayerUI = new CreatePlayerUI(game);
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
