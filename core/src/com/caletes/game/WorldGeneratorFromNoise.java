package com.caletes.game;

import com.badlogic.gdx.graphics.Pixmap;

import java.awt.*;

/**
 * cf. http://www.redblobgames.com/maps/terrain-from-noise/
 */
public class WorldGeneratorFromNoise {

    private static OpenSimplexNoise simplexNoise1;
    private int width, height;
    private boolean island;
    private Elevations elevations;

    public WorldGeneratorFromNoise(int width, int height, long seed, boolean island) {
        this.width = width;
        this.height = height;
        this.island = island;
        simplexNoise1 = new OpenSimplexNoise(seed);
        generateElevations();
    }

    public Elevations getElevations() {
        return elevations;
    }

    private void generateElevations() {
        elevations = new Elevations(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double nx = x / (double) width - 0.5, ny = y / (double) height - 0.5;
                double elevation = 0;
                elevation += heightNoise(nx, ny, 4, 1);
                elevation += heightNoise(nx, ny, 8, 0.5);
                elevation += heightNoise(nx, ny, 16, 0.25);
                elevation += heightNoise(nx, ny, 32, 0.12);
                elevation += heightNoise(nx, ny, 64, 0.06);
                elevation += heightNoise(nx, ny, 128, 0.03);
                elevation /= 1 + 0.5 + 0.25 + 0.12 + 0.06 + 0.03;

                elevation = redistribute(elevation);
                if (island) {
                    elevation = toIsland(elevation, nx, ny);
                }
                elevations.pushTo(elevation, x, y);
            }
        }
        elevations = elevations.normalize();
    }


    private double toIsland(double elevation, double nx, double ny) {
        double distance = getManhattanDistance(nx, ny);
        double a = 0.05;
        double b = 2;
        double c = 1;
        return (elevation + a) * (1 - b * Math.pow(distance, c));
    }

    private double getManhattanDistance(double nx, double ny) {
        return Math.max(Math.abs(nx), Math.abs(ny));
    }

    private double getEuclideanDistance(double nx, double ny) {
        return Math.sqrt(nx * nx + ny * ny);
    }

    private double redistribute(double elevation) {
        return Math.pow(elevation / 2.0 + 0.5, 2.4);
    }

    private double heightNoise(double nx, double ny, int frequency, double amplitude) {
        return amplitude * simplexNoise1.eval(nx * frequency, ny * frequency);
    }

    public Pixmap toHeightMap() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte greyscale = toGrayScale(elevations.get(x, y));
                pixmap.getPixels().put(i++, greyscale);
                pixmap.getPixels().put(i++, greyscale);
                pixmap.getPixels().put(i++, greyscale);
            }
        }
        return pixmap;
    }

    private byte toGrayScale(double elevation) {
        return (byte) (elevation * 255);
    }

    public Pixmap toBiomeMap() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = Biome.find(elevations.get(x, y)).getColor();
                pixmap.getPixels().put(i++, (byte) color.getRed());
                pixmap.getPixels().put(i++, (byte) color.getGreen());
                pixmap.getPixels().put(i++, (byte) color.getBlue());
            }
        }
        return pixmap;
    }
}
