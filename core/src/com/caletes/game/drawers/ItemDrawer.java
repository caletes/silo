package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;
import com.caletes.game.octree.Octree;

public class ItemDrawer implements Drawer {


    private static Octree<Item> items;
    private static SpriteBatch batch;

    public ItemDrawer(Octree<Item> items, SpriteBatch batch) {
        this.items = items;
        this.batch = batch;
    }

    public void draw() {
        NodeIterator it = items.iterator();
        while (it.hasNext()) {
            Node<Cube> node = it.next();
            if (node != null) {
                Item item = node.getObject();
                if (item != null) {
                    item.getSprite().draw(batch);
                }
            }
        }
    }

}
