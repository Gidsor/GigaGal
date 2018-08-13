package com.gidsor.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gidsor.gigagal.overlays.GigaGalHud;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.ChaseCam;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.LevelLoader;

public class GameplayScreen extends ScreenAdapter {
    private static final String TAG = GameplayScreen.class.getName();

    private ChaseCam chaseCam;
    private Level level;

    SpriteBatch batch;
//    ExtendViewport gameplayViewport;

    private GigaGalHud hud;

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        chaseCam = new ChaseCam();
        hud = new GigaGalHud();

        startNewLevel();
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width, height, true);
        level.viewport.update(width, height, true);
        chaseCam.camera = level.viewport.getCamera();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render(float delta) {
        level.update(delta);

        chaseCam.update(delta);

        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a
        );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        level.render(batch);
        hud.render(batch);
        batch.end();
    }

    private void startNewLevel() {
        level = Level.debugLevel();

        chaseCam.camera = level.viewport.getCamera();
        chaseCam.target = level.getGigaGal();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
