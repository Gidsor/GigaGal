package com.gidsor.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;

public class GigaGal {
    public static final String TAG = GigaGal.class.getName();

    Vector2 position;
    Facing facing = Facing.RIGHT;

    public GigaGal() {
        position = new Vector2(20, Constants.GIGAGAL_EYE_HEIGHT);
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft(dt);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight(dt);
        }
    }

    private void moveLeft(float dt) {
        facing = Facing.LEFT;
        position.x -= dt * Constants.GIGAGAL_MOVE_SPEED;
    }

    private void moveRight(float dt) {
        facing = Facing.RIGHT;
        position.x += dt * Constants.GIGAGAL_MOVE_SPEED;
    }

    public void render(SpriteBatch sb) {
        TextureRegion region;

        if (facing == Facing.RIGHT) {
            region = Assets.instance.gigaGalAssets.standingRight;
        } else {
            region = Assets.instance.gigaGalAssets.standingLeft;
        }

        sb.draw(
                region.getTexture(),
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    enum Facing {
        LEFT,
        RIGHT
    }
}
