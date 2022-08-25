package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface UI {
    Stage getStage();
    void initializeLabels();
    void initializeButtons();
    void initializeTextFields();

    default void initializeStage() {
        initializeLabels();
        initializeButtons();
        initializeTextFields();
    }

    default void draw(float delta) {
        this.update(delta);
        this.getStage().draw();
    }

    default void update(float delta) {
        this.getStage().act(delta);
    }
}
