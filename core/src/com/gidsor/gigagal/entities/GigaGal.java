package com.gidsor.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gidsor.gigagal.Level;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.Enums.*;
import com.gidsor.gigagal.util.Utils;

public class GigaGal {
    public static final String TAG = GigaGal.class.getName();

    Direction direction;
    JumpState jumpState;
    WalkState walkState;

    Vector2 position;
    Vector2 spawnLocation;
    Vector2 velocity;
    Vector2 lastFramePosition;

    long jumpStartTime;
    long walkStartTime;

    Level level;

    public GigaGal(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();

        init();
    }

    public void init() {
        position.set(spawnLocation);
        lastFramePosition.set(spawnLocation);
        velocity.setZero();
        jumpState = JumpState.FALLING;
        direction = Direction.RIGHT;
        walkState = WalkState.STANDING;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float dt, Array<Platform> platforms) {
        lastFramePosition.set(position);
        velocity.y -= dt * Constants.GRAVITY;
        position.mulAdd(velocity, dt);

        if (position.y < Constants.KILL_PLANE) {
            init();
        }

        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;

            for (Platform platform : platforms) {
                if (landeOnPlatform(platform)) {
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = platform.top + Constants.GIGAGAL_EYE_HEIGHT;
                }
            }
        }

        Rectangle gigaGalBounds = new Rectangle(
                position.x - Constants.GIGAGAL_STANCE_WIDTH / 2,
                position.y - Constants.GIGAGAL_EYE_HEIGHT,
                Constants.GIGAGAL_STANCE_WIDTH,
                Constants.GIGAGAL_HEIGHT
        );

        for (Enemy enemy : level.getEnemies()) {
            Rectangle enemyBounds = new Rectangle(
                enemy.position.x - Constants.ENEMY_COLLISION_RADIUS,
                enemy.position.y - Constants.ENEMY_COLLISION_RADIUS,
                2 * Constants.ENEMY_COLLISION_RADIUS,
                2 * Constants.ENEMY_COLLISION_RADIUS
            );

            if (gigaGalBounds.overlaps(enemyBounds)) {
                if (position.x < enemy.position.x) {
                    recoilFromEnemy(Direction.LEFT);
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                }
            }
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

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft(dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight(dt);
        } else {
            walkState = WalkState.STANDING;
        }
    }

    private void moveLeft(float dt) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }

        walkState = WalkState.WALKING;
        direction = Direction.LEFT;
        position.x -= dt * Constants.GIGAGAL_MOVE_SPEED;
    }

    private void moveRight(float dt) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }

        walkState = WalkState.WALKING;
        direction = Direction.RIGHT;
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

    boolean landeOnPlatform(Platform platform) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.GIGAGAL_EYE_HEIGHT >= platform.top &&
                position.y - Constants.GIGAGAL_EYE_HEIGHT < platform.top) {
            float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);
            straddle = (platform.left > leftFoot && platform.right < rightFoot);
        }
        return leftFootIn || rightFootIn || straddle;
    }

    private void recoilFromEnemy(Direction direction) {
        velocity.y = Constants.KNOCKBACK_VELOCITY.y;

        if (direction == Direction.LEFT) {
            velocity.x = -Constants.KNOCKBACK_VELOCITY.x;
        } else {
            velocity.x = Constants.KNOCKBACK_VELOCITY.x;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.gigaGalAssets.standingRight;

        if (direction == Direction.RIGHT && jumpState != JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingRight;
        } else if (direction == Direction.RIGHT && walkState == WalkState.STANDING) {
            region = Assets.instance.gigaGalAssets.standingRight;
        } else if (direction == Direction.RIGHT && walkState == WalkState.WALKING) {
            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = (TextureRegion) Assets.instance.gigaGalAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        } else if (direction == Direction.LEFT && jumpState != JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingLeft;
        } else if (direction == Direction.LEFT && walkState == WalkState.STANDING) {
            region = Assets.instance.gigaGalAssets.standingLeft;
        } else if (direction == Direction.LEFT && walkState == WalkState.WALKING) {
            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = (TextureRegion) Assets.instance.gigaGalAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }

        Utils.drawTextureRegion(batch, region,
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y);
    }
}
