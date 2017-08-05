package com.caletes.game.models;


import com.caletes.game.models.items.Item;

import java.util.ArrayList;

public class World extends ArrayList<PositionnedItem> {
    public void add(Item item, Coordinates position){
        add(new PositionnedItem(item, position));
        sort();
    }

    private void sort(){
        this.sort(PositionnedItem::compare);
    }

}
