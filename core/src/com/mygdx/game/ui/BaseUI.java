package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Eurobusiness;
import org.jetbrains.annotations.NotNull;

// TODO: add protected final int resolution and padding

public abstract class BaseUI{
    private final Eurobusiness game;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final Skin skin;
    private final Stage stage;

    public BaseUI(final @NotNull Eurobusiness game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.skin = game.skin;
        this.stage = new Stage();
    }

    public Batch getBatch() {
        return batch;
    }

    public Eurobusiness getGame() {
        return game;
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void draw(float delta) {
        Gdx.gl.glClearColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.setInputStage();
        this.update(delta);
        this.getStage().draw();
        this.drawShapeRenderer();
        this.drawBatch();
    }

    public void initializeStage() {
        initializeLabels();
        initializeButtons();
        initializeTextFields();
    }

    public void setInputStage() {
        Gdx.input.setInputProcessor(this.getStage());
    }

    public void update(float delta) {
        this.getStage().act(delta);
    }

    abstract void drawShapeRenderer();

    abstract void drawBatch();

    abstract void initializeLabels();

    abstract void initializeButtons();

    abstract void initializeTextFields();

}
