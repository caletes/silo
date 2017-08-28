package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.Cubesheet;

public class GroundCube extends Cube {
    GroundCube(Cubesheet sheet) {
        super(sheet, sheet.getGround()[0], sheet.getGround()[1]);
    }
}
