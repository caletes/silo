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
    private Vector3 displayPosition;

    public PositionnedItem(Item item, Vector3 position) {
        this.item = item;
        this.position = position;
        this.displayPosition = position;
        updateSpritePosition();
    }

    public Item getItem() {
        return item;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getDisplayPosition() {
        return displayPosition;
    }


    public static int compare(PositionnedItem item1, PositionnedItem item2) {
        if (item1.getDisplayPosition().z < item2.getDisplayPosition().z)
            return -1;
        if (item1.getDisplayPosition().z > item2.getDisplayPosition().z)
            return 1;
        if (item1.getDisplayPosition().y < item2.getDisplayPosition().y)
            return 1;
        if (item1.getDisplayPosition().y > item2.getDisplayPosition().y)
            return -1;
        if (item1.getDisplayPosition().x < item2.getDisplayPosition().x)
            return -1;
        if (item1.getDisplayPosition().x > item2.getDisplayPosition().x)
            return 1;
        return 0;
    }

    public void rotate(Vector2 pivot, int dir) {
        Vector3 position3D = this.getDisplayPosition();
        Vector2 position2D = new Vector2(position3D.x, position3D.y);
        position2D.x -= pivot.x;
        position2D.y -= pivot.y;
        position2D.rotate90(dir);
        position2D.x += pivot.x;
        position2D.y += pivot.y;
        position3D.x = position2D.x;
        position3D.y = position2D.y;
        updateSpritePosition();
    }

    private void updateSpritePosition() {
        float isoX = (displayPosition.x + displayPosition.y) * TILE_WIDTH / 2;
        float isoY = (displayPosition.y - displayPosition.x) * TILE_HEIGHT / 2 + displayPosition.z * TILE_HEIGHT;


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

        displayPosition = new Vector3(Math.round(mapX), Math.round(mapY), position.z);

        if (item instanceof Player) {

            System.out.println(position);
        }
    }
}
