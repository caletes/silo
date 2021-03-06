package com.caletes.game.models.items.cubes;


import com.badlogic.gdx.graphics.Color;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.spritesheet.CubeSheet;

public class GrassCube extends Cube {
    GrassCube(CubeSheet cubeSheet) {
        super(cubeSheet, "grass");
        setBorderColor(new Color(115f / 255, 170f / 255, 27f / 255, 1));
    }
}
