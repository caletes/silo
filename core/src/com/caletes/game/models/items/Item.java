package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Item {

    protected Sprite sprite;
    private int originX, originY = 0;
    private boolean visible = true;

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    protected void setOrigins(int originX, int originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setPosition(int x, int y) {
        sprite.setPosition(x - originX, y - originY);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
