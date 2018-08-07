package com.gidsor.gigagal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gidsor.gigagal.entities.GigaGal;

public class Level {
    GigaGal gigaGal;

    public Level() {
        gigaGal = new GigaGal();
    }

    public void update(float dt) {
        gigaGal.update(dt);
    }

    public void render(SpriteBatch sb) {
        gigaGal.render(sb);
    }
}
