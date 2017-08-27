package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.Cubesheet;

public class SnowCube extends Cube {
    SnowCube(Cubesheet sheet) {
        super(sheet, sheet.getSnow()[0], sheet.getSnow()[1]);
    }
}
