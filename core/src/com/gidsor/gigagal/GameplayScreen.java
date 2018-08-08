package com.gidsor.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.ChaseCam;
import com.gidsor.gigagal.util.Constants;

public class GameplayScreen extends ScreenAdapter {
    private static final String TAG = GameplayScreen.class.getName();

    private ChaseCam chaseCam;

    Level level;
    SpriteBatch batch;
    ExtendViewport viewport;

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        level = new Level(viewport);
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        chaseCam = new ChaseCam(viewport.getCamera(), level.getGigaGal());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render(float delta) {
        level.update(delta);

        chaseCam.update(delta);

        viewport.apply();

        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a
        );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        level.render(batch);
    }
}
