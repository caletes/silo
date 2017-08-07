package com.caletes.game.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.Item;

import java.util.ArrayList;

public class World extends ArrayList<PositionnedItem> {

    private Vector2 origin = new Vector2(0, 0);

    public void add(Item item, Vector3 position) {
        add(new PositionnedItem(item, position));
    }

    public void sortForDisplay() {
        this.sort(PositionnedItem::compare);
    }

    public void rotate(Vector2 pivot, int dir) {
        for (PositionnedItem positionnedItem : this) {
            positionnedItem.rotate(pivot, dir);
        }
    }
}
