package com.caletes.game;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class Elevations {

    private int width, height;
    private double[][] elevations;

    public Elevations(int width, int height) {
        this.width = width;
        this.height = height;
        elevations = new double[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double get(int x, int y) {
        return elevations[x][y];
    }

    public void pushTo(double elevation, int x, int y) {
        elevations[x][y] = elevation;
    }

    /**
     * Normalize elevations between 0 and 1
     *
     * @return
     */
    public Elevations normalize() {
        Elevations normalized = new Elevations(width, height);
        DoubleStream maxStream = Arrays.stream(elevations).flatMapToDouble(Arrays::stream);
        double max = maxStream.max().getAsDouble();
        DoubleStream minStream = Arrays.stream(elevations).flatMapToDouble(Arrays::stream);
        double min = minStream.min().getAsDouble();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                normalized.pushTo(normalize(elevations[x][y], min, max), x, y);
            }
        }
        return normalized;
    }

    /**
     * Normalize a value between 0 and 1
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    private double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public double getMinAround(int x, int y) {
        double inf = 9999;
        double north = (y > 0) ? get(x, y - 1) : inf;
        double east = (x < width - 1) ? get(x + 1, y) : inf;
        double south = (y < height - 1) ? get(x, y + 1) : inf;
        double west = (x > 0) ? get(x - 1, y) : inf;
        return Math.min(Math.min(north, east), Math.min(south, west));
    }
}
