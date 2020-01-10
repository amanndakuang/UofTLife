package com.example.uoftlife.transpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.uoftlife.R;
import com.example.uoftlife.data.DataFacade;
import com.example.uoftlife.data.GameConstants;
import com.example.uoftlife.gamemap.MapActivity;

@SuppressLint("Registered")
public class SemesterStartPageActivity extends TransitionPageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateValue("money", -GameConstants.TUITION);
        DataFacade.addToValue("understanding", DataFacade.getValue("intellect") / 2);
        DataFacade.saveProgress();
    }

    @Override
    protected boolean setSkipable() {
        return false;
    }

    @Override
    protected Intent nextActivity() {
        return new Intent(this, MapActivity.class);
    }

    @Override
    protected String setTitleText() {
        return getString(R.string.semester);
    }

    @Override
    protected String setDescriptionText() {
        return getString(R.string.tuition);
    }

    @Override
    protected int setShowLength() {
        return 6;
    }

}
