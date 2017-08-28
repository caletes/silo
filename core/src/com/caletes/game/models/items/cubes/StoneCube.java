package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.Cubesheet;

public class StoneCube extends Cube {
    StoneCube(Cubesheet sheet) {
        super(sheet, sheet.getStone()[0], sheet.getStone()[1]);
    }
}
