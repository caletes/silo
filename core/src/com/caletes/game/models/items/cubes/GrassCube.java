package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.Cubesheet;

public class GrassCube extends Cube {
    GrassCube(Cubesheet sheet) {
        super(sheet, sheet.getGrass()[0], sheet.getGrass()[1]);
    }
}
