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
        final float buttonWidth = screenWidth * 0.15f;
        final float buttonHeight = screenHeight * 0.10f;
        final float marginY = screenHeight * 0.2f;
        float buttonPadding = screenHeight * 0.15f;

        TextButton playGameButton = new TextButton("Nowa gra", skin, "default");
        playGameButton.setSize(buttonWidth, buttonHeight);
        playGameButton.setPosition(screenWidth / 2f - playGameButton.getWidth() / 2f,
                screenHeight - marginY);

        /*
        TextButton scoreButton = new TextButton("Wyniki", skin, "default");
        scoreButton.setSize(buttonWidth, buttonHeight);
        scoreButton.setPosition(playGameButton.getX(),
                playGameButton.getY() - buttonPadding);
        */

        /*
        TextButton rulesButton = new TextButton("Zasady gry", skin, "default");
        rulesButton.setSize(buttonWidth, buttonHeight);
        rulesButton.setPosition(scoreButton.getX(),
                scoreButton.getY() - buttonPadding);
        */

        TextButton exitButton = new TextButton("Wyjdz", skin, "default");
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(playGameButton.getX(),
                playGameButton.getY() - buttonPadding);

        playGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.gameSetupScreen);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        playGameButton.getLabel().setColor(Color.WHITE);

        stage.addActor(playGameButton);
        // stage.addActor(scoreButton);
        // stage.addActor(rulesButton);
        stage.addActor(exitButton);
    }

    @Override
    public void initializeTextFields() {

    }
}
