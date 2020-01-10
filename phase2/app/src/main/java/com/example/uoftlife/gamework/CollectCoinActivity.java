package com.example.uoftlife.gamework;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.uoftlife.GameBaseActivity;
import com.example.uoftlife.R;
import com.example.uoftlife.data.DataFacade;
import com.example.uoftlife.util.TransitionPageBuilder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class represents items in game collectcoin.
 */
public class CollectCoinActivity extends GameBaseActivity {

    /**
     * The bag's location.
     */
    private int bagLocation = 150;

    /**
     * The height of the bag.
     */
    private int bagHeight = 1560;

    /**
     * The last location of the bag.
     */
    private int bagLastLocation;

    /**
     * The max number of items displayed on screen.
     */
    private int maxNumItem = 20;

    /**
     * The player's health in this game.
     */
    private int health;

    /**
     * The score player gets in this game.
     */
    private int score;

    /**
     * The text view represents the score.
     */
    private TextView scoreText;

    /**
     * The image view presents the bag.
     */
    private ImageView bag;

    /**
     * The layout of the game's display.
     */
    private ViewGroup root;

    /**
     * The progress bar representing elapsed time in game.
     */
    private ProgressBar time;

    /**
     * The handler is used to create thread by runnable.
     */
    private Handler handler;

    /**
     * The CollectCoinPresenter class used in the game.
     */
    private CollectCoinPresenter presenter;

    /**
     * The time that the activity was created.
     */
    long lastTime;

    private Timer timer;

    /**
     * The ArrayList contains dropping items.
     */
    private ArrayList<ImageView> dropItems;

    /**
     * The ArrayList contains image view of healths.
     */
    private ArrayList<ImageView> healths;


    /**
     * When activity starts, onCreate is called first.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dropItems = new ArrayList<>();
        healths = new ArrayList<>();
        handler = new Handler();
        score = 0;
        health = 3;
        lastTime = System.currentTimeMillis();
        presenter = new CollectCoinPresenter(this);
        initView();
        initializeDifficulty();
        findViewById(R.id.pause).setVisibility(View.GONE);
    }


    /**
     * Set content layout.
     */
    @Override
    protected int setContentLayout() {
        return R.layout.activity_collect_coin;
    }

    /**
     * Return false when called.
     */
    @Override
    protected boolean setSavable() {
        return false;
    }

    /**
     * React on the touch event of the user who plays the game.
     */
    private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    bagLastLocation = (int) event.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int now = (int) event.getRawX();
                    bagLocation += now - bagLastLocation;
                    bagLastLocation = now;
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
            }
            return true;
        }
    }


    /**
     * Return the bag's location.
     */
    public int getBagLocation() {
        return bagLocation;
    }

    /**
     * .
     */
    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    /**
     * Return the bag's height.
     */
    public int getBagHeight() {
        return bagHeight;
    }

    /**
     * Return the maximum number of items displayed on screen.
     */
    public int getMaxNumItem() {
        return maxNumItem;
    }

    /**
     * Initialize the items in display.
     */
    private void initView() {
        root = findViewById(R.id.root);
        bag = findViewById(R.id.bag);
        root.setOnTouchListener(new ChoiceTouchListener());
        for (int i = 0; i < maxNumItem; i++) {
            ImageView imageView = new ImageView(this);
            dropItems.add(imageView);
            imageView.setImageResource(R.drawable.coin1);
            imageView.setVisibility(View.GONE);
            root.addView(imageView);
        }
        for (int i = 0; i < health; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.heart1);
            imageView.setX(0);
            imageView.setX(650 + 130 * i);
            healths.add(imageView);
            root.addView(imageView);
        }
        bag.setX(bagLastLocation);
        bag.setY(bagHeight);
        time = findViewById(R.id.progressBar);
        time.setX(10);
        time.setY(10);
        scoreText = new TextView(this);
        scoreText.setX(0);
        scoreText.setY(20);
        scoreText.setTextColor(Color.BLACK);
        scoreText.setTextSize(20);
        root.addView(scoreText);
        String scoreDisplay = "Score: ";
        scoreText.setText(scoreDisplay.concat(Integer.toString(score)));
    }

    /**
     * Specifies the activities once the game is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.updateGraph();
                        endGame();
                    }
                });
            }
        }, 0, 10);
    }

    /**
     * Update the positions of items in display.
     */
    public void updateGraph(ArrayList<DropItems> items) {
        updateProgressBar();
        bag.setX(bagLocation);
        int i = 0;
        for (DropItems item : items) {

            ImageView itemView = dropItems.get(i);
            itemView.setX(item.getLocationX());
            itemView.setY(item.getLocationY());

            switch (item.getType()) {
                case "coin":
                    itemView.setImageResource(R.drawable.coin1);
                    break;
                case "bomb":
                    itemView.setImageResource(R.drawable.shit);
                    break;
                case "health":
                    itemView.setImageResource(R.drawable.health);
                    break;
            }
            itemView.setVisibility(View.VISIBLE);
            i++;
        }
        while (i < maxNumItem) {
            dropItems.get(i).setVisibility(View.GONE);
            i++;
        }
    }

    /**
     * Update the progress bar.
     */
    public void updateProgressBar() {
        long curr = System.currentTimeMillis();
        time.setProgress((int) ((curr - lastTime) / 300));
    }

    /**
     * Increase health by 1 and display the change.
     */
    public void increaseHealth() {
        if (health < 3) {
            healths.get(health).setImageResource(R.drawable.heart1);
            health += 1;
        }
    }

    /**
     * Decrease health by 1 and display the change.
     */
    public void decreaseHealth() {
        if (health > 0) {
            healths.get(health - 1).setImageResource(R.drawable.heart2);
            health -= 1;
        }
    }

    /**
     * Increase score by 1 and display the change.
     */
    public void increaseScore() {
        score += 1;
        String scoreDisplay = "Score: ";
        scoreText.setText(scoreDisplay.concat(Integer.toString(score)));
    }

    /**
     * End the game when losing all healths or time's up (30 seconds).
     */
    synchronized public void endGame() {
        long curr = System.currentTimeMillis();
        long pass = curr - lastTime;
        if (health == 0 || pass >= 30000) {
            finish();
            new TransitionPageBuilder(this).setTitle(getString(R.string.gamework))
                    .setDescription(getString(R.string.summary))
                    .setShowingTime(6)
                    .addValueChange("time", -16)
                    .addValueChange("vitality", -30)
                    .addValueChange("mood", -25)
                    .addValueChange("money", score * 10)
                    .start();
        }
    }

    /**
     * Specifies the activities once the game is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Set difficulty level if the player is money-grubber.
     */
    void initializeDifficulty() {

        int char1 = DataFacade.getValue("ch1");
        int char2 = DataFacade.getValue("ch2");

        // Game becomes hard if the character has the characteristic of insomnia.
        if (char1 == 5 || char2 == 5) {
            presenter.setDifficulty();
        }
    }


}
