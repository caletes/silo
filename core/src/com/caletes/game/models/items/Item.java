package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.caletes.game.models.WorldPosition;

public abstract class Item {

    private WorldPosition worldPosition;
    protected Sprite sprite;
    private float originX, originY = 0;

    public void setWorldPosition(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public WorldPosition getWorldPosition() {
        return worldPosition;
    }

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

    public void setSpritePosition(float x, float y) {
        sprite.setPosition(x - originX, y - originY);
    }
}
