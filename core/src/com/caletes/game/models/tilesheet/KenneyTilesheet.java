package com.caletes.game.models.tilesheet;

import com.badlogic.gdx.graphics.Texture;

public class KenneyTilesheet extends Tilesheet {
    private static Texture texture = new Texture("assets/spritesets/kenney_tilesheet.png");
    private static final int TILE_WIDTH = 111;
    private static final int TILE_HEIGHT = 128;


    public KenneyTilesheet() {
        super(texture, TILE_WIDTH, TILE_HEIGHT);
    }

    @Override
    public int[] getGrass() {
        return new int[]{0, 0};
    }

    @Override
    public int[] getGround() {
        return new int[]{1, 0};
    }

    @Override
    public int[] getStone() {
        return new int[]{10, 1};
    }

    @Override
    public int[] getSand() {
        return new int[]{4, 4};
    }

    @Override
    public int[] getSnow() {
        return new int[]{11, 2};
    }

    @Override
    public int[] getWater() {
        return new int[]{5, 0};
    }
}
