package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.tilesheet.KenneyCubesheet;
import com.caletes.game.models.tilesheet.Cubesheet;

public abstract class Cube extends Item {

    protected Cube(Cubesheet sheet, int x, int y) {
        super();
        Texture texture = sheet.getTexture();
        int width = sheet.getSpriteWidth();
        int height = sheet.getSpriteHeight();
        setSprite(new Sprite(new TextureRegion(texture, x * width, y * height, width, height)));
        setOrigins(sheet.getOriginX(), sheet.getOriginY());
    }


}
