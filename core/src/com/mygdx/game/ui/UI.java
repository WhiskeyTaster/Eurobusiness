package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface UI {
    Batch getBatch();
    Stage getStage();
    ShapeRenderer getShapeRenderer();

    void drawShapeRenderer();
    void drawBatch();
    void initializeLabels();
    void initializeButtons();
    void initializeTextFields();

    default void draw(float delta) {
        Gdx.gl.glClearColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.setInputStage();
        this.update(delta);
        this.getStage().draw();
        this.drawShapeRenderer();
        this.drawBatch();
    }

    default void initializeStage() {
        initializeLabels();
        initializeButtons();
        initializeTextFields();
    }

    default void setInputStage() {
        Gdx.input.setInputProcessor(this.getStage());
    }

    default void update(float delta) {
        this.getStage().act(delta);
    }
}
