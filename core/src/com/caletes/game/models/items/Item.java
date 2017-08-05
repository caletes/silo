package com.caletes.game.models.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Item {
    private TextureRegion textureRegion;

    protected Item(String image, int x, int y, int width, int height) {
        textureRegion = new TextureRegion(new Texture(image), x, y, width, height);
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
