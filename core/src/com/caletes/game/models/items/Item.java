package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Item {

    protected Sprite sprite;
    private float originX, originY = 0;

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    protected void setOrigins(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x - originX, y - originY);
    }
}
