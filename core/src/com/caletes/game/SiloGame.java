package com.caletes.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.models.Coordinates;
import com.caletes.game.models.World;
import com.caletes.game.models.items.tiles.GrassTile;
import com.caletes.game.models.items.tiles.GroundTile;
import com.caletes.game.models.items.tiles.StoneTile;
import com.caletes.game.renderers.IsometricWorldDrawer;

public class SiloGame extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    World world;
    @Override
    public void create() {
        world = createWorld();
        batch = new SpriteBatch();
        float viewportWidth = Gdx.graphics.getWidth();
        float viewportHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(viewportWidth,viewportHeight);
    }

    @Override
    public void render() {

        handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        IsometricWorldDrawer drawer = new IsometricWorldDrawer(world, batch);
        drawer.draw();
        batch.end();
    }

    private World createWorld() {
        World world = new World();
        world.add(new GrassTile(), new Coordinates(1, 0, 0));
        world.add(new GrassTile(), new Coordinates(0, 1, 0));
        world.add(new GroundTile(), new Coordinates(0, 0, 0));
        world.add(new GrassTile(), new Coordinates(1, 1, 0));
        world.add(new StoneTile(), new Coordinates(2, 1, 0));
        world.add(new StoneTile(), new Coordinates(2, 0, 0));
        world.add(new GrassTile(), new Coordinates(1,2,0));
        return world;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }
    }

}
