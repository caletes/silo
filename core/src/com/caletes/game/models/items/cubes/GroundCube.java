package com.caletes.game.models.items.cubes;


import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class GroundCube extends Cube {
    GroundCube(WorldPosition worldPosition, CubeSheet cubeSheet) {
        super(worldPosition, cubeSheet, "ground");
    }
}
