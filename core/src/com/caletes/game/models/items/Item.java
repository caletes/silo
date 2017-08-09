package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Item {
    protected Sprite sprite;
    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 64;
    private Vector3 position;

    protected Item(String image, int x, int y, int width, int height) {
        sprite = new Sprite(new TextureRegion(new Texture(image), x, y, width, height));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract Vector2 getOrigin();

    public void setPosition(Vector3 position) {
        this.position = position;
        Vector2 screenPosition = worldToScreenPosition(position);
        sprite.setPosition(screenPosition.x, screenPosition.y);
    }
    public void setSpritePosition(Vector2 position) {
        this.sprite.setPosition(position.x, position.y);
        this.position = screenToWorldPosition(position);
    }

    public static int compare(Item item1, Item item2) {
        if (item1.position.z < item2.position.z)
            return -1;
        if (item1.position.z > item2.position.z)
            return 1;
        if (item1.position.y < item2.position.y)
            return 1;
        if (item1.position.y > item2.position.y)
            return -1;
        if (item1.position.x < item2.position.x)
            return -1;
        if (item1.position.x > item2.position.x)
            return 1;
        return 0;
    }

    private Vector2 worldToScreenPosition(Vector3 worldPosition) {
        float isoX = (worldPosition.x + worldPosition.y) * TILE_WIDTH / 2;
        float isoY = (worldPosition.y - worldPosition.x) * TILE_HEIGHT / 2 + worldPosition.z * TILE_HEIGHT;
        Vector2 itemOrigin = getOrigin();
        return new Vector2(isoX - itemOrigin.x, isoY - itemOrigin.y);
    }

    public Vector3 screenToWorldPosition(Vector2 screenPosition) {
        Vector2 itemOrigin = getOrigin();
        float x = screenPosition.x + itemOrigin.x;
        float y = screenPosition.y + itemOrigin.y;
        float mapX = 1 - (y / TILE_HEIGHT - x / TILE_WIDTH);
        float mapY = x / TILE_WIDTH + y / TILE_HEIGHT - 1;
        return new Vector3(Math.round(mapX), Math.round(mapY), position.z);
    }
}
