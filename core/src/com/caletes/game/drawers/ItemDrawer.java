package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;
import com.caletes.game.octree.Octree;

import java.util.List;

public class ItemDrawer implements Drawer {


    private static Octree<Item> items;
    private static SpriteBatch batch;
    private static Camera camera;

    public ItemDrawer(Octree<Item> items, SpriteBatch batch, Camera camera) {
        this.items = items;
        this.batch = batch;
        this.camera = camera;
    }

    public void draw() {
        int[] cameraPosition = camera.getPositionFromWorld();
        int substractExponent = Math.min(4, items.getExponent());
        Node sub = items.substract(cameraPosition[0], cameraPosition[1], (int) camera.position.z, substractExponent);
        List<Node> nodes = sub.withNeighbors();
        for (Node octree : nodes) {
            NodeIterator it = octree.iterator();
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

}
