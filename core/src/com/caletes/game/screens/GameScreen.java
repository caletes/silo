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
import com.caletes.game.drawers.RegionDrawer;
import com.caletes.game.generators.WorldGeneratorFromNoise;
import com.caletes.game.models.Region;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.CubeSheet;
import com.caletes.game.models.tilesheet.KenneyCubeSheet;

public class GameScreen extends ScreenAdapter {

    private static CubeFactory cubeFactory;
    private static IsoConverter isoConverter;
    private static Camera camera;
    private static Region region;
    private static RegionDrawer drawer;
    private static SpriteBatch batch;
    private static Logger logger;

    public GameScreen(SiloGame game) {
        this.batch = new SpriteBatch();
        this.logger = game.getLogger();
        CubeSheet cubeSheet = new KenneyCubeSheet();
        this.isoConverter = new IsoConverter(cubeSheet.getTileWidth(), cubeSheet.getTileHeight());
        this.cubeFactory = new CubeFactory(cubeSheet);
        this.region = createRegion();
        this.camera = new Camera(game.getViewportWidth(), game.getViewportHeight(), isoConverter);
        this.camera.setPositionToWorld(127, 127, 1);
        this.drawer = new RegionDrawer(batch, camera);
    }

    @Override
    public void render(float delta) {
        logger.setCameraWorldPosition(camera.getPositionFromWorld());
        logger.setBranchExponent(drawer.getBranchExponent());
        handleInput();
        camera.handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        drawer.draw(region);
    }

    private Region createRegion() {
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(256, 256, 0, 0, 0, false, false);
        ElevationsBuilder builder = new ElevationsBuilder(generator.getElevations(), 15, cubeFactory, isoConverter);
        return builder.build();
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
