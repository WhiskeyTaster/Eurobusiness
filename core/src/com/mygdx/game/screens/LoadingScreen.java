package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Eurobusiness;

public class LoadingScreen implements Screen {

    private final Eurobusiness game;
    private final ShapeRenderer shapeRenderer;

    private int widthPadding;
    private int loadingBarHeight;

    private float progress;
    private Skin skin;

    public LoadingScreen(final Eurobusiness game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.progress = 0f;
        this.skin = new Skin();

        setWidthPadding();
        setLoadingBarHeight();
    }

    private void loadResourcesToSkin() {
        skin.addRegions(game.assets.get("ui/uiatlas.atlas", TextureAtlas.class));
        skin.add("default-font", game.fontHolder.getFont("black28"));
        skin.add("white-font", game.fontHolder.getFont("white28"));
        skin.add("big-font", game.fontHolder.getFont("black42"));
        skin.load(Gdx.files.internal("ui/uiskin.json"));
    }

    @Override
    public void show() {
        this.progress = 0f;
        queueAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(widthPadding, game.camera.viewportHeight / 2 - loadingBarHeight / 2f,
                game.camera.viewportWidth - 2 * widthPadding, loadingBarHeight);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(widthPadding, game.camera.viewportHeight / 2 - loadingBarHeight / 2f,
                progress * (game.camera.viewportWidth - 2 * widthPadding), loadingBarHeight);

        shapeRenderer.end();
    }

    public void update(float delta) {
        progress = MathUtils.lerp(progress, game.assets.getProgress(), .1f);
        if (game.assets.update() && progress >= game.assets.getProgress() - 0.001f) {
            loadResourcesToSkin();
            game.setSkin(skin);
            game.setScreen(game.splashScreen);
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

    private void setWidthPadding() {
        if ((int) game.settings.getScreenWidth() == 1920) {
            int WIDTH_PADDING_1920 = 32;
            this.widthPadding = WIDTH_PADDING_1920;
        } else {
            int WIDTH_PADDING_800 = 13;
            this.widthPadding = WIDTH_PADDING_800;
        }
    }

    private void setLoadingBarHeight() {
        if ((int) game.settings.getScreenHeight() == 1080) {
            int LOADING_BAR_HEIGHT_1080 = 24;
            this.loadingBarHeight = LOADING_BAR_HEIGHT_1080;
        } else {
            int LOADING_BAR_HEIGHT_600 = 13;
            this.loadingBarHeight = LOADING_BAR_HEIGHT_600;
        }
    }

    private void queueAssets() {
        game.assets.load("images/logo/splash.png", Texture.class);
        game.assets.load("ui/uiatlas.atlas", TextureAtlas.class);
        // game.assets.load("textures/colors.atlas", TextureAtlas.class);
    }
}
