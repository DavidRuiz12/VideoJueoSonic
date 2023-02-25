package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Extra.AssetMan;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Extra.AssetMan;
import com.mygdx.game.Screens.GameStart;


public class MainGame extends Game {

	public AssetMan assetManager;
	private GameScreen gameScreen;
	private GameStart gameStart;

	@Override
	public void create(){
		this.assetManager = new AssetMan();

		this.gameScreen = new GameScreen(this);


		setScreen(new GameStart(this));
	}

}
