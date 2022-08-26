package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;

public class MainMenuUI extends BaseUI{

    public MainMenuUI(final Eurobusiness game) {
        super(game);
    }

    @Override
    public void drawShapeRenderer() {

    }

    @Override
    public void drawBatch() {

    }

    @Override
    public void initializeLabels() {

    }

    @Override
    public void initializeButtons() {
        final int screenWidth = (int) getGame().settings.getScreenWidth();
        final int screenHeight = (int) getGame().settings.getScreenHeight();
        final float buttonWidth = screenWidth * 0.15f;
        final float buttonHeight = screenHeight * 0.10f;
        final float marginY = screenHeight * 0.2f;
        float buttonPadding = screenHeight * 0.15f;

        TextButton playGameButton = new TextButton("Nowa gra", getSkin(), "default");
        playGameButton.setSize(buttonWidth, buttonHeight);
        playGameButton.setPosition(screenWidth / 2f - playGameButton.getWidth() / 2f,
                screenHeight - marginY);

        TextButton scoreButton = new TextButton("Wyniki", getSkin(), "default");
        scoreButton.setSize(buttonWidth, buttonHeight);
        scoreButton.setPosition(playGameButton.getX(),
                playGameButton.getY() - buttonPadding);

        TextButton rulesButton = new TextButton("Zasady gry", getSkin(), "default");
        rulesButton.setSize(buttonWidth, buttonHeight);
        rulesButton.setPosition(scoreButton.getX(),
                scoreButton.getY() - buttonPadding);

        TextButton exitButton = new TextButton("Wyjdz", getSkin(), "default");
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(rulesButton.getX(),
                rulesButton.getY() - buttonPadding);

        playGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(game.setUpScreen);
                // TODO: set new game screen
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        playGameButton.getLabel().setColor(Color.WHITE);

        getStage().addActor(playGameButton);
        getStage().addActor(scoreButton);
        getStage().addActor(rulesButton);
        getStage().addActor(exitButton);
    }

    @Override
    public void initializeTextFields() {

    }
}
