package com.caletes.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.*;
import com.caletes.game.builders.ElevationsBuilder;
import com.caletes.game.drawers.WorldDrawer;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.Cubesheet;
import com.caletes.game.models.tilesheet.KenneyCubesheet;
import com.caletes.game.octree.Node;

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
        this.batch = game.getBatch();
        this.logger = game.getLogger();
        Cubesheet cubesheet = new KenneyCubesheet();
        this.isoConverter = new IsoConverter(cubesheet.getTileWidth(), cubesheet.getTileHeight());
        this.cubeFactory = new CubeFactory(cubesheet);
        this.world = createWorld();
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight(), isoConverter);
        int peak = world.getPeakNode(127, 127, 0).getPosition().z;
        this.camera.setPositionToWorld(127, 127, peak + 1);
        this.drawer = new WorldDrawer(world, batch, camera);
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
        ElevationsBuilder builder = new ElevationsBuilder(generator.getElevations(), 15, cubeFactory, isoConverter);
        World world = builder.build();
        Node peak = world.getPeakNode(127, 127, 0);
        world.pushObjectAt(cubeFactory.createMarkerCube(), 127, 127, peak.getPosition().z + 1);
        return world;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

}
