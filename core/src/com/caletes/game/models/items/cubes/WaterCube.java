package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.Cubesheet;

public class WaterCube extends Cube {
    WaterCube(Cubesheet sheet) {
        super(sheet, sheet.getWater()[0], sheet.getWater()[1]);
    }
}
