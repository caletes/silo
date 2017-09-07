package com.caletes.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.*;
import com.caletes.game.builders.ChunkBuilder;
import com.caletes.game.drawers.ChunkDrawer;
import com.caletes.game.generators.ElevationsGenerator;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.World;
import com.caletes.game.models.WorldOutOfBoundsException;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.CubeSheet;
import com.caletes.game.models.tilesheet.KenneyCubeSheet;
import com.caletes.game.octree.Direction;

public class GameScreen extends ScreenAdapter {

    private static CubeFactory cubeFactory;
    private static IsoConverter isoConverter;
    private static Camera camera;
    private static World world;
    private static ChunkDrawer drawer;
    private static SpriteBatch batch;
    private static Logger logger;
    private static final int WORLD_SIZE = 1024;
    private static final int CHUNK_SIZE = 50;
    private static long seed = 0;

    public GameScreen(SiloGame game) {
        this.batch = new SpriteBatch();
        this.logger = game.getLogger();
        CubeSheet cubeSheet = new KenneyCubeSheet();
        this.isoConverter = new IsoConverter(cubeSheet.getTileWidth(), cubeSheet.getTileHeight());
        this.cubeFactory = new CubeFactory(cubeSheet);
        this.world = new World(WORLD_SIZE);
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight(), isoConverter);
        this.camera.setPositionToWorld(70, 40, 0);
        // this.camera.setPositionToWorld(4200, 2400, 0);
        //this.camera.setPositionToWorld(50230, 5281, 0);
        //this.camera.setPositionToWorld(50700, 50400, 0);
        this.drawer = new ChunkDrawer(batch);
    }

    @Override
    public void render(float delta) {
        int[] camPos = camera.getPositionFromWorld();

        logger.setCameraWorldPosition(camPos);
        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        int camX = camPos[0];
        int camY = camPos[1];
        int camZ = (int) camera.position.z;

        int chunkX = camX / CHUNK_SIZE;
        int chunkY = camY / CHUNK_SIZE;
        int chunkZ = camZ / CHUNK_SIZE;

        drawer.processShaders(delta, camera.position);
        try {
            if (world.isIn(chunkX, chunkY, chunkZ)) {
                batch.begin();
                for (Direction cardinal : Direction.getCardinals()) {
                    Direction.Delta cardinalDt = cardinal.getDelta();
                    int nextX = chunkX + cardinalDt.x;
                    int nextY = chunkY + cardinalDt.y;
                    int nextZ = chunkZ + cardinalDt.z;
                    if (world.isIn(nextX, nextY, nextZ)) {
                        Chunk nextChunk = world.getChunk(nextX, nextY, nextZ);
                        if (nextChunk == null) {
                            nextChunk = generateChunk(CHUNK_SIZE, chunkX + cardinalDt.x, chunkY + cardinalDt.y, seed);
                            world.setChunk(nextChunk, chunkX + cardinalDt.x, chunkY + cardinalDt.y, chunkZ + cardinalDt.z);
                        }
                        drawer.draw(nextChunk);
                    }
                }
                batch.end();
            }

        } catch (WorldOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private Chunk generateChunk(int chunkSize, int chunkX, int chunkY, long seed) {
        ElevationsGenerator generator = new ElevationsGenerator(chunkX * chunkSize, chunkY * chunkSize,chunkSize, seed);
        Elevations elevations = generator.generate();
        ChunkBuilder builder = new ChunkBuilder(elevations, 30, cubeFactory, isoConverter);
        return builder.build(chunkX, chunkY);
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
