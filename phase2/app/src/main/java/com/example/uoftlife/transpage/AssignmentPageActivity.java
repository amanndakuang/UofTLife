package com.example.uoftlife.transpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uoftlife.R;
import com.example.uoftlife.data.DataFacade;
import com.example.uoftlife.gamemap.MapActivity;


@SuppressLint("Registered")
public class AssignmentPageActivity extends TransitionPageActivity {
    private int restTime = DataFacade.getValue("time") - DataFacade.getValue("due");
    private boolean isEnough = restTime >= DataFacade.getValue("takes");
    private int markPercentage = calculateMark();
    private boolean isProcrastinator = (DataFacade.getValue("ch1") == 1 || DataFacade.getValue("ch2") == 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateValues();
    }

    private void updateValues() {
        int timePass;
        int loseMark;
        if (isEnough) {
            // procrastinator
            if (isProcrastinator) {
                timePass = restTime;
            } else {
                timePass = DataFacade.getValue("takes");
            }
            loseMark = DataFacade.getValue("worth") * (1 - (markPercentage / 100));
        } else {
            timePass = restTime;
            loseMark = DataFacade.getValue("worth");
        }
        updateValue("mark", -loseMark);
        updateValue("time", -timePass);
        updateValue("mood", -loseMark * 2);
        updateValue("vitality", -timePass * 2);
        updateValue("repletion", -timePass * 2);
        DataFacade.setValue("due", 0);
    }

    @Override
    protected Intent nextActivity() {
        return new Intent(this, MapActivity.class);
    }

    @Override
    protected String setTitleText() {
        return isEnough ? "Completed in time!" : "Failed...";
    }

    private int calculateMark() {
        int diff = ((100 - DataFacade.getValue("practice")) + (100 - DataFacade.getValue("understanding"))) / 2;
        if (diff < 0) {
            diff = 0;
        } else if (diff > 100) {
            diff = 100;
        }
        return 100 - diff;
    }

    @Override
    protected String setDescriptionText() {
        ((TextView) findViewById(R.id.description_text)).setTextSize(16);
        String text = isProcrastinator ? "Although you finally completed it, you cost all the time util due because of procrastination." : "";
        return isEnough ?
                text + "Correspond to performance on this period's study, result is " + markPercentage + "% marks." :
                "Although you spend time on it, the due date is still coming earlier than you think... You lost all the marks that this assignment is worth.";
    }

    @Override
    protected int setShowLength() {
        return 10;
    }

}
