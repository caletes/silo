package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.Texture;
import com.caletes.game.models.items.Item;

public abstract class Cube extends Item {

    private static Texture texture = new Texture("assets/basic_ground_tiles.png");

    protected Cube(int x, int y) {
        super(texture, x, y, 128, 128, 64, 96);
    }
}
