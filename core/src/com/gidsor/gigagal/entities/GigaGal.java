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
        position.x -= dt * Constants.GIGAGAL_MOVE_SPEED;
    }

    private void moveRight(float dt) {
        position.x += dt * Constants.GIGAGAL_MOVE_SPEED;
    }

    public void render(SpriteBatch sb) {
        TextureRegion region = Assets.instance.gigaGalAssets.standingRight;

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
}
