package com.gidsor.gigagal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gidsor.gigagal.entities.GigaGal;
import com.gidsor.gigagal.entities.Platform;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.Utils;

public class Level {
    private Viewport viewport;
    private GigaGal gigaGal;
    private Array<Platform> platforms;

    public Level(Viewport viewport) {
        this.viewport = viewport;
        initializeDebugLevel();
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
        Utils.drawTextureRegion(batch, Assets.instance.enemyAssets.enemy, new Vector2(100, 100), Constants.ENEMY_CENTER);
        batch.end();
    }

    private void initializeDebugLevel() {
        gigaGal = new GigaGal(new Vector2(14, 40), this);

        platforms = new Array<Platform>();

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

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public GigaGal getGigaGal() {
        return gigaGal;
    }

    public void setGigaGal(GigaGal gigaGal) {
        this.gigaGal = gigaGal;
    }
}
