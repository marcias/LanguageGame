package com.marciasc.languagegame.game;

import android.content.Context;

import com.marciasc.languagegame.WordTranslation;
import com.marciasc.languagegame.WordsRepository;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter implements GameContract.Presenter, WordsRepository.OnLoadingWordsFinished {
    private GameContract.View mView;
    private int mErrorsCount = 0;
    private int mRightsCount = 0;
    private int mMatchCounter = 0;
    private GameStrategy mGameStrategy;
    private WordsRepository.LoadingWordsAsyncTask mLoadingWordsAsyncTask;

    private List<WordTranslation> mWordTranslations = new ArrayList<>();

    @Override
    public void setView(GameContract.View view) {
        mView = view;
    }

    @Override
    public void loadGame() {
        mMatchCounter = 0;
        mErrorsCount = 0;
        mRightsCount = 0;
        mView.showLoadingState();
        mGameStrategy = new GameStrategy();
        mLoadingWordsAsyncTask = new WordsRepository.LoadingWordsAsyncTask((Context) mView, mGameStrategy);
        mLoadingWordsAsyncTask.setListener(this);
        mLoadingWordsAsyncTask.execute();
    }

    private void finishLoadingGame() {
        mView.updateCounter(mErrorsCount, mRightsCount);
        mView.updateWords(mWordTranslations.get(mMatchCounter));
        mView.showReadyState();
    }

    @Override
    public void initGame() {
        mView.showGameAnimation();
    }

    @Override
    public void markPoint(WordTranslation wordTranslation, boolean rightTranslation) {
        if (rightTranslation == wordTranslation.getmRealPair()) {
            mRightsCount++;
            mView.showPositiveFeedback();
        } else {
            mErrorsCount++;
            mView.showNegativeFeedback();
        }
        continueGame();
    }

    @Override
    public void lostPoint() {
        mErrorsCount++;
        mView.showNegativeFeedback();
        continueGame();
    }

    @Override
    public void onDestroy() {
        if (mLoadingWordsAsyncTask != null) {
            mLoadingWordsAsyncTask.setListener(null);
        }
    }

    @Override
    public void onLoadingWordsFinished(List<WordTranslation> wordTranslationList) {
        mWordTranslations = wordTranslationList;
        finishLoadingGame();
    }

    private void verifyResult() {
        if (mGameStrategy.hasUserWon(mErrorsCount, mRightsCount)) {
            mView.showPositiveResult();
        } else {
            mView.showNegativeResult();
        }
    }

    private void continueGame() {
        mView.updateCounter(mErrorsCount, mRightsCount);
        mMatchCounter++;
        if (mMatchCounter >= GameStrategy.MAXIMUM_MATCHES) {
            verifyResult();
        } else {
            mView.updateWords(mWordTranslations.get(mMatchCounter));
            mView.showGameAnimation();
        }
    }

}
