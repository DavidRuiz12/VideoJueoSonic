package com.mygdx.game.Screens;

import static com.mygdx.game.Extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.Extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameStart extends BaseScreen{
    private Stage stage;
    private Image background;
    private Image start;

    public GameStart(MainGame mainGame) {
        super(mainGame);

    }

    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    public void addStart(){
        this.start = new Image(mainGame.assetManager.getStart());
        this.start.setPosition(0,0);
        this.start.setSize(2f,4f);
        this.stage.addActor(this.start);
    }

    @Override
    public void render(float delta) {
        this.stage.draw();
        this.stage.act();
        if (Gdx.input.justTouched()){
            mainGame.setScreen(new GameScreen(mainGame));
        }
    }

    @Override
    public void show() {
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);
        addBackground();
        addStart();

    }


    @Override
    public void dispose() {
        this.stage.dispose();
    }
}

