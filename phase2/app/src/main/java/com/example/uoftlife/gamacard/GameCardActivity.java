package com.example.uoftlife.gamacard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uoftlife.GameBaseActivity;
import com.example.uoftlife.R;
import com.example.uoftlife.util.TransitionPageBuilder;

import java.util.ArrayList;
import java.util.Collections;

public class GameCardActivity extends GameBaseActivity {

    private GameCard cardGame;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize all the images
        int backImage = R.drawable.question;
        Card card1 = new Card(R.drawable.cow1);
        Card card2 = new Card(R.drawable.cow2);
        Card card3 = new Card(R.drawable.cow3);
        Card card4 = new Card(R.drawable.cow4);
        Card card5 = new Card(R.drawable.cow5);
        Card card6 = new Card(R.drawable.cow6);

        //Initialize score textView
        score = findViewById(R.id.score);

        //Initialize the cardArray and randomly shuffle it
        Card[] cards = {card1, card2, card3, card4, card5, card6};
        ArrayList<Card> cardArray = new ArrayList<>();
        for (Card card : cards) {
            cardArray.add(card);
            cardArray.add(card);
        }
        Collections.shuffle(cardArray);

        //Initialize all the imageViews
        ImageView[] listOfImageView = new ImageView[12];
        listOfImageView[0] = findViewById(R.id.pic1);
        listOfImageView[1] = findViewById(R.id.pic2);
        listOfImageView[2] = findViewById(R.id.pic3);
        listOfImageView[3] = findViewById(R.id.pic4);
        listOfImageView[4] = findViewById(R.id.pic5);
        listOfImageView[5] = findViewById(R.id.pic6);
        listOfImageView[6] = findViewById(R.id.pic7);
        listOfImageView[7] = findViewById(R.id.pic8);
        listOfImageView[8] = findViewById(R.id.pic9);
        listOfImageView[9] = findViewById(R.id.pic10);
        listOfImageView[10] = findViewById(R.id.pic11);
        listOfImageView[11] = findViewById(R.id.pic12);

        //Initialize the game
        cardGame = new GameCard(cardArray, listOfImageView, backImage);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_card_game_acticity;
    }

    @Override
    protected boolean setSavable() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        setViews();
        score.setText("Score: 0");
    }

    /**
     * set up all the Image Views
     */
    private void setViews() {
        for (ImageView imageView : cardGame.getListOfImageView()) {
            imageView.setOnClickListener((view) -> {
                int cardNumber = (int) imageView.getTag();
                cardGame.flipCard(imageView, cardNumber);
                if (cardGame.getNumCardOver() == 2) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        cardGame.checkResult();
                        score.setText("Score: " + cardGame.getScore());
                        if (cardGame.checkEnd()) {
                            endGame();
                        }
                    }, 1000);
                }
            });
        }
    }

    private void endGame() {
        finish();
        new TransitionPageBuilder(this).setTitle("Congratulations!!")
                .setDescription("You just finished your study!!")
                .setShowingTime(4)
                .addValueChange("practice", (int) Math.floor(cardGame.getScore() / 10))
                .addValueChange("understanding", (int) Math.floor(cardGame.getScore() / 20))
                .addValueChange("time", -12)
                .addValueChange("mood", -25)
                .addValueChange("vitality", -cardGame.getVitalityConsume())
                .start();
    }


}
