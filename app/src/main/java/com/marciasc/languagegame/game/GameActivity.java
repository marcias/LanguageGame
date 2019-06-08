package com.marciasc.languagegame.game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.marciasc.languagegame.R;
import com.marciasc.languagegame.WordTranslation;

public class GameActivity extends AppCompatActivity implements GameContract.View {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void updateWords(WordTranslation wordTranslation) {

    }

    @Override
    public void updateCounter(int errorsCount, int rightCount) {

    }

    @Override
    public void showPositiveResult() {

    }

    @Override
    public void showNegativeResult() {

    }

    @Override
    public void initGameAnimation() {

    }

    @Override
    public void showReadyState() {

    }
}
