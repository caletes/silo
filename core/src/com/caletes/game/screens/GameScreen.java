package com.caletes.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.Logger;
import com.caletes.game.SiloGame;
import com.caletes.game.WorldGeneratorFromNoise;
import com.caletes.game.builders.ElevationsBuilder;
import com.caletes.game.drawers.ItemDrawer;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.StoneCube;

import java.util.Random;

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
        Random random = new Random();
        long seed = random.nextLong();
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(256, 256, seed, true);
        ElevationsBuilder builder = new ElevationsBuilder(generator.getElevations(), 15);
        World world = builder.build();
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
