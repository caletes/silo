package com.caletes.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.Item;

public class PositionnedItem {
    private Item item;
    private Vector3 position;
    private Vector3 screenPosition;

    public PositionnedItem(Item item, Vector3 position) {
        this.item = item;
        this.position = position;
        this.screenPosition = position;
    }

    public Item getItem() {
        return item;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getDisplayPosition() {
        return screenPosition;
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
    }
}
