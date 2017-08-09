package com.caletes.game.models.items.actors;

import com.badlogic.gdx.math.Vector2;

public class Player extends Actor {
    public Player() {
        super("characters.png", 194, 5, 29, 42);
    }

    @Override
    public Vector2 getOrigin() {
        return new Vector2(21,60);
    }

    @Override
    public float getVelocity() {
        return 100;
    }
}
