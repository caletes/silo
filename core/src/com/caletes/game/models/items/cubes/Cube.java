package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.Texture;
import com.caletes.game.models.items.Item;

public abstract class Cube extends Item {

    private static final int SIZE = 128;
    private static Texture texture = new Texture("assets/spritesets/basic_ground_tiles.png");

    protected Cube(int x, int y) {
        super(texture, x * SIZE, y * SIZE, SIZE, SIZE, 64, 96);
    }
}
