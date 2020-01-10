package com.example.uoftlife.transpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uoftlife.GameBaseActivity;
import com.example.uoftlife.R;
import com.example.uoftlife.data.DataFacade;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TransitionPageActivity extends GameBaseActivity {

    private LayoutInflater inflater;
    private Timer timer;

    private int autoDestroyCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoDestroyCount = setShowLength();
        inflater = LayoutInflater.from(this);
        ((TextView) findViewById(R.id.title_text)).setText(setTitleText());
        ((TextView) findViewById(R.id.description_text)).setText(setDescriptionText());
    }


    @Override
    protected int setContentLayout() {
        return R.layout.activity_transition_page;
    }

    @Override
    protected boolean setSavable() {
        return true;
    }

    @Override
    protected boolean setSkipable() {
        return true;
    }

    protected abstract Intent nextActivity();

    private void startTask() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                autoDestroyCount--;
                if (autoDestroyCount == 0) {
                    if (nextActivity() != null) {
                        startActivity(nextActivity());
                    }
                    finish();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        startTask();
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    abstract protected String setTitleText();

    abstract protected String setDescriptionText();

    abstract protected int setShowLength();

    protected void updateValue(String key, int value) {
        View line = inflater.inflate(R.layout.attribute_line, findViewById(R.id.page_base), false);
        String signedValue = value >= 0 ? "+" + value : "" + value;
        if (DataFacade.addToValue(key, value)) {
            ((TextView) line.findViewById(R.id.attribute_name)).setText(key);
            ((TextView) line.findViewById(R.id.attribute_value)).setText(signedValue);
        }
        ((ViewGroup) findViewById(R.id.page_base)).addView(line);
    }

}
