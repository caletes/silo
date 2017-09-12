package com.caletes.game.models.items.cubes;

import com.caletes.game.IsoConverter;
import com.caletes.game.models.spritesheet.CubeSheet;

public class CubeFactory {

    protected static CubeSheet cubeSheet;
    protected static IsoConverter isoConverter;

    public CubeFactory(CubeSheet atlas, IsoConverter isoConverter) {
        this.cubeSheet = atlas;
        this.isoConverter = isoConverter;
    }

    public GrassCube createGrassCube(boolean borderLeft, boolean borderRight) {
        GrassCube cube = new GrassCube(cubeSheet);
        cube.setBorders(borderLeft, borderRight);
        return cube;
    }

    public GroundCube createGroundCube(boolean borderLeft, boolean borderRight) {
        GroundCube cube = new GroundCube(cubeSheet);
        cube.setBorders(borderLeft, borderRight);
        return cube;
    }

    public SandCube createSandCube(boolean borderLeft, boolean borderRight) {
        SandCube cube = new SandCube(cubeSheet);
        cube.setBorders(borderLeft, borderRight);
        return cube;
    }

    public SnowCube createSnowCube(boolean borderLeft, boolean borderRight) {
        SnowCube cube = new SnowCube(cubeSheet);
        cube.setBorders(borderLeft, borderRight);
        return cube;
    }

    public StoneCube createStoneCube(boolean borderLeft, boolean borderRight) {
        StoneCube cube = new StoneCube(cubeSheet);
        cube.setBorders(borderLeft, borderRight);
        return cube;
    }

    public WaterCube createWaterCube(boolean borderLeft, boolean borderRight) {
        WaterCube cube = new WaterCube(cubeSheet);
        cube.setBorders(borderLeft, borderRight);
        return cube;
    }

    public MarkerCube createMarkerCube(boolean borderLeft, boolean borderRight) {
        MarkerCube cube = new MarkerCube(cubeSheet);
        cube.setBorders(borderLeft, borderRight);
        return cube;
    }
}
