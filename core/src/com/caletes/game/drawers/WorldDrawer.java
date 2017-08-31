package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;
import com.caletes.game.octree.OctreeOutOfBoundsException;

import java.util.List;

public class WorldDrawer implements Drawer {

    public static final int SUBSTRACT_EXPONENT = 6;

    private static World world;
    private static SpriteBatch batch;
    private static Camera camera;
    private static ShaderSwitcher shaders;


    public WorldDrawer(World world, SpriteBatch batch, Camera camera) {
        this.world = world;
        this.batch = batch;
        this.camera = camera;
        this.shaders = new ShaderSwitcher(batch);
    }

    public void draw() {
        int[] cameraPosition = camera.getPositionFromWorld();

        shaders.process();

        batch.begin();
        try {
            int cameraX = cameraPosition[0];
            int cameraY = cameraPosition[1];
            int cameraZ = (int) camera.position.z;
            if (world.isWithinBounds(cameraX, cameraY, cameraZ)) {
                Node sub = world.substract(cameraX, cameraY, cameraZ, getSubstractExponent());
                List<Node> nodes = sub.withNeighbors();
                for (Node octree : nodes) {
                    NodeIterator it = octree.iterator();
                    while (it.hasNext()) {
                        Node<Cube> node = it.next();
                        if (node != null) {
                            Item item = node.getObject();
                            if (item != null) {
                                shaders.switchFor(item);
                                item.getSprite().draw(batch);
                            }
                        }
                    }
                }
            }
        } catch (OctreeOutOfBoundsException e) {
            e.printStackTrace();
        }
        batch.end();
        shaders.backToDefault();
    }

    private int getSubstractExponent() {
        return Math.min(SUBSTRACT_EXPONENT, world.getExponent());
    }

}
