package com.example.uoftlife.data;

import java.util.HashMap;
import java.util.Map;

public interface GameConstants {

    /**
     * Setting parameters
     */
    int HARD_POINTS = 20;
    int MEDIUM_POINTS = 32;
    int EASY_POINTS = 50;

    /**
     * Map drawer parameters
     */
    int HORIZONTAL_GRIDS = 4;
    int VERTICAL_GRIDS = 6;
    double ROAD_WIDTH = 0.4;
    //GREEN: 0xff009f45, LIGHT_GREY: 0xffe6e6e5
    int GRID_COLOR = 0xffc4c6c3;
    int STREET_COLOR = 0xff343834;
    int RESERVE_HEIGHT = 260;


    /**
     * Max values for the below attributes
     */
    int MAX_GENERAL = 100;
    int TUITION = 50000;


    /* Clarification:
     * Below String keys are the keys to save data, and the keys to read the texts that represents
     * on GUI from R.string file*/
    /**
     * The innate attributes that is assigned/set at the beginning of each turn of game
     */
    String[] INNATE_ATTRIBUTES = {
            "wealth", "stamina", "aspiration", "intellect", "fortune"
    };
    String[] MAP_BUILDINGS = {
            "school", "home", "mall"
    };
    String CONFIG_FILE = "game_config";


    /**
     * These sources key words are added in displaying order
     */
    String[] GAME_STATUS = {
            "money",
            "split line",
            "understanding", "practice", "mark",
            "split line",
            "repletion", "vitality",
            "split line",
            "mood", "health"
    };

    /**
     * The attributes that changes during game
     */
    Map<String, Integer> GAME_STATUS_INIT = new HashMap<String, Integer>() {{
        put("time", 200);
        put("money", 52000);
        put("mark", 100);

        put("understanding", 20);
        put("practice", 60);
        put("vitality", 100);
        put("repletion", 100);
        put("health", 100);
        put("mood", 100);
        put("GPA", 4);
        put("ch1", 0);
        put("ch2", 0);
        put("started", 0);
        put("due", 0);
        put("worth", 0);
        put("takes", 0);
    }};
    String STATUS_FILE = "game_progress";

    String USER_FILE = "user";

    /**
     * Game channel configuration
     */
    String CHANNEL_ID = "gameEvents";
    String CHANNEL_NAME = "Assignment";
}
