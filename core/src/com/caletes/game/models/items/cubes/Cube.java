package com.caletes.game.models.items.cubes;

import com.caletes.game.models.items.Item;

abstract class Cube extends Item {
    protected Cube(String image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }
}
