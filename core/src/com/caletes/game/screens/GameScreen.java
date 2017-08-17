package com.caletes.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.SiloGame;
import com.caletes.game.models.World;
import com.caletes.game.models.items.actors.Player;
import com.caletes.game.models.items.cubes.GrassCube;
import com.caletes.game.models.items.cubes.GroundCube;
import com.caletes.game.models.items.cubes.StoneCube;
import com.caletes.game.renderers.IsometricWorldDrawer;

public class GameScreen extends ScreenAdapter {


    private OrthographicCamera camera;
    private World world;
    private Player player;
    private IsometricWorldDrawer drawer;
    private SpriteBatch batch;

    public GameScreen(SiloGame game) {
        this.batch = game.getBatch();
        world = createWorld();
        player = new Player();
        world.add(player, new Vector3(1, 0, 1));
        drawer = new IsometricWorldDrawer(world, game.getBatch());
        float viewportWidth = Gdx.graphics.getWidth();
        float viewportHeight = Gdx.graphics.getHeight();
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

    private World createWorld() {
        World world = new World();
        world.add(new GroundCube(), new Vector3(0, 0, 1));
        world.add(new GrassCube(), new Vector3(1, 0, 0));
        world.add(new StoneCube(), new Vector3(2, 1, 0));
        world.add(new GrassCube(), new Vector3(0, 1, 0));
        world.add(new GrassCube(), new Vector3(1, 1, 0));
        world.add(new GroundCube(), new Vector3(1, 2, 0));
        world.add(new GroundCube(), new Vector3(1, 2, 1));
        world.add(new GrassCube(), new Vector3(1, 2, 2));
        world.add(new StoneCube(), new Vector3(2, 0, 0));
        world.add(new GrassCube(), new Vector3(0, 0, 2));
        world.add(new GrassCube(), new Vector3(0, 2, 0));
        world.add(new GroundCube(), new Vector3(0, 0, 0));
        world.add(new StoneCube(), new Vector3(2, 2, 0));
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
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            player.moveToLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveToRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            player.moveToUp();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveToDown();
        }
    }
}