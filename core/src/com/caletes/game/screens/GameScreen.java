package com.caletes.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.HeightMap;
import com.caletes.game.SiloGame;
import com.caletes.game.WorldFromHeightMapGenerator;
import com.caletes.game.drawers.ItemDrawer;
import com.caletes.game.models.World;

public class GameScreen extends ScreenAdapter {

    private static Camera camera;
    private static World world;
    private static ItemDrawer drawer;
    private static SpriteBatch batch;

    public GameScreen(SiloGame game) {
        batch = game.getBatch();
        world = createWorld();
        drawer = new ItemDrawer(world, batch);
        camera = new Camera(game.getWidth(), game.getHeight());
    }

    @Override
    public void render(float delta) {
        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawer.draw();
        batch.end();
    }

    private World createWorld() {
        HeightMap heightMap = new HeightMap("assets/heightmap5.jpg", 6);
        WorldFromHeightMapGenerator generator = new WorldFromHeightMapGenerator(heightMap);
        return generator.generate();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

}
