package com.gidsor.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.Utils;


public class Powerup {

    final public Vector2 position;

    public Powerup(Vector2 position) {
        this.position = position;
    }

    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.powerupAssets.powerup;
        Utils.drawTextureRegion(batch, region, position, Constants.POWERUP_CENTER);
    }

}
