package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.tilesheet.KenneyCubesheet;
import com.caletes.game.models.tilesheet.Cubesheet;

public abstract class Cube extends Item {

    protected static Cubesheet cubesheet = new KenneyCubesheet();

    protected Cube(int x, int y) {
        super();
        Texture texture = cubesheet.getTexture();
        int width = cubesheet.getSpriteWidth();
        int height = cubesheet.getSpriteHeight();
        setSprite(new Sprite(new TextureRegion(texture, x * width, y * height, width, height)));
        setOrigins(cubesheet.getOriginX(), cubesheet.getOriginY());
    }


}
