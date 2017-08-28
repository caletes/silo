package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;

import java.util.List;

public class WorldDrawer implements Drawer {

    public static final int SUBSTRACT_EXPONENT = 5;

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
        Node sub = world.substract(cameraPosition[0], cameraPosition[1], (int) camera.position.z, getSubstractExponent());
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

    private int getSubstractExponent() {
        return Math.min(SUBSTRACT_EXPONENT, world.getExponent());
    }

}
