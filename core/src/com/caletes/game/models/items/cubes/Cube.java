package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.tilesheet.KenneyTilesheet;
import com.caletes.game.models.tilesheet.Tilesheet;

public abstract class Cube extends Item {

    protected static Tilesheet tilesheet = new KenneyTilesheet();

    protected Cube(int x, int y) {
        super();
        Texture texture = tilesheet.getTexture();
        int width = tilesheet.getTileWidth();
        int height = tilesheet.getTileHeight();
        setSprite(new Sprite(new TextureRegion(texture, x * width, y * height, width, height)));
        setOrigins(tilesheet.getOriginX(), tilesheet.getOriginY());
    }
}
