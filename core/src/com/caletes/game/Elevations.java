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

    public double getMinAround(int x, int y) {
        double inf = 9999;
        double north = (y > 0) ? get(x, y - 1) : inf;
        double east = (x < width - 1) ? get(x + 1, y) : inf;
        double south = (y < height - 1) ? get(x, y + 1) : inf;
        double west = (x > 0) ? get(x - 1, y) : inf;
        return Math.min(Math.min(north, east), Math.min(south, west));
    }
}
