package com.caletes.game.models.items.cubes;

import com.caletes.game.models.tilesheet.Cubesheet;

public class CubeFactory {

    protected static Cubesheet sheet;

    public CubeFactory(Cubesheet sheet) {
        this.sheet = sheet;
    }

    public GrassCube createGrassCube() {
        return new GrassCube(sheet);
    }

    public GroundCube createGroundCube() {
        return new GroundCube(sheet);
    }

    public SandCube createSandCube() {
        return new SandCube(sheet);
    }

    public SnowCube createSnowCube() {
        return new SnowCube(sheet);
    }

    public StoneCube createStoneCube() {
        return new StoneCube(sheet);
    }

    public WaterCube createWaterCube() {
        return new WaterCube(sheet);
    }

    public MarkerCube createMarkerCube() {
        return new MarkerCube(sheet);
    }
}
