package com.caletes.game.models.items.cubes;


import com.badlogic.gdx.graphics.Color;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class StoneCube extends Cube {
    StoneCube(WorldPosition worldPosition, CubeSheet cubeSheet) {
        super(worldPosition, cubeSheet, "stone");
        setBorderColor(new Color(115f / 255, 138f / 255, 140f / 255, 1));
    }
}
