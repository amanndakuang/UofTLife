package com.example.uoftlife.data;

import java.util.ArrayList;
import java.util.List;

public enum Characteristics {
    NONE(0, "NONE", "No characteristics"),
    PCS(1, "Procrastinator", "Has lower efficiency when due date is far from now."),
    OPT(2, "Optimist", "Will not suicide when mood become to 0."),
    UST(3, "Unstable Mood", "Easy to lose temper."),
    INS(4, "Insomnia", "Have to sleep longer and is harder to get up."),
    MNY(5, "Money-grubber", "Get more money when working. Every time spend money, rapidly reduce mood."),
    STH(6, "Study-hater", "When studying, easier get tired and frustrated."),
    STL(7, "Study-lover", "When studying, vitality and mood reduced more slowly.");

    private String name;
    private int index;
    private String description;

    Characteristics(int index, String name, String description) {
        this.name = name;
        this.index = index;
        this.description = description;
    }

    public String getTextDisplay() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIndex() {
        return index;
    }

    public static Characteristics findByIndex(int index) {
        for (Characteristics ch : Characteristics.values()) {
            if (ch.index == index) {
                return ch;
            }
        }
        return null;
    }


    private static List<Characteristics> generateValidChar() {
        List<Characteristics> validChar = new ArrayList<>();

        int wealth = DataFacade.getValue("wealth");
        int stamina = DataFacade.getValue("stamina");
        int aspiration = DataFacade.getValue("aspiration");
        int intellect = DataFacade.getValue("intellect");
        int fortune = DataFacade.getValue("fortune");

        if (wealth <= 5 || wealth >= 15) {
            validChar.add(MNY);
        }
        if (stamina <= 10) {
            validChar.add(PCS);
            validChar.add(INS);
            validChar.add(STH);
        }
        if (aspiration <= 10) {
            validChar.add(PCS);
            validChar.add(INS);
            validChar.add(UST);
            validChar.add(STH);
        }
        if (intellect >= 10 && aspiration >= 10) {
            validChar.add(STL);
            validChar.add(OPT);
        }
        if (fortune >= 10) {
            validChar.add(OPT);
        }
        return validChar;
    }

    public static Characteristics randomize() {
        List<Characteristics> validChar = generateValidChar();
        int randomIndex = (int) (validChar.size() * Math.random());
        return validChar.get(randomIndex);
    }
}
