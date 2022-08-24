package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.settings.Settings;


public class Eurobusiness extends Game {

	public AssetManager assets;
	public OrthographicCamera camera;

	public Settings settings;

	public LoadingScreen loadingScreen;
	
	@Override
	public void create () {
		this.settings = new Settings();
		this.settings.applySettings();

		this.assets = new AssetManager();
		this.camera = new OrthographicCamera();

		this.loadingScreen = new LoadingScreen(this);

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
