package com.example.uoftlife.gamesleep;

class GameSleepModel {
    /**
     * The target number of click to pass the game level.
     */
    private int clickCounter;
    private int stateChangeInterval;

    /**
     * The time left in this game level in milliseconds.
     */
    private int timeLeft;

    /**
     * The time interval for the button to change position.
     */
    private int alarmMoveInterval;
    private int alarmMoveCounter;


    GameSleepModel(int gameTime, int clickTarget, int alarmMoveInterval, int statesNumber) {
        this.timeLeft = gameTime;
        this.clickCounter = clickTarget;
        this.alarmMoveInterval = alarmMoveInterval;
        this.alarmMoveCounter = alarmMoveInterval;
        this.stateChangeInterval = clickCounter / statesNumber;
    }

    /**
     * Update the time field and check if the game is finished.
     *
     * @return if time's up, return true. Else return false.
     */
    boolean updateTimer() {
        if (timeLeft == 0) {
            return true;
        }
        timeLeft--;
        return false;
    }

    /**
     * Update the click field and check if the game is finished.
     *
     * @return if each stage of click target is reached, return true. Else return false.
     */
    boolean updateClick() {
        clickCounter--;
        if (clickCounter % stateChangeInterval == 0) {
            return true;
        }
        return false;
    }

    boolean alarmMove() {
        alarmMoveCounter--;
        if (alarmMoveCounter == 0) {
            alarmMoveCounter = alarmMoveInterval;
            return true;
        }
        return false;
    }

    int getTimeLeft() {
        return timeLeft;
    }


}
