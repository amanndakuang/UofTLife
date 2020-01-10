package com.example.uoftlife.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.uoftlife.GameBaseActivity;
import com.example.uoftlife.R;
import com.example.uoftlife.gamemap.MapActivity;
import com.example.uoftlife.util.TransitionPageBuilder;

import java.util.Iterator;

public class ShoppingMallActivity extends GameBaseActivity {

    private Iterator itemIterator = new AllItem().iterator();
    private int index;
    private ViewGroup[] lays;
    private ViewGroup root;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRandomButton1(itemIndex);

//        setBreadButton();
//        setCoffeeButton(100, 100);
//        setLipstickButton();
//        setBubbleTeaButton();
//        setBookButton();
        setBackButton();
        lays = new ViewGroup[]{
                findViewById(R.id.lay1),
                findViewById(R.id.lay2),
                findViewById(R.id.lay3)
        };
        root = findViewById(R.id.root);
        setAllButton();
        setNextPageButton();
    }

    //exit shop and return to layout MapActivity
    protected int setContentLayout() {
        return R.layout.activity_purchase_item;
    }

    protected void setAllButton() {
        for (int i = 0; i < 5; ) {
            i += setRandomButton();
        }
    }

    private int setRandomButton() {
        if (itemIterator.hasNext()) {
            Button button;
            String next = (String) itemIterator.next();
            if (next.equals("book")) {
                button = setBookButton();
            } else if (next.equals("bread")) {
                button = setBreadButton();
            } else if (next.equals("bubbleTea")) {
                button = setBubbleTeaButton();
            } else if (next.equals("coffee")) {
                button = setCoffeeButton();
            } else {
                button = setLipstickButton();
            }
            if (button.getParent() == root) {
                ((ViewGroup) findViewById(R.id.root)).removeView(button);
                lays[(int) (Math.random() * lays.length)].addView(button);
                return 1;
            } else {
                return 0;
            }
        }
        return 1;
    }


    protected void setNextPageButton() {
        Button nextPage = findViewById(R.id.nextPage);
        nextPage.setOnClickListener(v -> {
            Intent i = new Intent(ShoppingMallActivity.this, ShoppingMallActivity.class);
            finish();
            overridePendingTransition(0, 0);
            startActivity(i);
            overridePendingTransition(0, 0);
        });
    }

    //exit shop and return to layout MapActivity
    protected void setBackButton() {
        Button back = findViewById(R.id.back_to_map);
        back.setOnClickListener((view) ->
                startActivity(new Intent(this, MapActivity.class)));
    }

    @Override
    protected boolean setSavable() {
        return true;
    }

    private Button setBreadButton() {
        Button bread = findViewById(R.id.bread);
        bread.setVisibility(View.VISIBLE);
        bread.setOnClickListener((view) -> {
            bread.setVisibility(View.INVISIBLE);
            new TransitionPageBuilder(this).setTitle("bread")
                    .setDescription("item bread, costs you 5 dollars and helps you to gain 20 repletion")
                    .setShowingTime(5)
                    .addValueChange("money", -5)
                    .addValueChange("repletion", 20)
                    .start();
        });
        return bread;
    }

    private Button setCoffeeButton() {
        Button coffee = findViewById(R.id.coffee);
        coffee.setVisibility(View.VISIBLE);
        coffee.setOnClickListener((view) -> {
            coffee.setVisibility(View.INVISIBLE);
            new TransitionPageBuilder(this).setTitle("coffee")
                    .setDescription("item coffee, costs 3 dollars and helps you to gain 10 vitality")
                    .setShowingTime(5)
                    .addValueChange("money", -3)
                    .addValueChange("vitality", 10)
                    .start();
        });
        return coffee;
    }

    private Button setBubbleTeaButton() {
        Button bubbleTea = findViewById(R.id.bubbleTea);
        bubbleTea.setVisibility(View.VISIBLE);
        bubbleTea.setOnClickListener((view) -> {
            bubbleTea.setVisibility(View.INVISIBLE);
            new TransitionPageBuilder(this).setTitle("bubbleTea")
                    .setDescription("item bubble tea, costs 8 dollars and helps you to gain 10 happiness")
                    .setShowingTime(5)
                    .addValueChange("money", -8)
                    .addValueChange("mood", 10)
                    .start();
        });
        return bubbleTea;
    }

    private Button setBookButton() {
        Button book = findViewById(R.id.book);
        book.setVisibility(View.VISIBLE);
        book.setOnClickListener((view) -> {
            book.setVisibility(View.INVISIBLE);
            new TransitionPageBuilder(this).setTitle("book")
                    .setDescription("item book, costs you 100, helps you to gain 5 knowledge but loses 20 happiness")
                    .setShowingTime(5)
                    .addValueChange("money", -100)
                    .addValueChange("mood", -20)
                    .addValueChange("understanding", 5)
                    .start();
        });
        return book;
    }

    private Button setLipstickButton() {
        Button lipstick = findViewById(R.id.lipstick);
        lipstick.setVisibility(View.VISIBLE);
        lipstick.setOnClickListener((view) -> {
            lipstick.setVisibility(View.INVISIBLE);
            new TransitionPageBuilder(this).setTitle("lipstick")
                    .setDescription("item lipstick, costs 50 dollars and gains 15 happiness")
                    .setShowingTime(5)
                    .addValueChange("money", -50)
                    .addValueChange("mood", 15)
                    .start();
        });
        return lipstick;
    }


}