package com.example.uoftlife.gamestudy;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.uoftlife.GameBaseActivity;
import com.example.uoftlife.R;
import com.example.uoftlife.util.TransitionPageBuilder;

import java.util.Timer;
import java.util.TimerTask;

public class GameStudyActivity extends GameBaseActivity {

    private GameStudy gameStudy;
    private long timeLeft;
    private int currentBoard = 1;

    private CountDownTimer boardRefreshTimer;
    private Timer charUpdateTimer;

    private TextView timerDisplay;
    private TextView boardText;
    private TextView inputText;


    //    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameStudy = new GameStudy();
        timerDisplay = findViewById(R.id.time_left);
        inputText = findViewById(R.id.note);
        boardText = findViewById(R.id.blackboard_content);
        setButtonsListener();
        startNewBoard();
    }

    private class InputReader implements View.OnClickListener {
        char input;

        private InputReader(char input) {
            this.input = input;
        }

        @Override
        public void onClick(View v) {
            String formatPrefix = "";
            String formatSuffix = "";
            if (!gameStudy.checkCorrectness(input)) {
                formatPrefix = "<font color = '#AA0000'><big>";
                formatSuffix = "</big></font>";
            }
            inputText.append(Html.fromHtml(formatPrefix + input + formatSuffix, Html.FROM_HTML_MODE_LEGACY));
        }
    }

    private void setButtonsListener() {
        findViewById(R.id.a).setOnClickListener(new InputReader('A'));
        findViewById(R.id.b).setOnClickListener(new InputReader('B'));
        findViewById(R.id.c).setOnClickListener(new InputReader('C'));
        findViewById(R.id.d).setOnClickListener(new InputReader('D'));
        findViewById(R.id.e).setOnClickListener(new InputReader('E'));
        findViewById(R.id.f).setOnClickListener(new InputReader('F'));
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_game_study;
    }

    @Override
    protected boolean setSavable() {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        boardRefreshTimer.cancel();
        charUpdateTimer.cancel();
    }

    private void startCharTimer() {
        charUpdateTimer = new Timer();
        charUpdateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->
                        boardText.append(String.valueOf(gameStudy.getNextCharOnBoard())));
            }
        }, 0, gameStudy.getEachCharTime());
    }

    private void startBoardTimer() {
        boardRefreshTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long time) {
                timeLeft = time;
                updateTimerDisplay();
            }

            @Override
            public void onFinish() {
                if (currentBoard == gameStudy.getBoardNumber()) {
                    gameEnd();
                } else {
                    currentBoard++;
                    startNewBoard();
                    startBoardTimer();
                }
            }
        }.start();
    }


    private void gameEnd() {
        finish();
        gameStudy.initializeNewBoard();
        new TransitionPageBuilder(GameStudyActivity.this).setTitle("Course is over")
                .setDescription(String.format(getString(R.string.note_cor), gameStudy.getCorrectness()))
                .setShowingTime(8)
                .addValueChange("practice", gameStudy.getStudyOutcome())
                .addValueChange("understanding", ((int) (gameStudy.getStudyOutcome() * 0.4)))
                .addValueChange("time", -12)
                .addValueChange("mood", -gameStudy.getMoodConsume())
                .addValueChange("vitality", -gameStudy.getVitalityConsume())
                .start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBoardTimer();
        startCharTimer();
    }

    private void startNewBoard() {
        gameStudy.initializeNewBoard();
        inputText.setText("");
        boardText.setText("");
        timeLeft = gameStudy.getEachBoardTime();
    }

    private void updateTimerDisplay() {
        timerDisplay.setText(String.valueOf(timeLeft / 1000));
    }


}
