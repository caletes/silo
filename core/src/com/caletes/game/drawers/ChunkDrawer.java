package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;

public class ChunkDrawer {

    private static SpriteBatch batch;
    private static ShaderSwitcher shaders;


    public ChunkDrawer(SpriteBatch batch) {
        this.batch = batch;
        this.shaders = new ShaderSwitcher(batch);
    }

    public void draw(Chunk chunk) {
        for (Node octree : chunk) {
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
        shaders.backToDefault();
    }

    public void processShaders(float delta, Vector3 camPos) {
        shaders.process(delta, camPos);
    }

}
