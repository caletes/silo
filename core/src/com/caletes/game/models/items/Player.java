package com.caletes.game.models.items;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.caletes.game.models.spritesheet.PlayerSheet;

public class Player extends Item {

    public Player() {
        PlayerSheet playerSheet = new PlayerSheet();
        addSprite(new Sprite(playerSheet.getTexture("marker")));
        setOrigins(playerSheet.getOriginX(), playerSheet.getOriginY());
    }
}
