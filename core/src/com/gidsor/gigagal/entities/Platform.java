package com.gidsor.gigagal.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gidsor.gigagal.util.Assets;

public class Platform {
    float left;
    float top;
    float right;
    float bottom;
    String identifier;

    public Platform(float left, float top, float width, float height) {
        this.left = left;
        this.top = top;
        this.right = left + width;
        this.bottom = top - height;
    }

    public void render(SpriteBatch batch) {
        float width = right - left;
        float height = top - bottom;

        Assets.instance.platformAssets.platformNinePatch.draw(
                batch,
                left - 1,
                bottom - 1,
                width + 2,
                height + 2
        );
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
