package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.math.Vector2;
import com.caletes.game.models.items.Item;

abstract class Cube extends Item {
    protected Cube(int x, int y) {
        super("basic_ground_tiles.png", x, y, 128,128);
    }
    public Vector2 getOrigin(){
        return new Vector2(64, 96);
    }
}
