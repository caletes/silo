package com.caletes.game.screens;


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
import com.caletes.game.octree.Direction;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.OctreeOutOfBoundsException;

import java.util.List;

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

    public GameScreen(SiloGame game) {
        this.batch = new SpriteBatch();
        this.logger = game.getLogger();
        CubeSheet cubeSheet = new KenneyCubeSheet();
        this.isoConverter = new IsoConverter(cubeSheet.getTileWidth(), cubeSheet.getTileHeight());
        this.cubeFactory = new CubeFactory(cubeSheet);
        this.world = new World(WORLD_SIZE);
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight(), isoConverter);
        //this.camera.setPositionToWorld(2371, 1416, 1);
        this.camera.setPositionToWorld(1, 1, 1);
        this.drawer = new ChunkDrawer(batch);
    }

    @Override
    public void render(float delta) {
        logger.setCameraWorldPosition(camera.getPositionFromWorld());
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
            if (world.isWithinBounds(worldX, worldY, worldZ)) {
                drawer.processShaders();
                Node chunkNode = world.getLeafAt(worldX, worldY, worldZ);
                List<Direction> cardinals = Direction.getCardinals();
                for (Direction direction : cardinals) {
                    Node nextNode = chunkNode.getNextOn(direction);
                    Chunk nextChunk = null;
                    if (nextNode != null) {
                        nextChunk = (Chunk) nextNode.getObject();
                    }
                    if (nextChunk == null) {
                        Direction.Delta delta1 = direction.getDelta();
                        int nextX = worldX + delta1.x;
                        int nextY = worldY + delta1.y;
                        int nextZ = worldZ + delta1.z;
                        if (world.isWithinBounds(nextX, nextY, nextZ)) {
                            nextChunk = generateChunk(CHUNK_SIZE, worldX + delta1.x, worldY + delta1.y);
                            world.pushObjectAt(nextChunk, worldX + delta1.x, worldY + delta1.y, worldZ + delta1.z);
                        }
                    }
                    if (nextChunk != null) {
                        drawer.draw(nextChunk);
                    }
                }
            }

        } catch (OctreeOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private Chunk generateChunk(int chunkSize, int worldX, int worldY) {
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(chunkSize, chunkSize, worldX * chunkSize, worldY * chunkSize, 0);
        ElevationsBuilder builder = new ElevationsBuilder(generator.getElevations(), 20, cubeFactory, isoConverter, CHUNK_SIZE);
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
}
