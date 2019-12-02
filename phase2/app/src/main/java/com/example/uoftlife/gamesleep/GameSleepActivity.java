package com.example.uoftlife.gamesleep;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uoftlife.GameBaseActivity;
import com.example.uoftlife.R;
import com.example.uoftlife.util.TransitionPageBuilder;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("Registered")
public class GameSleepActivity extends GameBaseActivity implements GameSleepView {

    /**
     * The countdown timer of the game level.
     */
    private CountDownTimer timer;

    /**
     * The alarm button of the game level.
     */
    Button alarmButton;

    /**
     * The text view of the timer.
     */
    TextView timerText;


    /**
     * The GameSleepPresenter instance passed into the game level.
     */
    private GameSleepPresenter gameSleepPresenter;


    /**
     * Specifies the activities once the game is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSleepModel gameSleepModel = createGameSleepModel();
        gameSleepPresenter = new GameSleepPresenter(gameSleepModel, this);
        gameSleepPresenter.initializeDifficulty();
        setAlarmBtn();
        setInitialCharacter();
        setTimer();

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_game_sleep;
    }

    @Override
    protected boolean setSavable() {
        return false;
    }

    /**
     * Specifies the activities once the game is started.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Specifies the activities once the game is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameSleepPresenter.pauseTimer();
        gameSleepPresenter.cancelTimer();

    }

    /**
     * Specifies the activities once the game is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startTimer(gameSleepPresenter.getTimeLeftInMilliseconds());
        gameSleepPresenter.setTimer(timer);
    }

    /**
     * Specifies the activities once the game is restarted.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        gameSleepPresenter.setTiming(true);
    }

    /**
     * Specifies the activities once the game is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameSleepPresenter.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK;
    }

    /**
     * Creates a new GameSleepModel.
     */
    GameSleepModel createGameSleepModel() {
        final DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return new GameSleepModel((displaymetrics.widthPixels * 0.7f),
                displaymetrics.heightPixels * 0.7f);
    }

    /**
     * Sets the character's initial appearance in the game level.
     */
    @Override
    public void setInitialCharacter() {
        findViewById(R.id.characterSit).setVisibility(View.INVISIBLE);
        findViewById(R.id.character1).setVisibility(View.VISIBLE);
        findViewById(R.id.character2).setVisibility(View.VISIBLE);
        findViewById(R.id.character3).setVisibility(View.VISIBLE);
        findViewById(R.id.character4).setVisibility(View.VISIBLE);
    }


    /**
     * Sets the functions of the alarm button.
     */
    @Override
    public void setAlarmBtn() {
        alarmButton = findViewById(R.id.btnAlarm);

        final Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    gameSleepPresenter.handleAlarmAnimation();
                });
            }
        }, 4000, gameSleepPresenter.getAlarmChangePositionInterval());

        alarmButton.setOnClickListener((view) -> {
            gameSleepPresenter.addClickAmount();
            gameSleepPresenter.makeToast();
            handleCharacter();
        });
    }

    /**
     * Shows the timer text to record the time left in the game.
     */
    @Override
    public void setTimerText(String timeLeftText) {
        timerText.setText(timeLeftText);

    }

    /**
     * Sets the countdown timer.
     */
    @Override
    public void setTimer() {
        timerText = findViewById(R.id.levelOneCountDown);

    }

    /**
     * Starts the countdown timer.
     */
    @Override
    public void startTimer(long timeLeft) {

        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                gameSleepPresenter.setTimeLeftInMilliseconds(l);
                gameSleepPresenter.updateTimer();
            }

            @Override
            public void onFinish() {
                handleRedirection();
                finish();

            }

        }.start();

        gameSleepPresenter.setTiming(true);
    }

    /**
     * Shows the alarm and assigns its location on screen.
     */
    @Override
    public void showAlarmAnimation(float windowWidth, float windowHeight) {
        alarmButton.animate()
                .x(windowWidth)
                .y(windowHeight)
                .setDuration(0)
                .start();

    }

    /**
     * Shows the toast instruction.
     */
    @Override
    public void makeToast() {
        Toast.makeText(getApplicationContext(), getString(R.string.tap),
                Toast.LENGTH_LONG)
                .show();
    }

    /**
     * Handles the appearance of the character.
     */
    @Override
    public void handleCharacter() {
        int d = gameSleepPresenter.handleCharacter();
        switch (d) {
            // if the click amount reaches 1/4 of the target click, hide the first layer of character
            case 1:
                findViewById(R.id.character4).setVisibility(View.INVISIBLE);
                break;
            // if the click amount reaches 1/2 of the target click, hide the second layer of character
            case 2:
                findViewById(R.id.character3).setVisibility(View.INVISIBLE);
                break;
            // if the click amount reaches 3/4 of the target click, hide the third layer of character
            case 3:
                findViewById(R.id.character2).setVisibility(View.INVISIBLE);
                break;

            // if reaches the target click, hide the fourth layer and show the final layer
            case 4:
                findViewById(R.id.character1).setVisibility(View.INVISIBLE);
                findViewById(R.id.characterSit).setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Transfer to the outcome page.
     */
    void handleRedirection() {
        if (gameSleepPresenter.isPassed()) {
            new TransitionPageBuilder(this).setTitle(getString(R.string.game_sleep_pass))
                    .setDescription(getString(R.string.game_sleep_pass_description))
                    .setShowingTime(5)
                    .addValueChange("time", -12)
                    .addValueChange("vitality", +50)
                    .addValueChange("health", +20)

                    .start();
        } else {
            new TransitionPageBuilder(this).setTitle(getString(R.string.game_sleep_fail))
                    .setDescription(getString(R.string.game_sleep_fail_description))
                    .setShowingTime(5)
                    .addValueChange("time", -20)
                    .addValueChange("vitality", +50)
                    .addValueChange("health", +20)

                    .start();
        }

    }

    /**
     * Shows the outcome of the game level after hiding the elements from display
     */
    @Override
    public void showOutcome() {
        //hide the button and timer when time is up.
        findViewById(R.id.btnAlarm).setVisibility(View.GONE);
        findViewById(R.id.levelOneCountDown).setVisibility(View.GONE);
        System.out.println("SCORE: " + gameSleepPresenter.getScore() + "/100");

    }


}
