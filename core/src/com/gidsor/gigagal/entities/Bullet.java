package com.gidsor.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gidsor.gigagal.Level;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.Enums.*;
import com.gidsor.gigagal.util.Utils;

public class Bullet {
    private final Direction direction;
    private final Level level;

    public boolean active;
    private Vector2 position;

    public Bullet(Level level, Vector2 position, Direction direction) {
        this.level = level;
        this.position = position;
        this.direction = direction;
        active = true;
    }

    public void update(float delta) {
        switch (direction) {
            case RIGHT:
                position.x += delta * Constants.BULLET_MOVE_SPEED;
                break;
            case LEFT:
                position.x -= delta * Constants.BULLET_MOVE_SPEED;
                break;
        }

        final float worldWidth = level.getViewport().getWorldWidth();
        final float cameraX = level.getViewport().getCamera().position.x;

        if (position.x < cameraX - worldWidth / 2 || position.x > cameraX + worldWidth / 2) {
            active = false;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.bulletAssets.bullet;
        Utils.drawTextureRegion(batch, region, position, Constants.BULLET_CENTER);
    }
}
