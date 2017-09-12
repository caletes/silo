package com.caletes.game.models.items.cubes;


import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class WaterCube extends Cube {
    WaterCube(WorldPosition worldPosition, CubeSheet cubeSheet) {
        super(worldPosition, cubeSheet, "water");
        setAlpha(0.5f);
    }
}
