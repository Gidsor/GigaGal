package com.gidsor.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gidsor.gigagal.overlays.GigaGalHud;
import com.gidsor.gigagal.overlays.VictoryOverlay;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.ChaseCam;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.LevelLoader;
import com.gidsor.gigagal.util.Utils;

public class GameplayScreen extends ScreenAdapter {
    private static final String TAG = GameplayScreen.class.getName();

    SpriteBatch batch;
    long levelEndOverlayStartTime;
    private ChaseCam chaseCam;
    private Level level;
    private GigaGalHud hud;
    private VictoryOverlay victoryOverlay;

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        chaseCam = new ChaseCam();
        hud = new GigaGalHud();
        victoryOverlay = new VictoryOverlay();

        startNewLevel();
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width, height, true);
        victoryOverlay.viewport.update(width, height, true);
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
        hud.render(batch, level.getGigaGal().getLives(), level.getGigaGal().getAmmo(), level.score);
        renderLevelEndOverlays(batch);
        batch.end();
    }

    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (level.victory) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                victoryOverlay.init();
            }

            victoryOverlay.render(batch);

            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelComplete();
            }
        }
    }

    private void startNewLevel() {
        level = Level.debugLevel();

        chaseCam.camera = level.viewport.getCamera();
        chaseCam.target = level.getGigaGal();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void levelComplete() {
        startNewLevel();
    }
}
