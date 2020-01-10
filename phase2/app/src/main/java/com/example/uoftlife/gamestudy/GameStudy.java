package com.example.uoftlife.gamestudy;

import com.example.uoftlife.data.DataFacade;

class GameStudy {


    private final String ALPHABET = "ABCDEF";
    private final int TOTAL_TIME = 60000;

    private int boardNumber;
    private int lengthOfWord;
    private String word;

    private int char1, char2;
    private int vitalityConsume;
    private int moodConsume;

    private int error = 0;
    private int boardPointer;
    private int inputPointer;


    GameStudy() {
        char1 = DataFacade.getValue("ch1");
        char2 = DataFacade.getValue("ch2");
        initializeDifficulty();
        initializeNewBoard();
    }

    private void initializeDifficulty() {
        if (char1 == 6 || char2 == 6) {
            //study hater
            lengthOfWord = 18;
            boardNumber = 6;
            moodConsume = 40;
            vitalityConsume = 40;
        } else if (char1 == 7 || char2 == 7) {
            // study lover
            lengthOfWord = 10;
            boardNumber = 3;
            moodConsume = -10;
            vitalityConsume = 20;
        } else {
            //default
            lengthOfWord = 12;
            boardNumber = 4;
            moodConsume = 20;
            vitalityConsume = 30;
        }
    }

    void initializeNewBoard() {
        randomGenerateWord();
        if (inputPointer < boardPointer) {
            error += boardPointer - inputPointer;
        }
        boardPointer = 0;
        inputPointer = 0;
    }

    double getCorrectness() {
        double correctness = (1 - ((double) error / (boardNumber * lengthOfWord))) * 100;
        if (correctness < 0) {
            correctness = 0;
        }
        return correctness;
    }

    private void randomGenerateWord() {
        StringBuilder sb = new StringBuilder(lengthOfWord);
        for (int i = 0; i < lengthOfWord; i++) {
            int index = (int) (ALPHABET.length() * Math.random());
            sb.append(ALPHABET.charAt(index));
        }
        word = sb.toString();
    }


    int getEachBoardTime() {
        return TOTAL_TIME / boardNumber;
    }

    int getBoardNumber() {
        return boardNumber;
    }

    int getEachCharTime() {
        return getEachBoardTime() / lengthOfWord;
    }

    char getNextCharOnBoard() {
        if (boardPointer != word.length()) {
            return word.charAt(boardPointer++);
        } else {
            return '\0';
        }
    }

    boolean checkCorrectness(char input) {
        inputPointer++;
        if (inputPointer <= boardPointer) {
            if (word.charAt(inputPointer - 1) == input) {
                return true;
            }
        }
        error++;
        return false;
    }

    int getStudyOutcome() {
        if (char2 == 6 || char1 == 6) {
            return (int) (20 * getCorrectness() / 100);
        } else if (char1 == 7 || char2 == 7) {
            return (int) (30 * getCorrectness() / 100);
        } else {
            return (int) (25 * getCorrectness() / 100);
        }
    }

    int getVitalityConsume() {
        return vitalityConsume;
    }

    int getMoodConsume() {
        return moodConsume;
    }
}
