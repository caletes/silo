package com.caletes.game.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.Item;

import java.util.ArrayList;

public class World extends ArrayList<PositionnedItem> {

    public void add(Item item, Vector3 position) {
        add(new PositionnedItem(item, position));
    }

    public void sortForDisplay() {
        this.sort(PositionnedItem::compare);
    }
}
