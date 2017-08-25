package com.caletes.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.*;
import com.caletes.game.drawers.ItemDrawer;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.StoneCube;

public class GameScreen extends ScreenAdapter {

    private static Camera camera;
    private static World world;
    private static ItemDrawer drawer;
    private static SpriteBatch batch;
    private static Logger logger;

    public GameScreen(SiloGame game) {
        this.batch = game.getBatch();
        this.logger = game.getLogger();
        this.world = createWorld();
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight());
        this.camera.setPositionToWorld(40, 40, 6);
        this.drawer = new ItemDrawer(world, batch, camera);
    }

    @Override
    public void render(float delta) {
        logger.setCameraWorldPosition(camera.getPositionFromWorld());
        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawer.draw();
        batch.end();
    }

    private World createWorld() {
        HeightMap heightMap = new HeightMap("assets/heightmaps/_generated_heightmap.png", 20);
        WorldFromHeightMapGenerator generator = new WorldFromHeightMapGenerator(heightMap);
        World world = generator.generate();
        world.pushObjectAt(new StoneCube(), 40, 40, 6);
        return world;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

}
