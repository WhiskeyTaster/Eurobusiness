package com.mygdx.game.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.awt.*;

public class Settings {
    private final Preferences preferences;

    private float screenWidth;
    private float screenHeight;

    private boolean windowedMode;
    private int fps;

    public Settings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        this.preferences = Gdx.app.getPreferences("eurobusiness");
        this.screenWidth = preferences.getFloat("screenWidth", width);
        this.screenHeight = preferences.getFloat("screenHeight", height);

        this.windowedMode = preferences.getBoolean("windowedMode", true);
        this.fps = preferences.getInteger("fps", 60);

        update();
    }

    private void update() {
        preferences.putFloat("screenWidth", screenWidth);
        preferences.putFloat("screenHeight", screenHeight);
        preferences.putBoolean("windowedMode", windowedMode);
        preferences.putInteger("fps", fps);

        preferences.flush();
    }

    public boolean isWindowedMode() {
        return windowedMode;
    }

    public int getFps() {
        return fps;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public void applySettings() {
        applyWindowMode();
        Gdx.graphics.setForegroundFPS(fps);
    }

    public void setFps(int fps) {
        this.fps = fps;
        update();
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
        update();
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
        update();
    }

    public void setWindowedMode(boolean windowedMode) {
        this.windowedMode = windowedMode;
        update();
    }

    private void applyWindowMode() {
        if (isWindowedMode()) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        else {
            Gdx.graphics.setWindowedMode(800, 600);
        }
    }
}
