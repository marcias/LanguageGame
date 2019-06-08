package com.marciasc.languagegame.game;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.marciasc.languagegame.R;
import com.marciasc.languagegame.WordTranslation;

public class GameActivity extends AppCompatActivity implements GameContract.View {
    private final int mDuration = 5000;
    private GamePresenter mGamePresenter;
    private TextView mTvErrorsCounter;
    private TextView mTvRightsCounter;
    private TextView mTvWord;
    private TextView mTvTranslation;
    private ProgressBar mProgress;
    private RelativeLayout mReadyStateView;

    private WordTranslation mWordTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initUi();
        mGamePresenter = new GamePresenter();
        mGamePresenter.setView(this);
        mGamePresenter.loadGame();
    }

    private void initUi() {
        mTvErrorsCounter = findViewById(R.id.tv_errors);
        mTvRightsCounter = findViewById(R.id.tv_rights);
        mTvWord = findViewById(R.id.tv_word);
        mTvTranslation = findViewById(R.id.tv_translation);
        mProgress = findViewById(R.id.progress);
        mReadyStateView = findViewById(R.id.view_ready_state);
    }

    @Override
    public void updateWords(WordTranslation wordTranslation) {
        mWordTranslation = wordTranslation;
        mTvWord.setText(wordTranslation.getmWord());
        mTvTranslation.setText(wordTranslation.getmTranslation());
    }

    @Override
    public void updateCounter(int errorsCount, int rightCount) {
        mTvErrorsCounter.setText(String.valueOf(errorsCount));
        mTvRightsCounter.setText(String.valueOf(rightCount));
    }

    @Override
    public void showPositiveResult() {

    }

    @Override
    public void showNegativeResult() {

    }

    @Override
    public void initGameAnimation() {
        mReadyStateView.setVisibility(View.GONE);
        float bottomOfScreen = getResources().getDisplayMetrics()
                .heightPixels - (mTvTranslation.getHeight() * 4);

        mTvTranslation.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(mDuration);
    }

    @Override
    public void showReadyState() {
        mProgress.setVisibility(View.GONE);
        mReadyStateView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingState() {
        mProgress.setVisibility(View.VISIBLE);
    }

    public void onRightTranslationPressed(View view) {
        mGamePresenter.markPoint(mWordTranslation, true);
    }

    public void onWrongTranslationPressed(View view) {
        mGamePresenter.markPoint(mWordTranslation, false);
    }

    public void onPlayButtonPressed(View view) {
        mGamePresenter.initGame();
    }
}
