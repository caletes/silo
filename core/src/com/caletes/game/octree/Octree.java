package com.caletes.game.octree;


import java.util.ArrayList;
import java.util.List;

//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Octree {

    private List<Object> objects = new ArrayList<>();
    private float exponent;
    private Octree[] children;


    public Octree(float size) {
        exponent = computeExponent(size);
    }

    private float computeExponent(float size) {
        return (float) (Math.log(size) / Math.log(2));
    }

    public float getExponent() {
        return exponent;
    }

    private void split(){

    }

    private Octree getChild(float x, float y, float z) {
        return null;
    }
}
