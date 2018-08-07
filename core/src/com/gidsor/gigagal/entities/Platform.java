package com.gidsor.gigagal.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Platform {
    float left;
    float top;
    float right;
    float bottom;

    public Platform(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void render(ShapeRenderer renderer) {
        float width = right - left;
        float height = top - bottom;

        renderer.setColor(Color.BLUE);
        renderer.rect(left, bottom, width, height);
    }
}
