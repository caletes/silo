package com.caletes.game.models.spritesheet;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PlayerSheet {

    private TextureAtlas atlas;

    public PlayerSheet() {
        this.atlas = new TextureAtlas("assets/atlas/kenney/kenney.atlas");
    }

    public int getOriginX() {
        return 111 / 2;
    }

    public int getOriginY() {
        return 128 - 64 / 2;
    }

    public TextureAtlas.AtlasRegion getTexture(String name) {
        return atlas.findRegion(name);
    }
}
