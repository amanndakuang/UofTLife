package com.example.uoftlife.gamacard;

import android.widget.ImageView;

import com.example.uoftlife.data.DataFacade;

import java.util.List;

class GameCard {
    private List<Card> cardArray;
    private ImageView[] listOfImageView;
    private int numCardOver;
    private int firstCard, secondCard;
    private int backImage;
    private int score;
    private int numberOfFlipTime;
    private int char1, char2;
    private int vitalityConsume;

    int getVitalityConsume() {
        return vitalityConsume;
    }

    GameCard(List<Card> cards, ImageView[] listOfImageView, int backImage) {
        this.cardArray = cards;
        this.listOfImageView = listOfImageView;
        for (int i = 0; i < this.listOfImageView.length; i++) {
            this.listOfImageView[i].setTag(i);
        }
        numCardOver = 0;
        firstCard = 0;
        secondCard = 0;
        this.backImage = backImage;
        score = 0;
        numberOfFlipTime = 0;
        char1 = DataFacade.getValue("ch1");
        char2 = DataFacade.getValue("ch2");
    }

    ImageView[] getListOfImageView() {
        return listOfImageView;
    }

    /**
     * This method check whether the two cards matches
     */
    void checkResult() {
        numberOfFlipTime++;
        if (!(cardArray.get(firstCard).equals(cardArray.get(secondCard)))) {
            cardArray.get(firstCard).setCovered(true);
            cardArray.get(secondCard).setCovered(true);
        } else {
            updateScore(numberOfFlipTime);
            numberOfFlipTime = 0;
        }
        for (int i = 0; i < cardArray.size(); i++) {
            if (cardArray.get(i).isCovered()) {
                listOfImageView[i].setImageResource(backImage);
                listOfImageView[i].setEnabled(true);
            }
        }
        numCardOver = 0;
    }

    /**
     *
     * @return the current score of the player
     */
    int getScore() {
        return score;
    }

    /**
     *
     * @return how many cards are turned over
     */
    int getNumCardOver() {
        return numCardOver;
    }

    /**
     * @param imageView  The imageView that has been clicked
     * @param cardNumber The index of the card that has been click
     * This method will flip card that the player has clicked.
     */
    void flipCard(ImageView imageView, int cardNumber) {
        imageView.setImageResource(cardArray.get(cardNumber).getValue());
        cardArray.get(cardNumber).setCovered(false);
        if (numCardOver == 0) {
            firstCard = cardNumber;
            numCardOver++;
            imageView.setEnabled(false);
        } else {
            secondCard = cardNumber;
            numCardOver++;
            for (ImageView image : listOfImageView) {
                image.setEnabled(false);
            }
        }
    }

    /**
     *
     * @param numberOfFlipTime
     * update the score based on the characteristic of the player
     */
    private void updateScore(int numberOfFlipTime) {
        if (char1 == 6 || char2 == 6) {
            score += 5 + Math.floor(20 / numberOfFlipTime);
            vitalityConsume = 10;
        }else if (char1 == 7 || char2 == 7){
            score += 10 + Math.floor(30 / numberOfFlipTime);
            vitalityConsume = 5;
        }else{
            score += 7 + Math.floor(25 / numberOfFlipTime);
            vitalityConsume = 7;
        }
    }
    /**
     * return true if the game finished
     */
    boolean checkEnd() {
        boolean end = true;
        for (Card card: cardArray){
            if (card.isCovered()){
                end = false;
            }
        }
        return end;
    }
}