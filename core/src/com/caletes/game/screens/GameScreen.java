package com.caletes.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.Camera;
import com.caletes.game.IsoConverter;
import com.caletes.game.Logger;
import com.caletes.game.SiloGame;
import com.caletes.game.drawers.ChunkDrawer;
import com.caletes.game.generators.ChunkGenerator;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.World;
import com.caletes.game.models.WorldOutOfBoundsException;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.CubeSheet;
import com.caletes.game.models.tilesheet.KenneyCubeSheet;

public class GameScreen extends ScreenAdapter {


    private static Camera camera;
    private static World world;
    private static ChunkDrawer drawer;
    private static SpriteBatch batch;
    private static Logger logger;
    private static final int WORLD_SIZE = 1024;
    private static final int CHUNK_SIZE = 50;
    private static final long SEED = 0;

    public GameScreen(SiloGame game) {
        this.batch = new SpriteBatch();
        this.logger = game.getLogger();
        CubeSheet cubeSheet = new KenneyCubeSheet();
        IsoConverter isoConverter = new IsoConverter(cubeSheet.getTileWidth(), cubeSheet.getTileHeight());
        ChunkGenerator chunkGenerator = new ChunkGenerator(new CubeFactory(cubeSheet), isoConverter, SEED);

        this.world = new World(WORLD_SIZE, CHUNK_SIZE, chunkGenerator);
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight(), isoConverter);
        this.camera.setPositionToWorld(70, 40, 0);
        // this.camera.setPositionToWorld(4200, 2400, 0);
        //this.camera.setPositionToWorld(50230, 5281, 0);
        //this.camera.setPositionToWorld(50700, 50400, 0);
        this.drawer = new ChunkDrawer(batch);
    }

    @Override
    public void render(float delta) {
        drawer.processShaders(delta, camera.position);

        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Vector3 camPos = camera.getPositionFromWorld();
        logger.setCameraPosition(camPos);
        
        try {
            if (world.isWithinBounds(camPos)) {
                batch.begin();
                for (Chunk chunk : world.getChunksAround(camPos)) {
                    drawer.draw(chunk);
                }
                batch.end();
            }

        } catch (WorldOutOfBoundsException e) {
            e.printStackTrace();
        }
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
