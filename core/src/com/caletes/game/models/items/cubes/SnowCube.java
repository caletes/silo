package com.caletes.game.models.items.cubes;


import com.badlogic.gdx.graphics.Color;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.spritesheet.CubeSheet;

public class SnowCube extends Cube {
    SnowCube(CubeSheet cubeSheet) {
        super(cubeSheet, "snow");
        setBorderColor(new Color(212f / 255, 208f / 255, 200f / 255, 1));
    }
}
