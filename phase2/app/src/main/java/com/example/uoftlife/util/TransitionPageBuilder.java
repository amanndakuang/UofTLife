package com.example.uoftlife.util;

import android.content.Context;
import android.content.Intent;

import com.example.uoftlife.transpage.CommonPageActivity;

import java.util.ArrayList;

public class TransitionPageBuilder {
    private Intent toPage;
    private Context context;
    private ArrayList<String> keyList = new ArrayList<>();
    private ArrayList<Integer> valueList = new ArrayList<>();

    public TransitionPageBuilder(Context context) {
        this.context = context;
        toPage = new Intent(context, CommonPageActivity.class);
    }

    public TransitionPageBuilder setTitle(String title) {
        toPage.putExtra("title", title);
        return this;
    }

    public TransitionPageBuilder setDescription(String description) {
        toPage.putExtra("description", description);
        return this;
    }

    public TransitionPageBuilder setShowingTime(int length) {
        toPage.putExtra("length", length);
        return this;
    }

    public TransitionPageBuilder addValueChange(String key, int value) {
        keyList.add(key);
        valueList.add(value);
        return this;
    }

    public void start() {
        toPage.putExtra("keys", keyList);
        toPage.putExtra("values", valueList);
        context.startActivity(toPage);
    }

}
