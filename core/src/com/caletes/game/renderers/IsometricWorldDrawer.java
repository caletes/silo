package com.caletes.game.renderers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.models.PositionnedItem;
import com.caletes.game.models.World;

public class IsometricWorldDrawer {

    private World world;
    private SpriteBatch batch;

    public IsometricWorldDrawer(World world, SpriteBatch batch) {
        this.world = world;
        this.batch = batch;
    }

    public void draw() {
        world.sortForDisplay();
        for (PositionnedItem positionnedItem : world) {
            Sprite sprite = positionnedItem.getItem().getSprite();
            positionnedItem.updatePosition();
            sprite.draw(batch);
        }
    }

}
