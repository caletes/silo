package com.caletes.game.models.items.cubes;


import com.badlogic.gdx.graphics.Color;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class GroundCube extends Cube {
    GroundCube(WorldPosition worldPosition, CubeSheet cubeSheet) {
        super(worldPosition, cubeSheet, "ground");
        setBorderColor(new Color(169f / 255, 123f / 255, 79f / 255, 1));
    }
}
