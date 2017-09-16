package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.caletes.game.models.WorldPosition;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {

    private WorldPosition worldPosition;
    private List<Sprite> sprites = new ArrayList<>();
    private float originX, originY = 0;
    private float width, height, depth = 0;

    public Item(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public void setWorldPosition(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public void applyWorldPosition() {
        setSpritesPosition(worldPosition.getSpritePosition());
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

    public void setSpritesPosition(Vector2 position) {
        for (Sprite sprite : sprites) {
            sprite.setPosition(position.x - originX, position.y - originY);
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDepth() {
        return depth;
    }
}
