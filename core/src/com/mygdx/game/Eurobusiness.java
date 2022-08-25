package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.SplashScreen;
import com.mygdx.game.settings.Settings;


public class Eurobusiness extends Game {

	public AssetManager assets;
	public OrthographicCamera camera;

	public Settings settings;

	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	
	@Override
	public void create () {
		this.settings = new Settings();
		this.settings.applySettings();

		this.assets = new AssetManager();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, settings.getScreenWidth(), settings.getScreenHeight());

		this.loadingScreen = new LoadingScreen(this);
		this.splashScreen = new SplashScreen(this);

		this.setScreen(loadingScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
