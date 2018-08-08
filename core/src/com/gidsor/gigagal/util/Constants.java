package com.gidsor.gigagal.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final float WORLD_SIZE = 192;
    public static final String TEXTURE_ATLAS = "images/gigagal.pack.atlas";

    public static final String STANDING_RIGHT = "standing-right";
    public static final String STANDING_LEFT = "standing-left";

    public static final String JUMPING_RIGHT = "jumping-right";
    public static final String JUMPING_LEFT = "jumping-left";

    public static final String WALKING_RIGHT_1 = "walk-1-right";
    public static final String WALKING_RIGHT_2 = "walk-2-right";
    public static final String WALKING_RIGHT_3 = "walk-3-right";
    public static final String WALKING_LEFT_1 = "walk-1-left";
    public static final String WALKING_LEFT_2 = "walk-2-left";
    public static final String WALKING_LEFT_3 = "walk-3-left";
    public static final String PLATFORM_SPRITE = "platform";

    public static final Vector2 GIGAGAL_EYE_POSITION = new Vector2(16, 24);
    public static final float GIGAGAL_EYE_HEIGHT = 16.0f;
    public static final float GIGAGAL_STANCE_WIDTH = 21.0f;

    public static final float GIGAGAL_MOVE_SPEED = 64.0f;
    public static final float WALK_LOOP_DURATION = 0.25f;
    public static final float JUMP_SPEED = 250.0f;
    public static final float MAX_JUMP_DURATION = 0.15f;
    public static final float GRAVITY = 1000;

    public static final int PLATFORM_EDGE = 8;
}
