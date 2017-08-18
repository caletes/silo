package com.caletes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class HeightMap {

    private static Pixmap pixmap;
    private int width;
    private int height;
    private final int whiteHeight;
    private int[][] mapHeights;

    public HeightMap(String internalPath, int whiteHeight) {
        this(Gdx.files.internal(internalPath), whiteHeight);
    }

    public HeightMap(FileHandle file, int whiteHeight) {
        pixmap = new Pixmap(file);
        if (pixmap.getFormat() != Pixmap.Format.RGB888) {

        }
        width = pixmap.getWidth();
        height = pixmap.getHeight();
        mapHeights = new int[width][height];
        this.whiteHeight = whiteHeight;
        process();
    }

    public int[][] process() {
        Color color = new Color();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixmap.getPixel(x, y);
                Color.rgba8888ToColor(color, pixel);
                float intensity = (color.r + color.g + color.b) / 3;
                mapHeights[x][y] = (int) (intensity * whiteHeight);
            }
        }
        return mapHeights;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getElevation(int x, int y) {
        return mapHeights[x][y];
    }
}
