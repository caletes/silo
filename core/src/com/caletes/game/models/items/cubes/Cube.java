package com.caletes.game.models.items.cubes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.tilesheet.CubeSheet;

public abstract class Cube extends Item {
    protected Cube(WorldPosition worldPosition, CubeSheet cubeSheet, String name) {
        super();
        setWorldPosition(worldPosition);
        setSprite(new Sprite(cubeSheet.getTexture(name)));
        setOrigins(cubeSheet.getOriginX(), cubeSheet.getOriginY());

        // Changement de teinte en fonction de la heuteur pair ou impair
        //boolean odd = (int) worldPosition.getZ() % 2 == 1;
        //float brightness = odd ? 1f : 0.98f;
        //sprite.setColor(brightness, brightness, brightness, 1f);
    }
}
