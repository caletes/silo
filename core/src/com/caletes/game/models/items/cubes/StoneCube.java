package com.caletes.game.models.items.cubes;


import com.badlogic.gdx.graphics.Color;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.spritesheet.CubeSheet;

public class StoneCube extends Cube {
    StoneCube(CubeSheet cubeSheet) {
        super(cubeSheet, "stone");
        setBorderColor(new Color(115f / 255, 138f / 255, 140f / 255, 1));
    }
}
