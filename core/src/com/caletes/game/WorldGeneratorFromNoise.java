package com.caletes.game;

import com.badlogic.gdx.graphics.Pixmap;

import java.awt.*;
import java.util.Random;

/**
 * cf. http://www.redblobgames.com/maps/terrain-from-noise/
 */
public class WorldGeneratorFromNoise {

    public static OpenSimplexNoise simplexNoise1;
    public int width, height;
    public boolean island;
    public double[][] elevations;

    public WorldGeneratorFromNoise(int width, int height, boolean island) {
        this.width = width;
        this.height = height;
        this.island = island;
        Random random = new Random();
        simplexNoise1 = new OpenSimplexNoise(random.nextLong());
        generateElevations();
    }

    private void generateElevations() {
        elevations = new double[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double nx = x / (double) width - 0.5, ny = y / (double) height - 0.5;
                //double nx = x / (double) width, ny = y / (double) height;
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
                System.out.println(elevation);
                elevations[x][y] = elevation;
            }
        }
    }

    private double toIsland(double elevation, double nx, double ny) {
        double distance = getManhattanDistance(nx, ny);
        double a = 0.05;
        double b = 4;
        double c = 2;
        return (elevation + a) * (1 - b * Math.pow(distance, c));
    }

    private double getManhattanDistance(double nx, double ny) {
        return Math.max(Math.abs(nx), Math.abs(ny));
    }

    private double getEuclideanDistance(double nx, double ny) {
        return Math.sqrt(nx * nx + ny * ny);
    }

    private double redistribute(double elevation) {
        return Math.pow(elevation, 2.4);
    }

    private double heightNoise(double nx, double ny, int frequency, double amplitude) {
        double noise = simplexNoise1.eval(nx * frequency, ny * frequency);
        // Rescale from -1.0:+1.0 to 0.0:1.0
        noise = noise / 2.0 + 0.5;
        return amplitude * noise;
    }

    public Pixmap toHeightMap() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte greyscale = toGrayScale(elevations[x][y]);
                pixmap.getPixels().put(i++, greyscale);
                pixmap.getPixels().put(i++, greyscale);
                pixmap.getPixels().put(i++, greyscale);
            }
        }
        return pixmap;
    }

    public Pixmap toBiomeMap() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double elevation = elevations[x][y];
                Color color = Biome.find(elevation).getColor();
                pixmap.getPixels().put(i++, (byte) color.getRed());
                pixmap.getPixels().put(i++, (byte) color.getGreen());
                pixmap.getPixels().put(i++, (byte) color.getBlue());
            }
        }
        return pixmap;
    }

    private byte toGrayScale(double elevation) {
        return (byte) (elevation * 255);
    }
}
