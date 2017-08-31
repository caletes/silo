package com.caletes.game.models.items.cubes;

import com.caletes.game.models.tilesheet.CubeSheet;

public class CubeFactory {

    protected static CubeSheet cubeSheet;

    public CubeFactory(CubeSheet atlas) {
        this.cubeSheet = atlas;
    }

    public GrassCube createGrassCube() {
        return new GrassCube(cubeSheet);
    }

    public GroundCube createGroundCube() {
        return new GroundCube(cubeSheet);
    }

    public SandCube createSandCube() {
        return new SandCube(cubeSheet);
    }

    public SnowCube createSnowCube() {
        return new SnowCube(cubeSheet);
    }

    public StoneCube createStoneCube() {
        return new StoneCube(cubeSheet);
    }

    public WaterCube createWaterCube() {
        return new WaterCube(cubeSheet);
    }

    public MarkerCube createMarkerCube() {
        return new MarkerCube(cubeSheet);
    }
}
