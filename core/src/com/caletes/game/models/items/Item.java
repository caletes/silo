package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.caletes.game.models.WorldPosition;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {

    private WorldPosition worldPosition;
    private List<Sprite> sprites = new ArrayList<>();
    private float originX, originY = 0;

    public void setWorldPosition(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public WorldPosition getWorldPosition() {
        return worldPosition;
    }

    protected void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    protected void setOrigins(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public void setSpritesPosition(float x, float y) {
        for (Sprite sprite : sprites) {
            sprite.setPosition(x - originX, y - originY);
        }
    }

    public void setAlpha(float alpha) {
        for (Sprite sprite : sprites) {
            sprite.setAlpha(alpha);
        }
    }

    public void setColor(float r, float g, float b, float a) {
        for (Sprite sprite : sprites) {
            sprite.setColor(r, g, b, a);
        }
    }
}
