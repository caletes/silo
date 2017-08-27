package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.Cubesheet;

public class SandCube extends Cube {
    SandCube(Cubesheet sheet) {
        super(sheet, sheet.getSand()[0], sheet.getSand()[1]);
    }
}
