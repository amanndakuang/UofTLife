package com.example.uoftlife;

class Score{
    // The score of this Score
    private int score;

    // The difficulty of this Score
    private byte difficulty;

    //The user that create this score
    //TODO: Remember to change the type
    private String user;

    //Score is 0 at first. Do not need to pass in score.
    Score(byte difficulty, String user) {
        this.difficulty = difficulty;
        this.user = user;
    }
    // Add amount to score
    void addScore(int amount){
        score += amount;
    }

    int getScore() {
        return score;
    }
}
