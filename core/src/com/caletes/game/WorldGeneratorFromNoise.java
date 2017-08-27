package com.caletes.game;

import com.badlogic.gdx.graphics.Pixmap;

import java.awt.*;
import java.util.Random;

/**
 * cf. http://www.redblobgames.com/maps/terrain-from-noise/
 */
public class WorldGeneratorFromNoise {

    private static OpenSimplexNoise simplexNoise1;
    private int width, height;
    private boolean island;
    private double[][] elevations;
    public double minE = 1;
    public double maxE = -1;


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
                elevations[x][y] = elevation;
                System.out.println(elevation);
                minE = Math.min(elevation, minE);
                maxE = Math.max(elevation, maxE);
            }
        }

        System.out.println(minE);
        System.out.println(maxE);
        //normalize();
    }

    private void normalize() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                elevations[x][y] = (elevations[x][y] - minE)/(maxE-minE);
                System.out.println(elevations[x][y]);
            }
        }
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
        return Math.pow(elevation, 2.4);
    }

    private double heightNoise(double nx, double ny, int frequency, double amplitude) {
        double noise = simplexNoise1.eval(nx * frequency, ny * frequency);
        noise = noise / 2.0 + 0.6465;
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

    public double[][] getElevations() {
        return elevations;
    }
}
