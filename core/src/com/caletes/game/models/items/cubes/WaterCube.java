package com.caletes.game.models.items.cubes;


import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.spritesheet.CubeSheet;

public class WaterCube extends Cube {
    WaterCube(CubeSheet cubeSheet) {
        super(cubeSheet, "water");
        setAlpha(0.5f);
    }
}
