package com.caletes.game.models.items.cubes;


import com.caletes.game.models.tilesheet.Cubesheet;

public class MarkerCube extends Cube {
    MarkerCube(Cubesheet sheet) {
        super(sheet, sheet.getMarker()[0], sheet.getMarker()[1]);
    }
}
