package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.math.Vector2;
import com.caletes.game.IsoConverter;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class CubeFactory {

    protected static CubeSheet cubeSheet;
    protected static IsoConverter isoConverter;

    public CubeFactory(CubeSheet atlas, IsoConverter isoConverter) {
        this.cubeSheet = atlas;
        this.isoConverter = isoConverter;
    }

    public GrassCube createGrassCube(WorldPosition worldPosition, boolean borderLeft, boolean borderRight) {
        GrassCube cube = new GrassCube(worldPosition, cubeSheet);
        cube.setBorders(borderLeft,borderRight);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public GroundCube createGroundCube(WorldPosition worldPosition, boolean borderLeft, boolean borderRight) {
        GroundCube cube = new GroundCube(worldPosition, cubeSheet);
        cube.setBorders(borderLeft,borderRight);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public SandCube createSandCube(WorldPosition worldPosition, boolean borderLeft, boolean borderRight) {
        SandCube cube = new SandCube(worldPosition, cubeSheet);
        cube.setBorders(borderLeft,borderRight);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public SnowCube createSnowCube(WorldPosition worldPosition, boolean borderLeft, boolean borderRight) {
        SnowCube cube = new SnowCube(worldPosition, cubeSheet);
        cube.setBorders(borderLeft,borderRight);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public StoneCube createStoneCube(WorldPosition worldPosition, boolean borderLeft, boolean borderRight) {
        StoneCube cube = new StoneCube(worldPosition, cubeSheet);
        cube.setBorders(borderLeft,borderRight);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public WaterCube createWaterCube(WorldPosition worldPosition, boolean borderLeft, boolean borderRight) {
        WaterCube cube = new WaterCube(worldPosition, cubeSheet);
        cube.setBorders(borderLeft,borderRight);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public MarkerCube createMarkerCube(WorldPosition worldPosition, boolean borderLeft, boolean borderRight) {
        MarkerCube cube = new MarkerCube(worldPosition, cubeSheet);
        cube.setBorders(borderLeft,borderRight);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    private void setSpritePosition(WorldPosition worldPosition, Cube cube) {
        Vector2 screenPosition = isoConverter.toScreen(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
        cube.setSpritesPosition(screenPosition.x, screenPosition.y);
    }
}
