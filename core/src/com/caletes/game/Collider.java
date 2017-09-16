package com.caletes.game;

import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.Item;

/**
 * http://thecodersbreakfast.net/index.php?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java
 * https://developer.mozilla.org/en-US/docs/Games/Techniques/3D_collision_detection
 */
public class Collider {
    private Collider() {
    }

    public static Collider getInstance() {
        return ColliderHolder.instance;
    }

    private static class ColliderHolder {
        private final static Collider instance = new Collider();
    }

    public boolean collide(Item i1, Item i2) {
        WorldPosition p1 = i1.getWorldPosition();
        WorldPosition p2 = i2.getWorldPosition();
        return (p1.getX() <= p2.getX() + i2.getWidth() && p1.getX() + i1.getWidth() >= p2.getX()) &&
                (p1.getY() <= p2.getY() + i2.getDepth() && p1.getY() + i1.getDepth() >= p2.getY()) &&
                (p1.getZ() <= p2.getZ() + i2.getHeight() && p1.getZ() + i1.getHeight() >= p2.getZ());
    }
}