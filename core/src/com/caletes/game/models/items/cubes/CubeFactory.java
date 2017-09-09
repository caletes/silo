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

    public GrassCube createGrassCube(WorldPosition worldPosition) {
        GrassCube cube = new GrassCube(worldPosition, cubeSheet);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public GroundCube createGroundCube(WorldPosition worldPosition) {
        GroundCube cube = new GroundCube(worldPosition, cubeSheet);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public SandCube createSandCube(WorldPosition worldPosition) {
        SandCube cube = new SandCube(worldPosition, cubeSheet);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public SnowCube createSnowCube(WorldPosition worldPosition) {
        SnowCube cube = new SnowCube(worldPosition, cubeSheet);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public StoneCube createStoneCube(WorldPosition worldPosition) {
        StoneCube cube = new StoneCube(worldPosition, cubeSheet);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public WaterCube createWaterCube(WorldPosition worldPosition) {
        WaterCube cube = new WaterCube(worldPosition, cubeSheet);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    public MarkerCube createMarkerCube(WorldPosition worldPosition) {
        MarkerCube cube = new MarkerCube(worldPosition, cubeSheet);
        setSpritePosition(worldPosition, cube);
        return cube;
    }

    private void setSpritePosition(WorldPosition worldPosition, Cube cube) {
        Vector2 screenPosition = isoConverter.toScreen(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
        cube.setSpritePosition(screenPosition.x, screenPosition.y);
    }
}
