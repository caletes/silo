package com.caletes.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.SiloGame;
import com.caletes.game.drawers.OctreeWorldDrawer;
import com.caletes.game.models.items.OctreeWorld;
import com.caletes.game.models.items.cubes.GrassCube;
import com.caletes.game.models.items.cubes.GroundCube;
import com.caletes.game.models.items.cubes.StoneCube;

public class OctreeScreen extends ScreenAdapter {


    private OrthographicCamera camera;
    private OctreeWorld world;

    private OctreeWorldDrawer drawer;
    private SpriteBatch batch;

    public OctreeScreen(SiloGame game) {
        this.batch = game.getBatch();
        world = createWorld();
        float viewportWidth = Gdx.graphics.getWidth();
        float viewportHeight = Gdx.graphics.getHeight();
        drawer = new OctreeWorldDrawer(world, game.getBatch());
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
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

    private OctreeWorld createWorld() {
        OctreeWorld world = new OctreeWorld(4);
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
