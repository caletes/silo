package com.caletes.game.renderers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caletes.game.models.Coordinates2D;
import com.caletes.game.models.Coordinates3D;
import com.caletes.game.models.PositionnedItem;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;

public class IsometricWorldDrawer {

    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 64;

    private World world;
    private SpriteBatch batch;

    public IsometricWorldDrawer(World world, SpriteBatch batch) {
        this.world = world;
        this.batch = batch;
    }

    public void draw() {
        for (PositionnedItem positionnedItem : world) {
            Item item = positionnedItem.getItem();
            Coordinates3D coordinates = positionnedItem.getPosition();
            Sprite sprite = new Sprite(item.getTextureRegion());
            Coordinates2D iso = coordinates.toIsometric(TILE_WIDTH, TILE_HEIGHT);
            sprite.setPosition(iso.getX(), iso.getY());
            sprite.draw(batch);
        }
    }

}
