package com.caletes.game.models;

import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.Item;

public class PositionnedItem {
    private Item item;
    private Vector3 position;

    public PositionnedItem(Item item, Vector3 position) {
        this.item = item;
        this.position = position;
    }

    public Item getItem() {
        return item;
    }

    public Vector3 getPosition() {
        return position;
    }

    public static int compare(PositionnedItem item1, PositionnedItem item2) {
        if (item1.getPosition().z < item2.getPosition().z)
            return -1;
        if (item1.getPosition().z > item2.getPosition().z)
            return 1;
        if (item1.getPosition().y < item2.getPosition().y)
            return 1;
        if (item1.getPosition().y > item2.getPosition().y)
            return -1;
        if (item1.getPosition().x < item2.getPosition().x)
            return -1;
        if (item1.getPosition().x > item2.getPosition().x)
            return 1;
        return 0;
    }
}
