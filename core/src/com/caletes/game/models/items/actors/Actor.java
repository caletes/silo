package com.caletes.game.models.items.actors;

import com.badlogic.gdx.Gdx;
import com.caletes.game.models.items.Item;

abstract class Actor extends Item {

    protected float velocity = 50.0f;

    protected Actor(String image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

    public float getVelocity() {
        return velocity;
    }

    public void moveToLeft() {
        sprite.setX(sprite.getX() - Gdx.graphics.getDeltaTime() * velocity);
    }

    public void moveToRight() {
        sprite.setX(sprite.getX() + Gdx.graphics.getDeltaTime() * velocity);
    }

    public void moveToUp() {
        sprite.setY(sprite.getY() + Gdx.graphics.getDeltaTime() * velocity);
    }

    public void moveToDown() {
        sprite.setY(sprite.getY() - Gdx.graphics.getDeltaTime() * velocity);
    }
}
