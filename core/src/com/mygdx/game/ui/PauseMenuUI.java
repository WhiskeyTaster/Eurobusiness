package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.windows.BaseWindow;
import com.mygdx.game.windows.FieldWindow;

public class PauseMenuUI extends BaseUI{

    private final BaseWindow baseWindow;
    private boolean closed;

    public PauseMenuUI(Eurobusiness game) {
        super(game);
        this.baseWindow = new BaseWindow(screenWidth / 2f - 150f, screenHeight / 2f - 200f, 300f, 400f, Color.BLACK);
        this.closed = false;
        this.clearScreen = false;
    }

    @Override
    public void draw(float delta) {
        baseWindow.draw();
        super.draw(delta);
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {

    }

    @Override
    void initializeLabels() {

    }

    @Override
    void initializeButtons() {
        TextButton backButton = new TextButton("Powrot", skin, "default");
        backButton.setSize(baseWindow.getWidth() - 2 * baseWindow.widthPadding(),
                baseWindow.getHeight() / 2f - 3 * baseWindow.heightPadding());
        backButton.setPosition(baseWindow.getX() + baseWindow.widthPadding(),
                baseWindow.getY() + baseWindow.getHeight() - 2 * baseWindow.heightPadding() - backButton.getHeight());

        backButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               setClosed(true);
           }
        });

        TextButton quitButton = new TextButton("Wyjdz", skin, "default");
        quitButton.setSize(baseWindow.getWidth() - 2 * baseWindow.widthPadding(),
                baseWindow.getHeight() / 2f - 3 * baseWindow.heightPadding());
        quitButton.setPosition(backButton.getX(), backButton.getY() - backButton.getHeight() - baseWindow.heightPadding());

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainMenuScreen);
            }
        });

        stage.addActor(backButton);
        stage.addActor(quitButton);
    }

    @Override
    void initializeTextFields() {

    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }
}
