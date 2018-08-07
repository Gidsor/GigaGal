package com.gidsor.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;

public class GigaGal {
    public static final String TAG = GigaGal.class.getName();

    Vector2 position;
    Facing facing;
    Vector2 velocity;
    JumpState jumpState;
    long jumpStartTime;

    public GigaGal() {
        position = new Vector2(20, Constants.GIGAGAL_EYE_HEIGHT);
        facing = Facing.RIGHT;

        velocity = new Vector2();
        jumpState = JumpState.FALLING;
    }

    public void update(float dt) {
        velocity.y -= dt * Constants.GRAVITY;
        position.mulAdd(velocity, dt);

        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }

        if (position.y - Constants.GIGAGAL_EYE_HEIGHT < 0) {
            jumpState = JumpState.GROUNDED;
            position.y = Constants.GIGAGAL_EYE_HEIGHT;
            velocity.y = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            switch (jumpState) {
                case GROUNDED: startJump(); break;
                case JUMPING: continueJump(); break;
                case FALLING: break;
            }
        } else {
            endJump();
        }

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

    private void startJump() {
        jumpState = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
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

    enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED
    }
}
