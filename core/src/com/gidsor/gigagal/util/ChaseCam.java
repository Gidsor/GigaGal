package com.gidsor.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.gidsor.gigagal.entities.GigaGal;

import java.security.Key;

public class ChaseCam {
    public Camera camera;
    public GigaGal target;

    private boolean following;

    public ChaseCam() {
        following = true;
    }

    public void update(float dt) {
//        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
//            following = !following;
//        }

        if (following) {
            camera.position.x = target.getPosition().x;
            camera.position.y = target.getPosition().y;
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                camera.position.x -= dt * Constants.CHASE_CAM_MOVE_SPEED;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                camera.position.x += dt * Constants.CHASE_CAM_MOVE_SPEED;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                camera.position.y += dt * Constants.CHASE_CAM_MOVE_SPEED;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                camera.position.y -= dt * Constants.CHASE_CAM_MOVE_SPEED;
            }
        }
    }
}
