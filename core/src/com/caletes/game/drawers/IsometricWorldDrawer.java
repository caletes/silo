package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;

public class IsometricWorldDrawer {

    private World world;
    private SpriteBatch batch;

    public IsometricWorldDrawer(World world, SpriteBatch batch) {
        this.world = world;
        this.batch = batch;
    }

    public void draw() {
        world.sortForDisplay();
        for (Item item : world) {
            item.getSprite().draw(batch);
        }
    }

}
