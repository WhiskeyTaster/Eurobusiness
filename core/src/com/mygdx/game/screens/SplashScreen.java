package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Eurobusiness;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

public class SplashScreen implements Screen {
    private final Eurobusiness game;
    private final Stage stage;

    public SplashScreen(final Eurobusiness game) {
        this.game = game;
        this.stage = new Stage(new StretchViewport(this.game.settings.getScreenWidth(), this.game.settings.getScreenHeight(),
                this.game.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Image splashImage = createImage();

        Runnable runAction = new Runnable() {
            @Override
            public void run() {
                // game.setScreen(game.mainMenuScreen);
            }
        };

        splashImage.addAction(sequence(alpha(0f), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f, 2f, 2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - splashImage.getWidth() / 2,
                                stage.getHeight() / 2,
                                2f,
                                Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(runAction)));

        stage.addActor(splashImage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();
    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        stage.dispose();
    }

    private Image createImage() {
        Resolution resolution = game.settings.getResolution();
        Texture splashTex = game.assets.get("images/logo/splash.png", Texture.class);
        Image splashImage = new Image(splashTex);
        if (resolution == Resolution.RESOLUTION_1920_1080) {
            splashImage.setSize(splashImage.getWidth() * 3, splashImage.getHeight() * 2);
        } else {
            splashImage.setSize(splashImage.getWidth() * 2, splashImage.getHeight() * 1);
        }
        splashImage.setOrigin(splashImage.getWidth() / 2, splashImage.getHeight() / 2);
        splashImage.setPosition(stage.getWidth() / 2 - splashImage.getWidth() / 2,
                stage.getHeight() + splashImage.getHeight());
        return splashImage;
    }
}
