package com.caletes.game.models.items.tiles;

import com.caletes.game.models.items.Item;

abstract class Tile extends Item {
    protected Tile(String image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }
}
