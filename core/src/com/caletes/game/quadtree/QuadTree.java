package com.caletes.game.quadtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
//https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
//https://gist.github.com/chrislo27/81c56d24199a13821bd2

public class QuadTree {
    private static final int MAX_OBJECTS = 3;

    private List<Positionable2D> objects = new ArrayList<>();
    private QuadTree[] children;

    private BoundingRectangle boundingRectangle;

    public QuadTree() {

    }

    public void insert(Positionable2D toInsert) {
        objects.add(toInsert);
        if (isFull() || !isLeaf()) {
            if (isLeaf()) {
                split();
            }
            computeBoudingRectangle();
            for (Positionable2D object : objects) {
                children[getIndex(object)].insert(object);
            }
            objects.clear();
        }
    }

    private boolean isFull() {
        return objects.size() > MAX_OBJECTS;
    }

    private boolean isLeaf() {
        return children == null;
    }

    private void split() {
        children = new QuadTree[4];
        children[0] = new QuadTree();
        children[1] = new QuadTree();
        children[2] = new QuadTree();
        children[3] = new QuadTree();
    }

    private int getIndex(Positionable2D object) {
        boolean top = object.getY() < boundingRectangle.getY() + boundingRectangle.getHeight() / 2;
        boolean left = object.getX() < boundingRectangle.getX() + boundingRectangle.getWidth() / 2;
        if (top && left)
            return 0;
        if (top && !left)
            return 1;
        if (!top && left)
            return 2;
        return 3;
    }

    private void computeBoudingRectangle() {
        //TODO Optimiser en faisant des cmp static ?
        Comparator<Positionable2D> xCmp = Comparator.comparing(Positionable2D::getX);
        Comparator<Positionable2D> yCmp = Comparator.comparing(Positionable2D::getY);
        float minX = objects.stream().min(xCmp).get().getX();
        float maxX = objects.stream().max(xCmp).get().getX();
        float minY = objects.stream().min(yCmp).get().getY();
        float maxY = objects.stream().max(yCmp).get().getY();
        float width = maxX - minX;
        float height = maxY - minY;
        boundingRectangle = new BoundingRectangle(minX, minY, width, height);
    }

    public Positionable2D find(float x, float y) {
        Positionable2D found = null;
        return found;
    }
}
