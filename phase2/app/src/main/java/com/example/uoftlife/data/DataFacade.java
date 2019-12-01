package com.example.uoftlife.data;

import android.content.Context;

public class DataFacade {
    // this is a tool with no fields and no need to instantiate

    /**
     * Private constructor that avoids being instantiated
     */
    private DataFacade() {
    }

    static public boolean addToValue(String key, int value) {
        int i = getValue(key);
        if (i != -1) {
            return setValue(key, i + value);
        } else {
            return false;
        }
    }

    static public boolean setValue(String key, int value) {
        try {
            GameProgress.getProgress().setValue(key, value);
        } catch (IllegalArgumentException e) {
            try {
                GameConfiguration.configure().setValue(key, value);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    static public int getValue(String key) {
        int v = GameProgress.getProgress().getValue(key);
        if (v == -1) {
            v = GameConfiguration.configure().getValue(key);
        }
        return v;
    }

    static private boolean save(GameData data) {
        try {
            return data.save(getContext());
        } catch (NullPointerException e) {
            return false;
        }
    }

    static private void load(GameData data) {
        try {
            data.load(getContext());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    static public void setTempData(String key, Object value) {
        GameProgress.getProgress().setTempData(key,value);
    }

    static public Object getTempData(String key){
        return GameProgress.getProgress().getTempData(key);
    }

    static public boolean saveConfig() {
        return save(GameConfiguration.configure());
    }

    static public boolean saveProgress() {
        return save(GameProgress.getProgress());
    }

    static public void loadConfig() {
        load(GameConfiguration.configure());
    }

    static public void loadProgress() {
        load(GameProgress.getProgress());
    }

    static public void initialize() {
        GameConfiguration.configure().initialize();
        GameProgress.getProgress().initialize();
    }

    static public void setContext(Context context) {
        GameConfiguration.configure().setAppContext(context);
    }

    static public Context getContext() {
        return GameConfiguration.configure().getAppContext();
    }

}
