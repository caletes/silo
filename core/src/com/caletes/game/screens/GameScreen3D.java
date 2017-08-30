package com.caletes.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.caletes.game.Camera3D;
import com.caletes.game.IsoConverter;
import com.caletes.game.SiloGame;
import com.caletes.game.WorldGeneratorFromNoise;
import com.caletes.game.builders.ElevationsBuilder;
import com.caletes.game.drawers.WorldDrawer3D;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.Cubesheet;
import com.caletes.game.models.tilesheet.KenneyCubesheet;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.OctreeOutOfBoundsException;

import java.util.Random;

public class GameScreen3D extends ScreenAdapter {

    private Camera3D camera;
    private ModelBatch batch;
    private WorldDrawer3D drawer;
    private CubeFactory cubeFactory;
    private static IsoConverter isoConverter;

    public GameScreen3D(SiloGame game) {
        Cubesheet cubesheet = new KenneyCubesheet();
        isoConverter = new IsoConverter(cubesheet.getTileWidth(), cubesheet.getTileHeight());
        cubeFactory = new CubeFactory(cubesheet);
        camera = new Camera3D(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(127, 0, 200);
        camera.lookAt(127, 127, 0);
        camera.near = 1f;
        camera.far = 500f;
        camera.update();
        batch = new ModelBatch();
        drawer = new WorldDrawer3D(createWorld(), batch, camera);
        drawer.build();
    }

    @Override
    public void render(float delta) {
        camera.handleInput();
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        drawer.draw();
    }

    private World createWorld() {
        Random random = new Random();
        long seed = random.nextLong();
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(256, 256, seed, true);
        ElevationsBuilder builder = new ElevationsBuilder(generator.getElevations(), 15, cubeFactory, isoConverter);
        World world = builder.build();
        return world;
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
}
