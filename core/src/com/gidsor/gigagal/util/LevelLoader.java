package com.gidsor.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gidsor.gigagal.Level;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;

public class LevelLoader {
    public static final String TAG = LevelLoader.class.getName();

    public static Level load(String levelName, Viewport viewport) {
        Level level = new Level(viewport);

        String path = Constants.LEVEL_DIR + File.separator + levelName + "." + Constants.LEVEL_FILE_EXTENSION;

        try {
            FileHandle file = Gdx.files.internal(path);
            JSONParser parser = new JSONParser();
            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());
            Gdx.app.log(TAG, rootJsonObject.keySet().toString());

            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);
            Gdx.app.log(TAG, composite.keySet().toString());

            JSONObject platforms = (JSONObject) composite.get(Constants.LEVEL_9PATCHES);
            JSONObject firstPlatform = (JSONObject) platforms.get(0);
            Gdx.app.log(TAG, firstPlatform.keySet().toString());
        } catch (Exception ex) {
            Gdx.app.error(TAG, ex.getMessage());
            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }
        return level;
    }
}
