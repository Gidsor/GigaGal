package com.gidsor.gigagal.overlays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gidsor.gigagal.entities.GigaGal;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.Utils;

public class OnScreenControls {
    public static final String TAG = OnScreenControls.class.getName();

    public final Viewport viewport;
    public GigaGal gigaGal;
    private Vector2 moveLeftCenter = new Vector2();
    private Vector2 moveRightCenter = new Vector2();
    private Vector2 shootCenter = new Vector2();
    private Vector2 jumpCenter = new Vector2();

    public OnScreenControls() {
        this.viewport = new ExtendViewport(
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE);

        recalculateButtonPositions();
    }

    public void render(SpriteBatch batch) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlsAssets.moveLeft,
                moveLeftCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlsAssets.moveRight,
                moveRightCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlsAssets.shoot,
                shootCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlsAssets.jump,
                jumpCenter,
                Constants.BUTTON_CENTER
        );

    }

    public void recalculateButtonPositions() {
        moveLeftCenter.set(Constants.BUTTON_RADIUS * 3 / 4, Constants.BUTTON_RADIUS);
        moveRightCenter.set(Constants.BUTTON_RADIUS * 2, Constants.BUTTON_RADIUS * 3 / 4);


        shootCenter.set(
                viewport.getWorldWidth() - Constants.BUTTON_RADIUS * 2f,
                Constants.BUTTON_RADIUS * 3 / 4
        );

        jumpCenter.set(
                viewport.getWorldWidth() - Constants.BUTTON_RADIUS * 3 / 4,
                Constants.BUTTON_RADIUS
        );

    }
}