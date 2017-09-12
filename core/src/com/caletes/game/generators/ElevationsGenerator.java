package com.caletes.game.generators;

import com.badlogic.gdx.graphics.Pixmap;
import com.caletes.game.Biome;
import com.caletes.game.Elevations;

import java.awt.*;
import java.util.function.Function;

/**
 * cf. http://www.redblobgames.com/maps/terrain-from-noise/
 * https://cmaher.github.io/posts/working-with-simplex-noise/
 */
public class ElevationsGenerator {

    private static OpenSimplexNoise simplexNoise1;
    private int size;
    private int startX, startY;
    private Elevations elevations;

    private boolean debug;
    double minNoiseDebug = 999;
    double maxNoiseDebug = -999;
    static double absoluteMinDebug = 999;
    static double absoluteMaxDebug = -999;

    public ElevationsGenerator(int size, long seed) {
        this(0, 0, size, seed);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public ElevationsGenerator(int startX, int startY, int size, long seed) {
        this.size = size;
        this.startX = startX;
        this.startY = startY;
        simplexNoise1 = new OpenSimplexNoise(seed);
    }

    public Elevations generate() {
        elevations = new Elevations(size);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                double elevation = getElevation(x + (int) startX, y + (int) startY);
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

    public double getElevation(int x, int y) {
        // amplitudeCoeff :
        // -2 red noise
        // -1 pink noise 0
        // 0 white noise
        // 1 blue noise
        // 2 violet noise
        double elevation = sumOctave(12, x, y, 0.002, f -> 1 / f);
        elevation += sumOctave(1, x, y, 0.0015, f -> 1 / f / f);
        elevation /= 2;
        return elevation;
    }

    private double sumOctave(int octaveCount, double x, double y, double frequency, Function<Double, Double> amplitudeFunction) {
        double amplitudesSum = 0;
        double noise = 0;
        //add successively smaller, higher-frequency terms
        for (int i = 0; i < octaveCount; ++i) {
            double amplitude = amplitudeFunction.apply(frequency);
            noise += amplitude * simplexNoise1.eval(x * frequency, y * frequency);
            amplitudesSum += amplitude;
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
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
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
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Color color = Biome.find(elevations.get(x, y)).getColor();
                pixmap.getPixels().put(i++, (byte) color.getRed());
                pixmap.getPixels().put(i++, (byte) color.getGreen());
                pixmap.getPixels().put(i++, (byte) color.getBlue());
            }
        }
        return pixmap;
    }

    public double getMinAround(int x, int y) {
        double north = getNorthElevations(x, y);
        double east = getEastElevations(x, y);
        double south = getSouthElevations(x, y);
        double west = getWestElevations(x, y);
        return Math.min(Math.min(north, east), Math.min(south, west));
    }

    public double getNorthElevations(int x, int y) {
        return (y - startY > 0) ? elevations.get(x - startX, y - startY - 1) : getElevation(x, y - 1);
    }

    public double getEastElevations(int x, int y) {
        return (x - startX < size - 1) ? elevations.get(x - startX + 1, y - startY) : getElevation(x + 1, y);
    }

    public double getSouthElevations(int x, int y) {
        return (y - startY < size - 1) ? elevations.get(x - startX, y - startY + 1) : getElevation(x, y + 1);
    }

    public double getWestElevations(int x, int y) {
        return (x - startX > 0) ? elevations.get(x - startX - 1, y - startY) : getElevation(x - 1, y);
    }
}
