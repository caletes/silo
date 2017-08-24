package com.caletes.game;

import com.badlogic.gdx.graphics.Pixmap;

import java.util.Random;

/**
 * cf. http://www.redblobgames.com/maps/terrain-from-noise/
 */
public class WorldGeneratorFromNoise {

    public static OpenSimplexNoise simplexNoise1;
    public int width, height;
    public double[][] elevations;

    public WorldGeneratorFromNoise(int width, int height, int... frequencies) {
        this.width = width;
        this.height = height;
        Random random = new Random();
        simplexNoise1 = new OpenSimplexNoise(random.nextLong());
        generateElevations();
    }

    private void generateElevations() {
        elevations = new double[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double nx = x / (double) width, ny = y / (double) height;
                double elevation = 0;

                elevation += heightNoise(nx, ny, 4, 1);
                elevation += heightNoise(nx, ny, 8, 0.5);
                elevation += heightNoise(nx, ny, 16, 0.25);
                elevation += heightNoise(nx, ny, 32, 0.125);
                elevation /= 1+0.5+0.25+0.125;

                elevation = redistribute(elevation);
                elevations[x][y] = elevation;
            }
        }
    }

    private double redistribute(double elevation) {
        return Math.pow(elevation, 2.5);
    }

    private double heightNoise(double nx, double ny, int frequency, double amplitude) {
        // Rescale from -1.0:+1.0 to 0.0:1.0
        return amplitude * (simplexNoise1.eval(nx * frequency, ny * frequency) / 2.0 + 0.5);
    }

    public Pixmap toPixmap() {
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

    private byte toGrayScale(double elevation) {
        return (byte) (elevation * 255);
    }
}
