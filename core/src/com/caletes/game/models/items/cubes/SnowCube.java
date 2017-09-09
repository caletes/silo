package com.caletes.game.models.items.cubes;


import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class SnowCube extends Cube {
    SnowCube(WorldPosition worldPosition, CubeSheet cubeSheet) {
        super(worldPosition, cubeSheet, "snow");
    }
}
