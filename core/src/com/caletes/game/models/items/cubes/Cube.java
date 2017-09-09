package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.tilesheet.CubeSheet;

public abstract class Cube extends Item {
    protected Cube(WorldPosition worldPosition, CubeSheet cubeSheet, String name) {
        super();
        setWorldPosition(worldPosition);
        setSprite(new Sprite(cubeSheet.getTexture(name)));
        setOrigins(cubeSheet.getOriginX(), cubeSheet.getOriginY());
    }

    public void setBorders(boolean borderLeft, boolean borderRight) {
        if (borderLeft || borderRight) {
            sprite.setColor(0.95f, 0.95f, 0.95f, 1f);
        }
    }
}
