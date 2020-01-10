package com.example.uoftlife.gamesleep;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uoftlife.R;
import com.example.uoftlife.data.DataFacade;
import com.example.uoftlife.util.GameMessenger;
import com.example.uoftlife.util.TransitionPageBuilder;

import java.util.Timer;
import java.util.TimerTask;

class GameSleepPresenter {

    private GameSleepModel gameSleepModel;
    private Activity context;
    private Button alarmButton;
    private TextView timeDisplay;

    private int screenWidth;
    private int screenHeight;

    private Timer timer;
    private int characterState = 0;

    private View[] character;


    /**
     * GameSleepPresenter constructor.
     */
    GameSleepPresenter(Activity context, Button alarmButton, TextView timeDisplay) {
        this.context = context;
        this.alarmButton = alarmButton;
        this.timeDisplay = timeDisplay;
        readScreenSize();
    }

    void setCharacter(View[] character) {
        this.character = character;
        initializeModelDifficulty();
    }

    void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateTimeDisplay();
                if (gameSleepModel.updateTimer()) {
                    endFailing();
                }
                if (gameSleepModel.alarmMove()) {
                    moveButtonRandomly();
                }
            }
        }, 0, 1000);
    }

    void cancelTimer() {
        timer.cancel();
    }

    void updateClick() {
        if (gameSleepModel.updateClick()) {
            characterState++;
            if (characterState == character.length) {
                endPassing();
            } else {
                updateCharacterView();
            }
        }
    }

    private void endPassing() {
        new TransitionPageBuilder(context).setTitle(context.getString(R.string.game_sleep_pass))
                .setDescription(context.getString(R.string.game_sleep_pass_description))
                .setShowingTime(5)
                .addValueChange("time", -16)
                .addValueChange("vitality", +50)
                .addValueChange("repletion", -10)
                .addValueChange("health", +10)
                .addValueChange("mood", 20)
                .start();
        context.finish();
    }

    private void endFailing() {
        new TransitionPageBuilder(context).setTitle(context.getString(R.string.game_sleep_fail))
                .setDescription(context.getString(R.string.game_sleep_fail_description))
                .setShowingTime(5)
                .addValueChange("time", -16 + ((int) (20 * Math.random())))
                .addValueChange("vitality", +60)
                .addValueChange("health", +10)
                .addValueChange("mood", -20)
                .start();
        context.finish();
    }

    private void readScreenSize() {
        Point screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getRealSize(screenSize);
        ViewGroup.LayoutParams alarmParams = alarmButton.getLayoutParams();
        screenWidth = screenSize.x - alarmParams.width;
        screenHeight = screenSize.y - alarmParams.height;
    }

    private void moveButtonRandomly() {
        context.runOnUiThread(() -> {
            alarmButton.animate()
                    .x(((int) (screenWidth * Math.random())))
                    .y(((int) (screenHeight * Math.random())))
                    .setDuration(200)
                    .start();
            GameMessenger.getMessenger().toastMessage(context.getString(R.string.tap));
        });
    }

    /**
     * Sets the difficulty of the game according to the characteristics
     */
    private void initializeModelDifficulty() {
        int char1 = DataFacade.getValue("ch1");
        int char2 = DataFacade.getValue("ch2");
        // Game becomes hard if the character has the characteristic of insomnia.
        if (char1 == 4 || char2 == 4) {
            createModel(12, 70, 1);
        } else {
            // default difficulty
            createModel(16, 45, 2);
        }
    }

    private void createModel(int timeLength, int targetClick, int alarmMoveInterval) {
        this.gameSleepModel = new GameSleepModel(timeLength, targetClick, alarmMoveInterval, character.length);
    }


    /**
     * Updates the countdown timer.
     */
    private void updateTimeDisplay() {
        int timeLeft = gameSleepModel.getTimeLeft();
        String timeLeftText = "00:";
        if (timeLeft < 10) {
            timeLeftText += "0";
        }
        timeLeftText += timeLeft;
        timeDisplay.setText(timeLeftText);
    }


    /**
     * Specifies character's appearance according to the number of clicks entered.
     */
    private void updateCharacterView() {
        character[character.length - 1 - characterState].setVisibility(View.INVISIBLE);
        if (characterState == character.length - 1) {
            character[character.length - 1].setVisibility(View.VISIBLE);
        }
    }
}
