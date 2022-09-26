package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.owners.Bank;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.owners.Player;
import com.mygdx.game.screens.*;
import com.mygdx.game.settings.Settings;

import java.util.ArrayList;
import java.util.HashMap;


public class Eurobusiness extends Game {

	static int currentPlayer = 0;
	static int tour = 0;

	public AssetManager assets;
	public OrthographicCamera camera;

	public Settings settings;

	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	public GameSetupScreen gameSetupScreen;
	public CreatePlayerScreen createPlayerScreen;
	public GameScreen gameScreen;

	public Skin skin;
	public FontHolder fontHolder;

	public Pair<Integer, Integer> numberOfPlayers;
	public Bank bank;
	public ArrayList<Player> players;
	public HashMap<Player, Integer> hotelBuyCounter;

	@Override
	public void create () {
		this.settings = new Settings();
		this.settings.applySettings();

		this.assets = new AssetManager();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, settings.getScreenWidth(), settings.getScreenHeight());

		this.fontHolder = new FontHolder();
		createFonts();

		this.numberOfPlayers = new Pair<>();
		this.players = new ArrayList<>();

		this.loadingScreen = new LoadingScreen(this);
		this.splashScreen = new SplashScreen(this);
		this.mainMenuScreen = new MainMenuScreen(this);
		this.gameSetupScreen = new GameSetupScreen(this);
		this.createPlayerScreen = new CreatePlayerScreen(this);
		this.gameScreen = new GameScreen(this);

		this.setScreen(loadingScreen);
		this.hotelBuyCounter = new HashMap<>();
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
		skin.dispose();
	}

	private void createFonts() {
		fontHolder.addFont(28, Color.BLACK, "black28");
		fontHolder.addFont(28, Color.WHITE, "white28");
		fontHolder.addFont(42, Color.BLACK, "black42");
	}

	public void nextPlayer() {
		currentPlayer++;
		if (currentPlayer >= players.size()) {
			currentPlayer = 0;
			tour++;
		}
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}

	public void clean() {
		this.players = new ArrayList<>();
		tour = 0;
        currentPlayer = 0;
        Owner.resetId();
		this.bank = null;
		this.hotelBuyCounter = new HashMap<>();
	}
}
