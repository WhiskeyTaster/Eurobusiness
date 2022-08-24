package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Eurobusiness;

public class LoadingScreen implements Screen {
    private final Eurobusiness game;
    private final ShapeRenderer shapeRenderer;

    private float progress;

    public LoadingScreen(final Eurobusiness game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.progress = 0f;
    }

    private void queueAssets() {
        game.assets.load("images/logo/splash.png", Texture.class);
        game.assets.load("ui/uiatlas.atlas", TextureAtlas.class);
        // game.assets.load("textures/colors.atlas", TextureAtlas.class);
    }

    @Override
    public void show() {
        this.progress = 0f;
        queueAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, game.camera.viewportHeight / 2 - 8, game.camera.viewportWidth - 64, 16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, game.camera.viewportHeight / 2 - 8, progress * (game.camera.viewportWidth - 64), 16);

        shapeRenderer.end();
    }

    public void update(float delta) {
        progress = MathUtils.lerp(progress, game.assets.getProgress(), .1f);
        if (game.assets.update() && progress >= game.assets.getProgress() - 0.001f) {
            // game.setScreen(game.splashScreen);
        }
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
