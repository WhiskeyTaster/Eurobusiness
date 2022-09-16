package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Eurobusiness;

import java.util.Objects;

public abstract class BaseUI{
    protected final Eurobusiness game;
    protected final ShapeRenderer shapeRenderer;
    protected final SpriteBatch batch;
    protected final Skin skin;
    protected final Stage stage;

    protected final int screenWidth;
    protected final int screenHeight;

    protected final float widthPadding;
    protected final float heightPadding;
    protected boolean clearScreen;

    public BaseUI(final Eurobusiness game) {
        this.game = Objects.requireNonNull(game, "Game is null");
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.skin = game.skin;
        this.stage = new Stage();

        this.screenWidth = (int) game.settings.getScreenWidth();
        this.screenHeight = (int) game.settings.getScreenHeight();

        this.widthPadding = this.screenWidth * 0.1f;
        this.heightPadding = this.screenHeight * 0.1f;
        this.clearScreen = true;
    }

    public void draw(float delta) {
        if (clearScreen) {
            Gdx.gl.glClearColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        this.setInputStage();
        this.update(delta);
        this.stage.draw();
        this.drawShapeRenderer();
        this.drawBatch();
    }

    public void initializeStage() {
        initializeLabels();
        initializeButtons();
        initializeTextFields();
    }

    public void setInputStage() {
        Gdx.input.setInputProcessor(this.stage);
    }

    public void update(float delta) {
        this.stage.act(delta);
    }

    abstract void drawShapeRenderer();

    abstract void drawBatch();

    abstract void initializeLabels();

    abstract void initializeButtons();

    abstract void initializeTextFields();

}
