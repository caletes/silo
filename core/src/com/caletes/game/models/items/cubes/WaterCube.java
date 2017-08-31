package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.CubeSheet;

public class WaterCube extends Cube {
    WaterCube(CubeSheet cubeSheet) {
        super(cubeSheet, "water");
        sprite.setAlpha(0.5f);
    }
}
