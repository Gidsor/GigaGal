package com.gidsor.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gidsor.gigagal.Level;
import com.gidsor.gigagal.entities.Enemy;
import com.gidsor.gigagal.entities.ExitPortal;
import com.gidsor.gigagal.entities.GigaGal;
import com.gidsor.gigagal.entities.Platform;
import com.gidsor.gigagal.entities.Powerup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Comparator;

public class LevelLoader {
    public static final String TAG = LevelLoader.class.getName();

    public static Level load(String path) {
        Level level = new Level();


        FileHandle file = Gdx.files.internal(path);

        JSONParser parser = new JSONParser();
        JSONObject rootJsonObject;

        try {
            rootJsonObject = (JSONObject) parser.parse(file.reader());

            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);

            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);
            loadPlatforms(platforms, level);

            JSONArray nonPlatformObjects = (JSONArray) composite.get(Constants.LEVEL_IMAGES);
            loadNonPlatformEntities(level, nonPlatformObjects);

        } catch (Exception ex) {
            Gdx.app.log(TAG, ex.getMessage());
            Gdx.app.log(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }

        return level;
    }

    private static float safeGetFloat(JSONObject object, String key) {
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }

    private static void loadPlatforms(JSONArray array, Level level) {
        Array<Platform> platforms = new Array<Platform>();

        for (Object object : array) {
            final JSONObject platformObject = (JSONObject) object;

            final float x = safeGetFloat(platformObject, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(platformObject, Constants.LEVEL_Y_KEY);

            final float width = ((Number) platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();
            final float height = ((Number) platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();

            Gdx.app.log(TAG,"Loaded a platform at x = " + x + " y = " + (y + height) + " w = " + width + " h = " + height);

            final Platform platform = new Platform(x, y + height, width, height);
            platforms.add(platform);

            final String identifier = (String) platformObject.get(Constants.LEVEL_IDENTIFIER_KEY);

            if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_TAG)) {
                Gdx.app.log(TAG, "Loaded an enemy on that platform");
                final Enemy enemy = new Enemy(platform);
                level.getEnemies().add(enemy);
            }
        }

        platforms.sort(new Comparator<Platform>() {
            @Override
            public int compare(Platform p1, Platform p2) {
                if (p1.top < p2.top) {
                    return 1;
                } else if (p1.top > p2.top) {
                    return -1;
                }
                return 0;
            }
        });

        level.getPlatforms().addAll(platforms);
    }

    private static void loadNonPlatformEntities(Level level, JSONArray nonPlatformObjects) {
        for (Object o : nonPlatformObjects) {
            JSONObject item = (JSONObject) o;
            Vector2 lowerLeftCorner = new Vector2();

            final float x = safeGetFloat(item, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(item, Constants.LEVEL_Y_KEY);

            lowerLeftCorner = new Vector2(x, y);

            if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.STANDING_RIGHT)) {
                final Vector2 gigaGalPosition = lowerLeftCorner.add(Constants.GIGAGAL_EYE_POSITION);
                Gdx.app.log(TAG, "Loaded Gigagal at " + gigaGalPosition);
                level.setGigaGal(new GigaGal(gigaGalPosition, level));
            } else if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.EXIT_PORTAL_SPRITE_1)) {
                final Vector2 exitPortalPosition = lowerLeftCorner.add(Constants.EXIT_PORTAL_CENTER);
                Gdx.app.log(TAG, "Loaded the exit portal at " + exitPortalPosition);
                level.setExitPortal(new ExitPortal(exitPortalPosition));
            } else if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.POWERUP_SPRITE)) {
                final Vector2 powerupPosition = lowerLeftCorner.add(Constants.POWERUP_CENTER);
                Gdx.app.log(TAG, "Loaded a powerup at " + powerupPosition);
                level.getPowerups().add(new Powerup(powerupPosition));
            }
        }
    }
}
