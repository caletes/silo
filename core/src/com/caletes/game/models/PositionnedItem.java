package com.caletes.game.models;

import com.caletes.game.models.items.Item;

public class PositionnedItem {
    private Item item;
    private Coordinates position;

    public PositionnedItem(Item item, Coordinates position) {
        this.item = item;
        this.position = position;
    }

    public Item getItem() {
        return item;
    }

    public Coordinates getPosition() {
        return position;
    }

    public static int compare(PositionnedItem item1, PositionnedItem item2) {
        if (item1.getPosition().getZ() < item2.getPosition().getZ())
            return -1;
        if (item1.getPosition().getZ() > item2.getPosition().getZ())
            return 1;
        if (item1.getPosition().getY() < item2.getPosition().getY())
            return 1;
        if (item1.getPosition().getY() > item2.getPosition().getY())
            return -1;
        if (item1.getPosition().getX() < item2.getPosition().getX())
            return -1;
        if (item1.getPosition().getX() > item2.getPosition().getX())
            return 1;
        return 0;
    }
}
