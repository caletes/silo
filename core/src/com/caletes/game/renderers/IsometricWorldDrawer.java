package com.caletes.game.renderers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.PositionnedItem;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;

public class IsometricWorldDrawer {

    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 64;

    private World world;
    private SpriteBatch batch;

    public IsometricWorldDrawer(World world, SpriteBatch batch) {
        world.sortForDisplay();
        this.world = world;
        this.batch = batch;
    }

    public void draw() {
        for (PositionnedItem positionnedItem : world) {
            Item item = positionnedItem.getItem();
            Vector3 coordinates = positionnedItem.getPosition();
            Sprite sprite = new Sprite(item.getTextureRegion());
            Vector2 iso = toIsometric(coordinates, TILE_WIDTH, TILE_HEIGHT);
            sprite.setPosition(iso.x, iso.y);
            sprite.draw(batch);
        }
    }

    public Vector2 toIsometric(Vector3 coordinates, float tileWidth, float tileheight) {
        float isoX = (coordinates.x + coordinates.y) * tileWidth / 2;
        float isoY = (coordinates.y - coordinates.x) * tileheight / 2 + coordinates.z * tileheight;
        return new Vector2(isoX, isoY);
    }

}
