package com.caletes.game.models.items.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.caletes.game.models.items.Item;

abstract class Actor extends Item {

    protected Actor(String image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

    public void moveToLeft() {
        setSpritePosition(new Vector2(sprite.getX() - Gdx.graphics.getDeltaTime() * getVelocity(), sprite.getY()));
    }

    public void moveToRight() {
        setSpritePosition(new Vector2(sprite.getX() + Gdx.graphics.getDeltaTime() * getVelocity(), sprite.getY()));
    }

    public void moveToUp() {
        setSpritePosition(new Vector2(sprite.getX(), sprite.getY() + Gdx.graphics.getDeltaTime() * getVelocity()));
    }

    public void moveToDown() {
        setSpritePosition(new Vector2(sprite.getX(), sprite.getY() - Gdx.graphics.getDeltaTime() * getVelocity()));
    }

    public abstract float getVelocity();
}
