package com.example.uoftlife.gamesleep;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uoftlife.GameBaseActivity;
import com.example.uoftlife.R;

@SuppressLint("Registered")
public class GameSleepActivity extends GameBaseActivity {

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
        TextView timeDisplay = findViewById(R.id.count_down);
        Button alarmButton = findViewById(R.id.btnAlarm);
        initializePresenter(alarmButton, timeDisplay);
        setListener(alarmButton);
    }

    private void initializePresenter(Button alarmButton, TextView timeDisplay) {
        gameSleepPresenter = new GameSleepPresenter(this, alarmButton, timeDisplay);
        gameSleepPresenter.setCharacter(new View[]{
                findViewById(R.id.character1),
                findViewById(R.id.character2),
                findViewById(R.id.character3),
                findViewById(R.id.character4),
                findViewById(R.id.characterSit)}
        );
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_game_sleep;
    }

    @Override
    protected boolean setSavable() {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameSleepPresenter.cancelTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSleepPresenter.startTimer();
    }

    private void setListener(View button) {
        button.setOnClickListener((view) -> gameSleepPresenter.updateClick());
    }

}
