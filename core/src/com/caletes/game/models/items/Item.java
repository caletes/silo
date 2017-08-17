package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Item {

    protected Sprite sprite;
    int originX = 0;
    int originY = 0;


    protected Item(Texture texture, int x, int y, int width, int height, int originX, int originY) {
        sprite = new Sprite(new TextureRegion(texture, x, y, width, height));
        this.originX = originX;
        this.originY = originY;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setPosition(int x, int y) {
        sprite.setPosition(x - originX, y - originY);
    }

}
