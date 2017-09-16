package com.caletes.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.Player;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.spritesheet.CubeSheet;
import com.caletes.game.models.spritesheet.KenneyCubeSheet;

public class GameScreen extends ScreenAdapter {


    private static Camera camera;
    private static World world;
    private static ChunkDrawer drawer;
    private static CubeFactory cubeFactory;
    private static SpriteBatch batch;
    private static Logger logger;
    private static Player player;
    private static final int WORLD_SIZE = 1024;
    private static final int CHUNK_SIZE = 50;
    private static final long SEED = 0;

    public GameScreen(SiloGame game) {
        this.batch = new SpriteBatch();
        this.logger = game.getLogger();
        CubeSheet cubeSheet = new KenneyCubeSheet();
        IsoConverter isoConverter = IsoConverter.createInstance(cubeSheet.getTileWidth(), cubeSheet.getTileHeight());
        this.cubeFactory = new CubeFactory(cubeSheet);
        ChunkGenerator chunkGenerator = new ChunkGenerator(cubeFactory, SEED);

        this.world = new World(WORLD_SIZE, CHUNK_SIZE, chunkGenerator);
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight());
        this.camera.setWorldPosition(new WorldPosition(50, 50, 9));
        this.drawer = new ChunkDrawer(batch);

        this.player = new Player();
        try {
            world.pushItem(player, new WorldPosition(50, 50, 9));
        } catch (WorldOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        playerInput();
        drawer.processShaders(delta, camera.position);
        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        WorldPosition camPos = camera.getWorldPosition();
        logger.setCameraPosition(camPos);
        logger.setPlayerPosition(player.getWorldPosition());
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

    public void playerInput() {
        try {
            if (Gdx.input.isKeyPressed(Input.Keys.Z))
                move(new Vector3(0, -0.2f, 0));
            else if (Gdx.input.isKeyPressed(Input.Keys.D))
                move(new Vector3(0.2f, 0, 0));
            else if (Gdx.input.isKeyPressed(Input.Keys.S))
                move(new Vector3(0, 0.2f, 0));
            else if (Gdx.input.isKeyPressed(Input.Keys.Q))
                move(new Vector3(-0.2f, 0, 0));
            else if (Gdx.input.isKeyJustPressed(Input.Keys.E))
                move(new Vector3(0, 0, 1f));
            else if (Gdx.input.isKeyJustPressed(Input.Keys.A))
                move(new Vector3(0, 0, -1f));
        } catch (WorldOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void move(Vector3 move) throws WorldOutOfBoundsException {

        WorldPosition worldPosition = player.getWorldPosition();
        Vector3 next = move.add(worldPosition.getPosition());
        WorldPosition nextWP = new WorldPosition(next);
        Item item = world.getItem(nextWP);
        if (nextWP.getItemPositionInChunk(CHUNK_SIZE).equals(worldPosition.getItemPositionInChunk(CHUNK_SIZE)) || item == null || item == player) {
            world.removeItem(worldPosition);
            world.pushItem(player, nextWP);
            camera.setWorldPosition(nextWP);
        }
    }

}
