package com.caletes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.screens.GameScreen;

public class SiloGame extends Game {

    private SpriteBatch batch;
    private FPSLogger logger = new FPSLogger();


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        logger.log();
        super.render();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
