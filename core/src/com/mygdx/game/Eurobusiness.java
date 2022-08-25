package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.SplashScreen;
import com.mygdx.game.settings.Settings;


public class Eurobusiness extends Game {

	public AssetManager assets;
	public OrthographicCamera camera;

	public Settings settings;

	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public Skin skin;
	public FontHolder fontHolder;
	
	@Override
	public void create () {
		this.settings = new Settings();
		this.settings.applySettings();

		this.assets = new AssetManager();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, settings.getScreenWidth(), settings.getScreenHeight());

		this.fontHolder = new FontHolder();
		createFonts();

		this.loadingScreen = new LoadingScreen(this);
		this.splashScreen = new SplashScreen(this);

		this.setScreen(loadingScreen);
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}

	private void createFonts() {
		fontHolder.addFont(28, Color.BLACK, "black28");
		fontHolder.addFont(28, Color.WHITE, "white28");
		fontHolder.addFont(42, Color.BLACK, "black42");
	}
}
