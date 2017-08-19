package com.caletes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.screens.GameScreen;

public class SiloGame extends Game {

    private int width, height;
    private static SpriteBatch batch;
    private static BitmapFont font;
    private static OrthographicCamera camera;
    

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera(width, height);
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        displayFps();
        batch.end();
    }

    private void displayFps() {
        int padding = 10;
        font.draw(batch, Gdx.graphics.getFramesPerSecond() + " fps", -width / 2 + padding, height / 2 - padding);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.width = width;
        this.height = height;
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

}
