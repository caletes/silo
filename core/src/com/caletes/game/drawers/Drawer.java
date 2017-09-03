package com.caletes.game.drawers;

import com.caletes.game.models.Chunk;

public interface Drawer {
    void draw(Chunk chunk, int x, int y, int z);
}
