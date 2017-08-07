package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Item {
    protected Sprite sprite;

    protected Item(String image, int x, int y, int width, int height) {
        sprite = new Sprite(new TextureRegion(new Texture(image), x, y, width, height));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract Vector2 getOrigin();
}
