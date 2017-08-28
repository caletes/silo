package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;
import com.caletes.game.octree.Octree;

import java.util.List;

public class WorldDrawer implements Drawer {


    private static World world;
    private static SpriteBatch batch;
    private static Camera camera;

    public WorldDrawer(World world, SpriteBatch batch, Camera camera) {
        this.world = world;
        this.batch = batch;
        this.camera = camera;
    }

    public void draw() {
        int[] cameraPosition = camera.getPositionFromWorld();
        int substractExponent = Math.min(4, world.getExponent());
        Node sub = world.substract(cameraPosition[0], cameraPosition[1], (int) camera.position.z, substractExponent);
        List<Node> nodes = sub.withNeighbors();
        for (Node octree : nodes) {
            NodeIterator it = octree.iterator();
            while (it.hasNext()) {
                Node<Cube> node = it.next();
                if (node != null && world.isVisible(node)) {
                    Item item = node.getObject();
                    if (item != null) {
                        item.getSprite().draw(batch);
                    }
                }
            }
        }
    }

}
