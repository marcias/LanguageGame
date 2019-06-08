package com.marciasc.languagegame.game;

import android.content.Context;
import android.os.AsyncTask;

import com.marciasc.languagegame.WordTranslation;
import com.marciasc.languagegame.WordsRepository;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.View mView;
    private int mErrosCount = 0;
    private int mRightsCount = 0;
    private int mMatchCounter = 0;
    private GameStrategy mGameStrategy;

    private List<WordTranslation> mWordTranslations = new ArrayList<>();

    @Override
    public void setView(GameContract.View view) {
        mView = view;
    }

    @Override
    public void loadGame() {
        mMatchCounter = 0;
        mErrosCount = 0;
        mRightsCount = 0;
        mView.showLoadingState();
        mGameStrategy = new GameStrategy();
        new LoadWordsAsyncTask().execute();
    }

    private void finishLoadingGame() {
        mView.updateCounter(mErrosCount,mRightsCount);
        mView.updateWords(mWordTranslations.get(mMatchCounter));
        mView.showReadyState();
    }

    @Override
    public void initGame() {
        mView.showGameAnimation();
    }

    @Override
    public void markPoint(WordTranslation wordTranslation, boolean rightTranslation) {
        if(rightTranslation == wordTranslation.getmRealPair()) {
            mRightsCount++;
        } else {
            mErrosCount++;
        }
        continueGame();
    }

    @Override
    public void lostPoint() {
        mErrosCount++;
        continueGame();
    }

    private void verifyResult() {
        if(mGameStrategy.getRoundResult(mErrosCount,mRightsCount) == GameStrategy.RoundResult.WON) {
            mView.showPositiveResult();
        } else {
            mView.showNegativeResult();
        }
    }

    private void continueGame() {
        mView.updateCounter(mErrosCount,mRightsCount);
        mMatchCounter++;
        if(mMatchCounter >= GameStrategy.MAXIMUM_MATCHES){
            verifyResult();
        } else {
            mView.updateWords(mWordTranslations.get(mMatchCounter));
            mView.showGameAnimation();
        }
    }

    class LoadWordsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<WordTranslation> wordsList = WordsRepository.getInstance().loadWords((Context) mView);
            mWordTranslations = mGameStrategy.generateListOfWords(wordsList);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            finishLoadingGame();
        }
    }
}
