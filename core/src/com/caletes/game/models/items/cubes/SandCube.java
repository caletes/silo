package com.caletes.game.models.items.cubes;


import com.badlogic.gdx.graphics.Color;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.tilesheet.CubeSheet;

public class SandCube extends Cube {
    SandCube(WorldPosition worldPosition, CubeSheet cubeSheet) {
        super(worldPosition, cubeSheet, "sand");
        setBorderColor(new Color(200f / 255, 167f / 255, 85f / 255, 1));
    }
}
