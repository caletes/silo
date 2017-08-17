package com.caletes.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.SiloGame;
import com.caletes.game.drawers.ItemDrawer;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.models.items.cubes.GrassCube;
import com.caletes.game.models.items.cubes.GroundCube;
import com.caletes.game.models.items.cubes.StoneCube;

public class GameScreen extends ScreenAdapter {


    private OrthographicCamera camera;
    private World world;

    private ItemDrawer drawer;
    private SpriteBatch batch;

    public GameScreen(SiloGame game) {
        batch = game.getBatch();
        //world = createWorld1();
        world = createWorld2();
        drawer = new ItemDrawer(world, batch);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawer.draw();
        batch.end();
    }

    private World createWorld1() {
        World world = new World(4);
        world.pushObjectAt(new GroundCube(), 0, 0, 1);
        world.pushObjectAt(new GrassCube(), 1, 0, 0);
        world.pushObjectAt(new StoneCube(), 2, 1, 0);
        world.pushObjectAt(new GrassCube(), 0, 1, 0);
        world.pushObjectAt(new GrassCube(), 1, 1, 0);
        world.pushObjectAt(new GroundCube(), 1, 2, 0);
        world.pushObjectAt(new GroundCube(), 1, 2, 1);
        world.pushObjectAt(new GrassCube(), 1, 2, 2);
        world.pushObjectAt(new StoneCube(), 2, 0, 0);
        world.pushObjectAt(new GrassCube(), 0, 0, 2);
        world.pushObjectAt(new GrassCube(), 0, 2, 0);
        world.pushObjectAt(new GroundCube(), 0, 0, 0);
        world.pushObjectAt(new StoneCube(), 2, 2, 0);
        return world;
    }

    private World createWorld2() {
        int size = 64;
        World world = new World(size);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < 3; z++) {
                    Cube cube = z < 2 ? new GroundCube() : new GrassCube();
                    world.pushObjectAt(cube, x, y, z);
                }
            }
        }
        return world;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP)) {
            if (camera.zoom > 1)
                camera.zoom = camera.zoom - 0.5f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_DOWN)) {
            if (camera.zoom < 2)
                camera.zoom = camera.zoom + 0.5f;
        }
    }
}
