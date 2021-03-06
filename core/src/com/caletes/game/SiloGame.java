package com.caletes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.screens.GameScreen;

public class SiloGame extends Game {

    private int viewportWidth, viewportHeight;
    private static SpriteBatch batch;
    private static OrthographicCamera camera;
    private static Logger logger;


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
        this.logger = new Logger(this.batch);
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        logger.setFps(Gdx.graphics.getFramesPerSecond());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        logger.render();
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
    }

    public Logger getLogger() {
        return logger;
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.viewportWidth = width;
        this.viewportHeight = height;
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        logger.setViewportWidth(width);
        logger.setViewportHeight(height);
    }

}
