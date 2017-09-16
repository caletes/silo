package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.spritesheet.CubeSheet;

public abstract class Cube extends Item {

    private CubeSheet cubeSheet;

    private Color borderColor;

    protected Cube(CubeSheet cubeSheet, String name) {
        super(1,1,1);
        this.cubeSheet = cubeSheet;
        addSprite(new Sprite(cubeSheet.getTexture(name)));
        setOrigins(cubeSheet.getOriginX(), cubeSheet.getOriginY());
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorders(boolean borderLeft, boolean borderRight) {
        Sprite borders = null;
        if (borderLeft && !borderRight)
            borders = new Sprite(cubeSheet.getTexture("border_left"));
        else if (!borderLeft && borderRight)
            borders = new Sprite(cubeSheet.getTexture("border_right"));
        else if (borderLeft && borderRight)
            borders = new Sprite(cubeSheet.getTexture("border_left_right"));

        if (borders != null) {
            if (borderColor != null)
                borders.setColor(borderColor);
            addSprite(borders);
        }
    }

}
