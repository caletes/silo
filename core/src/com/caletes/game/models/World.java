package com.caletes.game.models;


import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.Item;

import java.util.ArrayList;

public class World extends ArrayList<Item> {

    public void add(Item item, Vector3 position) {
        item.setPosition(position);
        super.add(item);
    }

    public void sortForDisplay() {
        this.sort(Item::compare);
    }
}
