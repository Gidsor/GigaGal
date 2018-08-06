package com.gidsor.gigagal.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assests implements Disposable, AssetErrorListener {

    public static final String TAG = Assests.class.getName();
    public static final Assests instance = new Assests();

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    @Override
    public void dispose() {

    }

    public class GigaGalAssests {
        public final AtlasRegion standingRight;

        public GigaGalAssests(TextureAtlas atlas) {
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
        }
    }
}
