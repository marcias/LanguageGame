package com.marciasc.languagegame;

import com.google.gson.annotations.SerializedName;

public class WordTranslation {
    @SerializedName("text_eng")
    private String mWord;
    @SerializedName("text_spa")
    private String mTranslation;
    private Boolean mRealPair;

    public String getmWord() {
        return mWord;
    }

    public void setmWord(String mWord) {
        this.mWord = mWord;
    }

    public String getmTranslation() {
        return mTranslation;
    }

    public void setmTranslation(String mTranslation) {
        this.mTranslation = mTranslation;
    }

    public Boolean getmRealPair() {
        return mRealPair;
    }

    public void setmRealPair(Boolean mRealPair) {
        this.mRealPair = mRealPair;
    }

}
