package com.caletes.game.models.items.cubes;


import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class SandCube extends Cube {
    SandCube(WorldPosition worldPosition, CubeSheet cubeSheet) {
        super(worldPosition, cubeSheet, "sand");
    }
}
