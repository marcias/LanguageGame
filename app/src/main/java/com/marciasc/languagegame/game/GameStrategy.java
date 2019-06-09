package com.marciasc.languagegame.game;

import com.marciasc.languagegame.WordTranslation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameStrategy {
    public static final int MAXIMUM_MATCHES = 10;

    private Random mRandom = new Random();

    public List<WordTranslation> generateListOfWords(List<WordTranslation> list) {
        List<WordTranslation> wordList = new ArrayList<>();
        WordTranslation wordTranslation;
        int counter = 0;
        while (counter < MAXIMUM_MATCHES) {
            if (getTruePairs()) {
                int index = getRandomIndex(list.size());
                wordTranslation = list.get(index);
                wordTranslation.setmRealPair(true);
            } else {
                int indexWord = getRandomIndex(list.size());
                int indexFalseTranslation = getRandomIndex(list.size());
                wordTranslation = new WordTranslation();
                wordTranslation.setmWord(list.get(indexWord).getmWord());
                wordTranslation.setmTranslation(list.get(indexFalseTranslation).getmTranslation());
                wordTranslation.setmRealPair(false);
            }
            wordList.add(wordTranslation);
            counter++;
        }
        return wordList;
    }

    public boolean hasUserWon(int errors, int rights) {
        return rights > errors;
    }

    private boolean getTruePairs() {
        return mRandom.nextBoolean();
    }

    private int getRandomIndex(int bound) {
        return mRandom.nextInt(bound);
    }
}
