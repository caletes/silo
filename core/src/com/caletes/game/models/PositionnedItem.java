package com.caletes.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.actors.Player;

public class PositionnedItem {
    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 64;
    private Item item;
    private Vector3 position;

    public PositionnedItem(Item item, Vector3 position) {
        this.item = item;
        this.position = position;
        updateSpritePosition();
    }

    public Item getItem() {
        return item;
    }

    public Vector3 getPosition() {
        return position;
    }

    public static int compare(PositionnedItem item1, PositionnedItem item2) {
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



    private void updateSpritePosition() {
        float isoX = (position.x + position.y) * TILE_WIDTH / 2;
        float isoY = (position.y - position.x) * TILE_HEIGHT / 2 + position.z * TILE_HEIGHT;


        Vector2 itemOrigin = item.getOrigin();
        Sprite sprite = item.getSprite();
        sprite.setPosition(isoX - itemOrigin.x, isoY - itemOrigin.y);
    }

    public void updatePosition() {
        Vector2 itemOrigin = item.getOrigin();
        float x = item.getSprite().getX() + itemOrigin.x;
        float y = item.getSprite().getY() + itemOrigin.y;
        float mapX = 1 - (y / TILE_HEIGHT - x / TILE_WIDTH);
        float mapY = x / TILE_WIDTH + y / TILE_HEIGHT - 1;
        position = new Vector3(Math.round(mapX), Math.round(mapY), position.z);
    }
}
