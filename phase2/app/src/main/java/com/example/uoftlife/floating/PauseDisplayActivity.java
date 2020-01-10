package com.example.uoftlife.floating;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.uoftlife.MainActivity;
import com.example.uoftlife.R;
import com.example.uoftlife.data.DataFacade;
import com.example.uoftlife.data.GameConstants;
import com.example.uoftlife.transpage.AssignmentPageActivity;
import com.example.uoftlife.util.GameMessenger;

public class PauseDisplayActivity extends FloatingActivity {

    private LayoutInflater inflater;
    private int saveClick = 0;
    private int exitClick = 0;
    private final String TIME_KEY = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inflater = LayoutInflater.from(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_pause_display;
    }

    @Override
    protected String setTitle() {
        return getString(R.string.pause);
    }

    @Override
    protected void initializeView() {
        setSaveButton();
        setExitButton();
        getStatusDisplay();
        setTimeBar();
    }

    private void setTimeBar() {
        addAttributeLine(TIME_KEY);
        ProgressBar timeBar = findViewById(R.id.time_bar);
        timeBar.setIndeterminate(false);
        Integer max = GameConstants.GAME_STATUS_INIT.get(TIME_KEY);
        if (max != null) {
            timeBar.setMax(max);
        } else {
            throw new NullPointerException();
        }
        timeBar.setMin(0);
        timeBar.setProgress(DataFacade.getValue(TIME_KEY), true);
    }

    private void setStatus(String statusText) {
        DataFacade.setTempData("status", statusText);
    }

    private void getStatusDisplay() {
        TextView statusText = findViewById(R.id.status);
        String status = getString(R.string.status_default);
        if (DataFacade.getValue("mood") <= 20) {
            status = getString(R.string.status_bad_mood);
        } else if (DataFacade.getValue("mood") >= 90) {
            status = getString(R.string.status_good_mood);
        }
        if (DataFacade.getValue("vitality") <= 20) {
            status = getString(R.string.status_exhausted);
        }
        if (DataFacade.getValue("repletion") <= 20) {
            status = getString(R.string.status_starving);
        }
        if (DataFacade.getValue("health") <= 40) {
            status = getString(R.string.status_sick);
        }
        if (DataFacade.getValue("due") != 0) {
            status = getString(R.string.status_has_due);
            statusText.setOnClickListener((view) -> {
                finish();
                PauseDisplayActivity.this.startActivity(new Intent(PauseDisplayActivity.this, AssignmentPageActivity.class));
            });
        }
        String display = String.format(status, DataFacade.getTempData("name"));
        statusText.setText(display);
    }

    @Override
    protected void dynamicCreateView() {
        for (String s : GameConstants.GAME_STATUS) {
            addAttributeLine(s);
        }
    }

    private void addAttributeLine(String key) {
        View line = inflater.inflate(R.layout.attribute_line, getContentBaseLayout(), false);
        int value = DataFacade.getValue(key);
        if (value != -1) {
            ((TextView) line.findViewById(R.id.attribute_name)).setText(key);
            ((TextView) line.findViewById(R.id.attribute_value)).setText(String.valueOf(DataFacade.getValue(key)));
        }
        getContentBaseLayout().addView(line);
    }


    private void setSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener((view) -> {
            String text;
            if (getIntent().getBooleanExtra("savable", false)) {
                if (saveClick == 0) {
                    text = getString(R.string.save_alert);
                    saveClick++;
                } else {
                    if (DataFacade.saveProgress()) {
                        text = getString(R.string.save_success);
                    } else {
                        text = getString(R.string.save_error);
                    }
                }
            } else {
                text = getString(R.string.save_disable);
            }
            GameMessenger.getMessenger().toastMessage(text);
        });
    }

    private void setExitButton() {
        findViewById(R.id.exitButton).setOnClickListener((view) -> {
            if (exitClick == 0) {
                GameMessenger.getMessenger().toastMessage(getString(R.string.quit_alert));
                exitClick++;
            } else {
                startActivity(new Intent(this, MainActivity.class));
                // because launch mode of main activity is single task,
                // so there is no need to finish this activity manually
            }
        });
    }


}
