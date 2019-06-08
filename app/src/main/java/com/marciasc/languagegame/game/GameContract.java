package com.marciasc.languagegame.game;

import com.marciasc.languagegame.WordTranslation;

public class GameContract {

    interface View {
        void updateWords(WordTranslation wordTranslation);
        void updateCounter(int errorsCount, int rightCount);
        void showPositiveResult();
        void showNegativeResult();
        void initGameAnimation();
        void showReadyState();
    }

    interface Presenter {
        void setView(View view);
        void loadGame();
        void initGame();
        void markPoint(WordTranslation wordTranslation, boolean rightTranslation);
    }
}