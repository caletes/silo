package com.caletes.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.IsoConverter;
import com.caletes.game.Logger;
import com.caletes.game.SiloGame;
import com.caletes.game.builders.ElevationsBuilder;
import com.caletes.game.drawers.ChunkDrawer;
import com.caletes.game.generators.WorldGeneratorFromNoise;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.CubeSheet;
import com.caletes.game.models.tilesheet.KenneyCubeSheet;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class GameScreen extends ScreenAdapter {

    private static CubeFactory cubeFactory;
    private static IsoConverter isoConverter;
    private static Camera camera;
    private static World world;
    private static ChunkDrawer drawer;
    private static SpriteBatch batch;
    private static Logger logger;
    private static final int WORLD_SIZE = 16;
    private static final int CHUNK_SIZE = 256;

    public GameScreen(SiloGame game) {
        this.batch = new SpriteBatch();
        this.logger = game.getLogger();
        CubeSheet cubeSheet = new KenneyCubeSheet();
        this.isoConverter = new IsoConverter(cubeSheet.getTileWidth(), cubeSheet.getTileHeight());
        this.cubeFactory = new CubeFactory(cubeSheet);
        this.world = new World(WORLD_SIZE);
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight(), isoConverter);
        this.camera.setPositionToWorld(127, 127, 1);
        this.drawer = new ChunkDrawer(batch);
    }

    @Override
    public void render(float delta) {
        logger.setCameraWorldPosition(camera.getPositionFromWorld());
        logger.setBranchExponent(drawer.getBranchExponent());
        handleInput();
        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        int[] camPos = camera.getPositionFromWorld();
        int camX = camPos[0];
        int camY = camPos[1];
        int camZ = (int) camera.position.z;
        int worldX = camX / CHUNK_SIZE;
        int worldY = camY / CHUNK_SIZE;
        int worldZ = camZ / CHUNK_SIZE;
        try {
            Chunk chunk = world.getObjectAt(worldX, worldY, worldZ);
            if (chunk == null) {
                chunk = generateChunk(CHUNK_SIZE, worldX, worldY);
                world.pushObjectAt(chunk, worldX, worldY, worldZ);
            }
            drawer.draw(chunk, camX - (worldX * CHUNK_SIZE), camY - (worldY * CHUNK_SIZE), camZ);

        } catch (OctreeOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private Chunk generateChunk(int chunkSize, int worldX, int worldY) {
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(chunkSize, chunkSize, worldX * chunkSize, worldY * chunkSize, 0, false, true);
        ElevationsBuilder builder = new ElevationsBuilder(generator.getElevations(), 15, cubeFactory, isoConverter, CHUNK_SIZE);
        return builder.build(worldX, worldY);
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

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            drawer.setBranchExponent(drawer.getBranchExponent() + 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.M))
            drawer.setBranchExponent(drawer.getBranchExponent() - 1);
    }
}
