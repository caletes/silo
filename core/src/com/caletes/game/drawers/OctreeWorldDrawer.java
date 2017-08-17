package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.OctreeWorld;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.MortonCode;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;

public class OctreeWorldDrawer {

    private OctreeWorld world;
    private SpriteBatch batch;

    public OctreeWorldDrawer(OctreeWorld world, SpriteBatch batch) {
        this.world = world;
        this.batch = batch;
    }

    public void draw() {
        NodeIterator it = this.world.iterator();
        while (it.hasNext()) {
            Node<Cube> node = it.next();
            if (node != null) {
                Cube cube = node.getObject();
                if (cube != null) {
                    MortonCode.Vector3 position = node.getPosition();
                    cube.setPosition(new Vector3(position.x, position.y, position.z));
                    cube.getSprite().draw(batch);
                }
            }
        }
    }

}
