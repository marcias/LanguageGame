package com.marciasc.languagegame.game;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.marciasc.languagegame.R;
import com.marciasc.languagegame.WordTranslation;

public class GameActivity extends AppCompatActivity implements GameContract.View {
    private final int mDuration = 4000;
    private GamePresenter mGamePresenter;
    private TextView mTvErrorsCounter;
    private TextView mTvRightsCounter;
    private TextView mTvWord;
    private TextView mTvTranslation;
    private LinearLayout mLayoutCounter;
    private ProgressBar mProgress;
    private RelativeLayout mReadyStateView;
    private WordTranslation mWordTranslation;
    private TranslateAnimation mAnimator;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initUi();
        mGamePresenter = new GamePresenter();
        mGamePresenter.setView(this);
        initAnimation();
        mGamePresenter.loadGame();
    }

    private void initUi() {
        mTvErrorsCounter = findViewById(R.id.tv_errors);
        mTvRightsCounter = findViewById(R.id.tv_rights);
        mTvWord = findViewById(R.id.tv_word);
        mTvTranslation = findViewById(R.id.tv_translation);
        mLayoutCounter = findViewById(R.id.ll_counter);
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
        showDialogResult(getString(R.string.you_won));
    }

    @Override
    public void showNegativeResult() {
        showDialogResult(getString(R.string.study_more));
    }

    @Override
    public void showGameAnimation() {
        mReadyStateView.setVisibility(View.GONE);
        mTvTranslation.setVisibility(View.VISIBLE);
        mTvTranslation.startAnimation(mAnimator);
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

    @Override
    public void showPositiveFeedback() {
        mLayoutCounter.setBackgroundColor(getResources().getColor(R.color.green));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayoutCounter.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        }, 1000);
    }

    @Override
    public void showNegativeFeedback() {
        mLayoutCounter.setBackgroundColor(getResources().getColor(R.color.red));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayoutCounter.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        }, 1000);
    }

    public void onRightTranslationPressed(View view) {
        mTvTranslation.setVisibility(View.GONE);
        mTvTranslation.getAnimation().cancel();
        mGamePresenter.markPoint(mWordTranslation, true);
    }

    public void onWrongTranslationPressed(View view) {
        mTvTranslation.setVisibility(View.GONE);
        mTvTranslation.getAnimation().cancel();
        mGamePresenter.markPoint(mWordTranslation, false);
    }

    public void onPlayButtonPressed(View view) {
        mGamePresenter.initGame();
    }

    private void showDialogResult(String message) {
        new AlertDialog.Builder(this).setCancelable(false).setMessage(message).setNeutralButton(getString(R.string.play_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGamePresenter.loadGame();
            }
        }).show();
    }

    private void initAnimation() {
        float bottomOfScreen = getResources().getDisplayMetrics().heightPixels - (mTvTranslation.getHeight() * 4);
        mAnimator = new TranslateAnimation(0, 0, 0, bottomOfScreen);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.setDuration(mDuration);
        mAnimator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mTvTranslation.setVisibility(View.GONE);
                mTvTranslation.getAnimation().cancel();
                mGamePresenter.lostPoint();
            }
        });
        mAnimator.setRepeatCount(TranslateAnimation.INFINITE);
        mAnimator.setRepeatMode(TranslateAnimation.RESTART);
    }

    @Override
    protected void onDestroy() {
        mGamePresenter.onDestroy();
        super.onDestroy();
    }
}
