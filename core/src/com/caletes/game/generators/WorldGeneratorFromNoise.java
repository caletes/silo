package com.caletes.game.generators;

import com.badlogic.gdx.graphics.Pixmap;
import com.caletes.game.Biome;
import com.caletes.game.Elevations;

import java.awt.*;

/**
 * cf. http://www.redblobgames.com/maps/terrain-from-noise/
 * https://cmaher.github.io/posts/working-with-simplex-noise/
 */
public class WorldGeneratorFromNoise {

    private static OpenSimplexNoise simplexNoise1;
    private int width, height;
    private int startX, startY;
    private Elevations elevations;

    private boolean debug;
    double minNoiseDebug = 999;
    double maxNoiseDebug = -999;
    static double absoluteMinDebug = 999;
    static double absoluteMaxDebug = -999;

    public WorldGeneratorFromNoise(int width, int height, long seed) {
        this(width, height, 0, 0, seed);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public WorldGeneratorFromNoise(int width, int height, int startX, int startY, long seed) {
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        simplexNoise1 = new OpenSimplexNoise(seed);
    }

    public Elevations generate() {
        elevations = new Elevations(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double elevation = sumOctave(12, x + startX, y + startY, 0.002, 1, 0.5);
                elevation += sumOctave(1, x + startX, y + startY, 0.001, 1, 0.5);
                elevation /= 2;
                if (debug) {
                    minNoiseDebug = Math.min(minNoiseDebug, elevation);
                    maxNoiseDebug = Math.max(maxNoiseDebug, elevation);
                }
                elevations.pushTo(elevation, x, y);
            }
        }
        if (debug) {
            if (minNoiseDebug < absoluteMinDebug) {
                absoluteMinDebug = minNoiseDebug;
                System.out.println("MIN " + absoluteMinDebug);
            }
            if (maxNoiseDebug > absoluteMaxDebug) {
                absoluteMaxDebug = maxNoiseDebug;
                System.out.println("MAX " + absoluteMaxDebug);
            }
        }
        return elevations;
    }

    private double sumOctave(int octaveCount, double x, double y, double frequency, double amplitude, double persistence) {
        double amplitudesSum = 0;
        double noise = 0;
        //add successively smaller, higher-frequency terms
        for (int i = 0; i < octaveCount; ++i) {
            noise += amplitude * simplexNoise1.eval(x * frequency, y * frequency);
            amplitudesSum += amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }
        //take the average value of the iterations
        noise /= amplitudesSum;
        // noise += 4 * simplexNoise1.eval(x * 0.0005, y * 0.0005);
        // the 2D noise is trapped within ±½√2 ≈ ±0.707
        //normalize the result between 0 and 1
        double min = -0.6;
        double max = 0.6;
        noise = (noise - min) / (max - min);
        return noise;
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
