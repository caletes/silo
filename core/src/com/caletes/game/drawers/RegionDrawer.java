package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.Camera;
import com.caletes.game.models.Region;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;
import com.caletes.game.octree.OctreeOutOfBoundsException;

import java.util.List;

public class RegionDrawer implements Drawer {

    public static final int DEFAULT_BRANCH_EXPONENT = 5;
    public static int branchExponent;

    private static Region region;
    private static SpriteBatch batch;
    private static Camera camera;
    private static ShaderSwitcher shaders;


    public RegionDrawer(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.camera = camera;
        this.shaders = new ShaderSwitcher(batch);
        this.branchExponent = DEFAULT_BRANCH_EXPONENT;
    }

    public void draw(Region region) {
        this.region = region;
        int[] cameraPosition = camera.getPositionFromWorld();

        shaders.process();

        batch.begin();
        try {
            int cameraX = cameraPosition[0];
            int cameraY = cameraPosition[1];
            int cameraZ = (int) camera.position.z;
            if (region.isWithinBounds(cameraX, cameraY, cameraZ)) {
                Node sub = region.getBranch(cameraX, cameraY, cameraZ, getBranchExponent());
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

    public int getBranchExponent() {
        return branchExponent;
    }


    public void setBranchExponent(int exponent) {
        this.branchExponent = Math.max(0, Math.min(exponent, region.getExponent()));
    }
}
