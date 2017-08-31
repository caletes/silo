package com.caletes.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.*;
import com.caletes.game.builders.ElevationsBuilder;
import com.caletes.game.drawers.WorldDrawer;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.CubeSheet;
import com.caletes.game.models.tilesheet.KenneyCubeSheet;

import java.util.Random;

public class GameScreen extends ScreenAdapter {

    private static CubeFactory cubeFactory;
    private static IsoConverter isoConverter;
    private static Camera camera;
    private static World world;
    private static WorldDrawer drawer;
    private static SpriteBatch batch;
    private static Logger logger;

    public GameScreen(SiloGame game) {
        this.batch = new SpriteBatch();
        this.logger = game.getLogger();
        CubeSheet cubeSheet = new KenneyCubeSheet();
        this.isoConverter = new IsoConverter(cubeSheet.getTileWidth(), cubeSheet.getTileHeight());
        this.cubeFactory = new CubeFactory(cubeSheet);
        this.world = createWorld();
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight(), isoConverter);
        this.camera.setPositionToWorld(127, 127, 1);
        this.drawer = new WorldDrawer(world, batch, camera);
    }

    @Override
    public void render(float delta) {
        logger.setCameraWorldPosition(camera.getPositionFromWorld());
        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        drawer.draw();
    }

    private World createWorld() {
        Random random = new Random();
        long seed = random.nextLong();
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(256, 256, seed, true);
        ElevationsBuilder builder = new ElevationsBuilder(generator.getElevations(), 15, cubeFactory, isoConverter);
        return builder.build();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
