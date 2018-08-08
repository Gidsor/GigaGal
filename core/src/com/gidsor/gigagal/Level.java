package com.gidsor.gigagal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gidsor.gigagal.entities.GigaGal;
import com.gidsor.gigagal.entities.Platform;

public class Level {
    GigaGal gigaGal;
    Array<Platform> platforms;

    public Level() {
        gigaGal = new GigaGal(new Vector2(15, 40));
        platforms = new Array<Platform>();
        addDebugPlatforms();
    }

    public void update(float delta) {
        gigaGal.update(delta, platforms);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (Platform platform : platforms) {
            platform.render(batch);
        }
        gigaGal.render(batch);
        batch.end();
    }

    private void addDebugPlatforms() {
        platforms.add(new Platform(15, 100, 30, 20));
        platforms.add(new Platform(75, 90, 100, 65));
        platforms.add(new Platform(35, 55, 50, 20));
        platforms.add(new Platform(10, 20, 20, 9));
        platforms.add(new Platform(100, 110, 30, 9));
        platforms.add(new Platform(200, 130, 30, 40));
        platforms.add(new Platform(150, 150, 30, 9));
        platforms.add(new Platform(150, 180, 30, 9));
        platforms.add(new Platform(200, 200, 9, 9));
        platforms.add(new Platform(280, 100, 30, 9));
    }
}
