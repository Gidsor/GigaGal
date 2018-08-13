package com.gidsor.gigagal.overlays;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gidsor.gigagal.util.Constants;

public class GigaGalHud {
    public final Viewport viewport;
    final BitmapFont font;

    public GigaGalHud() {
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        font.draw(
                batch,
                "Test record",
                viewport.getWorldWidth() / 2,
                viewport.getWorldHeight() / 4,
                0,
                Align.center,
                false
        );
    }
}
